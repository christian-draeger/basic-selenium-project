package selenium.driver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

class DesiredCapabilitiesFactory {

	DesiredCapabilities initDesiredCapabilities(WebDriverConfig config) {

		final DesiredCapabilities capabilities = new DesiredCapabilities();

		final String browser = config.getBrowserName();
		capabilities.setBrowserName(browser);

		capabilities.setJavascriptEnabled(true);
		capabilities.acceptInsecureCerts();

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		return capabilities;
	}

	ChromeOptions chromeOptions(DesiredCapabilities capabilities, String userAgent, boolean disableCookies) {
        Map<String, Integer> prefs = new HashMap();
        if (disableCookies) {
            prefs.put("profile.default_content_setting_values.cookies", 2);
        }
		return new ChromeOptions()
				.addArguments("user-agent=" + userAgent)
				.addArguments("--disable-gpu")
				.addArguments("--dns-prefetch-disable")
                .setExperimentalOption("prefs", prefs)
				.merge(capabilities);
	}

	OperaOptions operaOptions(DesiredCapabilities capabilities, String userAgent) {
		return new OperaOptions()
				.addArguments("user-agent=" + userAgent)
				.merge(capabilities);
	}

	FirefoxOptions firefoxOptions(DesiredCapabilities capabilities, String userAgent) {
		return new FirefoxOptions()
				.addArguments("user-agent=" + userAgent)
				.merge(capabilities);
	}

}
