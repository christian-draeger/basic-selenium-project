package selenium;

import static org.junit.Assume.assumeTrue;
import static selenium.utils.annotations.RequiredBrowser.Browsers.CHROME;
import static selenium.utils.annotations.RequiredBrowser.Browsers.EDGE;
import static selenium.utils.annotations.RequiredBrowser.Browsers.FIREFOX;
import static selenium.utils.annotations.RequiredBrowser.Browsers.INTERNET_EXPLORER;
import static selenium.utils.annotations.RequiredBrowser.Browsers.OPERA;
import static selenium.utils.annotations.RequiredBrowser.Browsers.PHANTOMJS;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import selenium.driver.WebDriverConfig;
import selenium.utils.WebDriverProvider;
import selenium.utils.annotations.BrowserDimension;
import selenium.utils.annotations.RepeatRule;
import selenium.utils.annotations.RequiredBrowser;
import selenium.utils.annotations.UserAgent;


public abstract class SeleniumTestWrapper {

	// Config
	private final WebDriverConfig webDriverConfig = new WebDriverConfig();
	protected final WebDriverProvider webDriverProvider = new WebDriverProvider(this.webDriverConfig);

	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	protected WebDriver getDriver() {
		return this.webDriverProvider.getDriver();
	}

	protected void runWithFirefoxOnly() {
		RemoteWebDriver remoteWebDriver = (RemoteWebDriver) getDriver();
		String browserName = (String) remoteWebDriver.getCapabilities().getCapability("browserName");
		assumeTrue("skipping test; only runs with firefox", browserName.equals("firefox"));
	}

	@Before
	public void setUserAgent(){
		UserAgent userAgent = this.getClass().getAnnotation(UserAgent.class);
		if (userAgent != null) {
			webDriverProvider.useUserAgent(userAgent.value());
		}
	}

	@Before
	public void browserDimension(){
		BrowserDimension browserDimension = this.getClass().getAnnotation(BrowserDimension.class);
		if (browserDimension != null) {
			getDriver().manage().window().setSize(browserDimension.value().dimension);
		}
	}

	@Before
	public void assumeBrowser(){
		RequiredBrowser requiredBrowser = this.getClass().getAnnotation(RequiredBrowser.class);
		RemoteWebDriver webDriverWithCapabilities = (RemoteWebDriver) getDriver();

		if (requiredBrowser != null && requiredBrowser.value().equals(FIREFOX)) {
			assumeTrue("only execute test against firefox", webDriverWithCapabilities.getCapabilities().getBrowserName().equalsIgnoreCase("firefox"));
		} else if (requiredBrowser != null && requiredBrowser.value().equals(PHANTOMJS)) {
			assumeTrue("only execute test against phantomjs", webDriverWithCapabilities.getCapabilities().getBrowserName().equalsIgnoreCase("phantomjs"));
		} else if (requiredBrowser != null && requiredBrowser.value().equals(CHROME)) {
			assumeTrue("only execute test against chrome", webDriverWithCapabilities.getCapabilities().getBrowserName().equalsIgnoreCase("chrome"));
		} else if (requiredBrowser != null && requiredBrowser.value().equals(EDGE)) {
			assumeTrue("only execute test against edge", webDriverWithCapabilities.getCapabilities().getBrowserName().equalsIgnoreCase("edge"));
		} else if (requiredBrowser != null && requiredBrowser.value().equals(INTERNET_EXPLORER)) {
			assumeTrue("only execute test against internet explorer", webDriverWithCapabilities.getCapabilities().getBrowserName().equalsIgnoreCase("internetExplorer"));
		} else if (requiredBrowser != null && requiredBrowser.value().equals(OPERA)) {
			assumeTrue("only execute test against opera", webDriverWithCapabilities.getCapabilities().getBrowserName().equalsIgnoreCase("opera"));
		}
	}

	@After
	public void closeBrowser(){
		getDriver().close();
	}


}
