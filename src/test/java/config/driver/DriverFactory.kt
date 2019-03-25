package config.driver

import config.Utils
import config.driver.Browsers.*
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

class DriverFactory {

    fun get(requestedDriver: Browsers?): WebDriver {
        return webDriver[requestedDriver]?.invoke() ?: default()
    }

    private val webDriver: Map<Browsers, () -> WebDriver> = mapOf(
        INTERNET_EXPLORER to { internetExplorer() },
        FIREFOX_HEADLESS to { firefoxHeadless() },
        CHROME_HEADLESS to { chromeHeadless() },
        FIREFOX to { firefox() },
        CHROME to { chrome() },
        SAFARI to { safari() },
        OPERA to { opera() },
        EDGE to { edge() }
    )

    private fun firefox(): WebDriver {
        WebDriverManager.firefoxdriver().setup()
        return FirefoxDriver(firefoxOptions())
    }

    private fun firefoxHeadless(): WebDriver {
        WebDriverManager.firefoxdriver().setup()
        return FirefoxDriver(firefoxOptions().setHeadless(true))
    }

    private fun chrome(): WebDriver {
        WebDriverManager.chromedriver().setup()
        return ChromeDriver(chromeOptions())
    }

    private fun chromeHeadless(): WebDriver {
        WebDriverManager.chromedriver().setup()
        return ChromeDriver(chromeOptions().setHeadless(true))
    }

    private fun opera(): WebDriver {
        WebDriverManager.operadriver().setup()
        return OperaDriver(operaOptions())
    }

    private fun internetExplorer(): WebDriver {
        WebDriverManager.iedriver().setup()
        return InternetExplorerDriver(ieOptions())
    }

    private fun edge(): WebDriver {
        WebDriverManager.edgedriver().setup()
        return EdgeDriver(edgeOptions())
    }

    private fun safari(): WebDriver {
        return SafariDriver(safariOptions())
    }

    private fun default(): WebDriver {
        return webDriver[getDefaultBrowser()]?.invoke() ?: throw RuntimeException("Invalid Browser requested")
    }

    private fun getDefaultBrowser(): Browsers {
        val invokedBrowser = System.getProperty("browser").orEmpty()

        if (invokedBrowser.isNotBlank()) {
            return Browsers.byString(invokedBrowser)
        }

        val defaultBrowser: String = Utils().getProp("default.browser") ?: "unsupported"

        return Browsers.byString(defaultBrowser)
    }

    private fun firefoxOptions() = FirefoxOptions().merge(capabilities())
    private fun operaOptions() = OperaOptions().merge(capabilities())
    private fun safariOptions() = SafariOptions().merge(capabilities())
    private fun edgeOptions() = EdgeOptions().merge(capabilities())
    private fun ieOptions() = InternetExplorerOptions().merge(capabilities())
    private fun chromeOptions() = ChromeOptions()
        .addArguments("--disable-gpu")
        .addArguments("--dns-prefetch-disable")
        .addArguments("disable-infobars")
        .addArguments("--disable-dev-shm-usage")
        .merge(capabilities())

    private fun capabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()
        val logPrefs = LoggingPreferences().apply { enable(LogType.BROWSER, Level.ALL) }

        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
        capabilities.isJavascriptEnabled = true

        return capabilities
    }
}

enum class Browsers(val value: String) {
    FIREFOX("firefox"),
    FIREFOX_HEADLESS("firefox-headless"),
    CHROME("chrome"),
    CHROME_HEADLESS("chrome-headless"),
    SAFARI("safari"),
    OPERA("opera"),
    EDGE("edge"),
    INTERNET_EXPLORER("ie"),
    DEFAULT("");

    companion object {
        fun byString(s: String) = Browsers.values().find { it.value == s } ?: throw RuntimeException("invalid browser '$s' requested")
    }
}
