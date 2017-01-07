package selenium.driver;

import static org.openqa.selenium.phantomjs.PhantomJSDriverService.PHANTOMJS_CLI_ARGS;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

class DesiredCapabilitiesFactory {

	public DesiredCapabilities initDesiredCapabilities(WebDriverConfig config, String userAgent, boolean disableCookies) {

		// DesireCapabilities variable
		final DesiredCapabilities capabilities = new DesiredCapabilities();

		// Get browser from config and set browser.
		final String browser = config.getBrowserName();
		capabilities.setBrowserName(browser);

		// Set specific settings for firefox.
		if (browser.equals("firefox")) {
			FirefoxProfile profile = new FirefoxProfile();
			// profile.setPreference("general.useragent.override", userAgent);
			// if (disableCookies) {
			// 	profile.setPreference("network.cookie.cookieBehavior", 2);
			// }
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		}

		// Set specific settings for chrome.
		else if (browser.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("user-agent=" + userAgent);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		}

		// Set specific settings for opera.
		else if (browser.equals("opera")) {
			OperaOptions options = new OperaOptions();
			options.addArguments("start-maximized");
			options.addArguments("user-agent=" + userAgent);
			capabilities.setCapability(OperaOptions.CAPABILITY, options);
		}

		// Set settings for internet explorer.
		else if (browser.equals("internet-explorer")) {

		}

		// Set specific settings for edge.
		else if (browser.equals("MicrosoftEdge")) {

		}

		// Set specific settings for phantomjs
		else if (browser.equals("phantomjs")) {
			capabilities.setCapability("phantomjs.page.settings.userAgent", userAgent);
			capabilities.setCapability(PHANTOMJS_CLI_ARGS, new String[] { "--webdriver-loglevel=NONE" });//NONE,ERROR
			Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.WARNING);
		}

		return capabilities;
	}

}
