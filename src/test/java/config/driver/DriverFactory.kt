package config.driver

import config.annotations.Browsers
import config.annotations.Browsers.*
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
import java.util.concurrent.TimeUnit
import java.util.logging.Level

open class DriverFactory {

    private val timeout = Integer.getInteger("page_load_timeout", 30).toLong()

    fun get(requestedDriver: Browsers): WebDriver {
        return webDriver[requestedDriver.value]?.invoke() ?: chromeHeadless()
    }

    fun WebDriver.defaultSettings(): WebDriver = apply {
        manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS)
        manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
        manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS)
        manage().window().maximize()
    }

    private val webDriver: Map<String, () -> WebDriver> = mapOf(
        FIREFOX.value to { firefox() },
        FIREFOX_HEADLESS.value to { firefoxHeadless() },
        CHROME.value to { chrome() },
        CHROME_HEADLESS.value to { chromeHeadless() },
        OPERA.value to { opera() },
        SAFARI.value to { safari() },
        EDGE.value to { edge() },
        INTERNET_EXPLORER.value to { internetExplorer() }
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

    private fun firefoxOptions() = FirefoxOptions().merge(capabilities())
    private fun operaOptions() = OperaOptions().merge(capabilities())
    private fun safariOptions() = SafariOptions().merge(capabilities())
    private fun edgeOptions() = EdgeOptions().merge(capabilities())
    private fun ieOptions() = InternetExplorerOptions().merge(capabilities())
    private fun chromeOptions() = ChromeOptions().addArguments("--dns-prefetch-disable").merge(capabilities())

    private fun capabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()
        val logPrefs = LoggingPreferences().apply { enable(LogType.BROWSER, Level.ALL) }

        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
        capabilities.isJavascriptEnabled = true

        return capabilities
    }
}

