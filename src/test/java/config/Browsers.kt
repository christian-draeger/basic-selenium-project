package config

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.ie.InternetExplorerOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.opera.OperaDriver
import org.openqa.selenium.opera.OperaOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.safari.SafariOptions
import java.util.logging.Level

private fun capabilities(): DesiredCapabilities {
	val capabilities = DesiredCapabilities()

	val logPrefs = LoggingPreferences().apply {
		enable(LogType.BROWSER, Level.ALL)
	}

	capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
	capabilities.isJavascriptEnabled = true

	return capabilities
}

fun firefox(): WebDriver {
	WebDriverManager.firefoxdriver().setup()

	return FirefoxDriver(FirefoxOptions(capabilities()))
}

private fun firefoxOptions(): FirefoxOptions {
	return FirefoxOptions(capabilities())
}

fun firefoxHeadless(): FirefoxDriver {
	WebDriverManager.firefoxdriver().setup()
	return FirefoxDriver(firefoxOptions().setHeadless(true))
}

private fun chromeOptions(): ChromeOptions {
	return ChromeOptions()
			.addArguments("--disable-gpu")
			.addArguments("--dns-prefetch-disable")
}

fun chrome(): WebDriver {
	WebDriverManager.chromedriver().setup()
	return ChromeDriver(chromeOptions())
}

fun chromeHeadless(): WebDriver {
	WebDriverManager.chromedriver().setup()
	return ChromeDriver(chromeOptions().setHeadless(true))
}

fun opera(): WebDriver {
	WebDriverManager.operadriver().setup()
	return OperaDriver(OperaOptions().merge(capabilities()))

}

fun internetExplorer(): WebDriver {
	WebDriverManager.iedriver().setup()
	return InternetExplorerDriver(InternetExplorerOptions().merge(capabilities()))

}

fun edge(): WebDriver {
	WebDriverManager.edgedriver().setup()
	return EdgeDriver(EdgeOptions().merge(capabilities()))

}

fun safari(): WebDriver {
	return SafariDriver(SafariOptions().merge(capabilities()))
}