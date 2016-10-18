package selenium;

import java.util.Set;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import selenium.driver.WebDriverConfig;
import selenium.utils.WebDriverProvider;


public abstract class SeleniumTestWrapper {

	// Config
	private final WebDriverConfig webDriverConfig = new WebDriverConfig();
	protected final WebDriverProvider webDriverProvider = new WebDriverProvider(this.webDriverConfig);

	protected WebDriver getDriver() {
		return this.webDriverProvider.getDriver();
	}

	@After
	public void tearDown() {
		Set<String> windowList = getDriver().getWindowHandles();

		if (!windowList.isEmpty() && windowList.size() > 1) {
			for (String window : windowList) {
				getDriver().switchTo().window(window).close();
			}
		} else {
			getDriver().close();
		}
	}
}
