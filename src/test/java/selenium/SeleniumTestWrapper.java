package selenium;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;

import selenium.configurations.TestConfig;
import selenium.driver.WebDriverConfig;
import selenium.utils.WebDriverProvider;
import selenium.utils.annotations.DisableCookies;
import selenium.utils.annotations.RepeatRule;
import selenium.utils.annotations.UserAgent;
import selenium.utils.annotations.browser.Browser;
import selenium.utils.annotations.browser.BrowserDimension;
import selenium.utils.annotations.browser.Browsers;


public abstract class SeleniumTestWrapper {

	// Config
	protected static final TestConfig testConfig = new TestConfig();
	private final WebDriverConfig webDriverConfig = new WebDriverConfig();
	protected final WebDriverProvider webDriverProvider = new WebDriverProvider(this.webDriverConfig);

	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	protected WebDriver getDriver() {
		return this.webDriverProvider.getDriver();
	}

	/**
	 * test class annotations
	 */
	@Before
	public void setUserAgent(){
		UserAgent userAgent = this.getClass().getAnnotation(UserAgent.class);
		if (userAgent != null) {
			webDriverProvider.useUserAgent(userAgent.value());
		}
	}

	@Before
	public void disableCookies(){
		DisableCookies cookies = this.getClass().getAnnotation(DisableCookies.class);
		if (cookies != null) {
			webDriverProvider.disableCookies(true);
		}
	}

	@Before
	public void browser() throws Exception {
		Browser browser = this.getClass().getAnnotation(Browser.class);
		if (browser != null){
			if (browser.require().length > 0 && browser.skip().length == 0){
				String browsers = concatinateBrowsers(browser.require());
				assumeTrue("only execute test against " + browsers, browsers.contains(testConfig.getBrowser()));
			}

			if (browser.skip().length > 0 && browser.require().length == 0){
				String browsers = concatinateBrowsers(browser.skip());
				assumeFalse("skip test against " + browsers, browsers.contains(testConfig.getBrowser()));
			}
		}
	}

	private String concatinateBrowsers(Browsers[] browsers){
		String concatinatedBrowsers = "";
		for(Browsers browser : browsers) concatinatedBrowsers += browser.getValue() + " & ";
		return concatinatedBrowsers.substring(0,concatinatedBrowsers.lastIndexOf("&"));
	}

	@Before
	public void browserDimension(){
		BrowserDimension browserDimension = this.getClass().getAnnotation(BrowserDimension.class);
		if (browserDimension != null) {
			getDriver().manage().window().setSize(browserDimension.value().dimension);
		}
	}

	@After
	public void closeBrowser(){
		getDriver().quit();
	}


}
