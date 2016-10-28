package selenium.driver;

import org.openqa.selenium.remote.DesiredCapabilities;

class DesiredCapabilitiesFactory {

	public DesiredCapabilities initDesiredCapabilities(WebDriverConfig config) {

		// DesireCapabilities variable
		final DesiredCapabilities capabilities = new DesiredCapabilities();

		// Get browser from config and set browser.
		final String browser = config.getBrowserName();
		capabilities.setBrowserName(browser);

		switch (browser) {
			case "chrome":
				// put chrome specific capabilities here
				break;
			case "edge":

				break;
			case "internetexplorer":

				break;
			case "firefox":

				break;
			case "opera":

				break;
			case "phantomjs":

			default:
				break;
		}

		return capabilities;
	}

}
