package selenium.driver;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.MarionetteDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;

class DesiredCapabilitiesFactory {

	static DesiredCapabilities initDesiredCapabilities(WebDriverConfig config) {

		// DesireCapabilities variable
		final DesiredCapabilities capabilities = new DesiredCapabilities();

		// Get browser from config and set browser.
		final String browser = config.getBrowserName();
		capabilities.setBrowserName(browser);

		switch (browser) {
			case "chrome":
				ChromeDriverManager.getInstance().setup();
				break;
			case "edge":
				EdgeDriverManager.getInstance().setup();
				break;
			case "internetexplorer":
				InternetExplorerDriverManager.getInstance().setup();
				break;
			case "firefox":
				MarionetteDriverManager.getInstance().setup();
				break;
			case "opera":
				OperaDriverManager.getInstance().setup();
				break;
			case "phantomjs":
				PhantomJsDriverManager.getInstance().setup();
			default:
				break;
		}

		return capabilities;
	}

}
