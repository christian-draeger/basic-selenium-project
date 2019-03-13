package config

import mu.KotlinLogging
import org.fluentlenium.adapter.junit.jupiter.FluentTest
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.core.search.SearchFilter
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver
import java.io.FileInputStream
import java.util.*
import java.util.concurrent.TimeUnit

//@TestInstance(PER_CLASS)
@ExtendWith(TestStatusLogger::class)
open class UiTest : FluentTest() {

	private val logger = KotlinLogging.logger {}

	private val browser = System.getProperty("browser", "chrome-headless")
	private val timeout = Integer.getInteger("page_load_timeout", 30).toLong()

	override fun newWebDriver(): WebDriver {

		val driver = when (browser) {
			"firefox-headless" -> firefoxHeadless()
			"firefox" -> firefox()
			"chrome" -> chrome()
			"safari" -> safari()
			"opera" -> opera()
			"edge" -> edge()
			"ie" -> internetExplorer()
			else -> chromeHeadless()
		}

		driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS)
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
		driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS)
        driver.manage().window().size = Dimension(1400, 900)

		return EventFiringWebDriver(driver)
	}

	@BeforeEach
	fun setUp() {
		screenshotMode = ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL
		screenshotPath = "build/screenshots"
		awaitAtMost = 30_000

		events().beforeClickOn { element, _ ->
			executeScript("arguments[0].style.border = '3px solid red'; ", element.element)
		}

		events().beforeNavigateTo { url, _ ->
			logger.info { "open URL $url" }
		}

		events().afterNavigateTo { url, _ ->
			logger.info { "opened URL $url" }
			logger.info { "window title: ${window().title()}" }
		}

		driver.manage().deleteAllCookies()
	}

	@AfterEach
	fun tearDown() {
		// no op
	}

	fun jq(selector: String, vararg filter: SearchFilter) = `$`(selector, *filter)

	private fun getProp(key: String): String? = Properties().apply {
		load(FileInputStream("config.properties"))
	}.getProperty(key)?: throw RuntimeException("property '$key' not found")
}
