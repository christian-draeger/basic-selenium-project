package config

import config.annotations.Browser
import config.annotations.Browsers.DEFAULT
import config.annotations.Screen
import config.annotations.Screenshot
import config.driver.DriverFactory
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
import java.util.*
import java.util.concurrent.TimeUnit

@ExtendWith(TestStatusLogger::class)
open class UiTest : FluentTest() {

	private val logger = KotlinLogging.logger {}

	private val timeout = Integer.getInteger("page_load_timeout", 30).toLong()

	private val driverFactory = DriverFactory()

	override fun newWebDriver(): WebDriver {

		val currentDriver = driverFactory.get(requestedDriver())

		currentDriver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS)
		currentDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
		currentDriver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS)
        currentDriver.manage().window().maximize()

		return EventFiringWebDriver(currentDriver)
	}

	@BeforeEach
	fun setUp() {
		screenshotMode = if(screenshotAlways()) ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL else ConfigurationProperties.TriggerMode.MANUAL
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

	private fun requestedDriver()= javaClass.getAnnotation(Browser::class.java)?.use ?: DEFAULT

	private fun screenshotAlways()= javaClass.getAnnotation(Screenshot::class.java) != null

	@BeforeEach
	fun changeBrowserDimension() {
		logger.info { "prop: ${getProp<Int>("screen.small")}" }
		javaClass.getAnnotation(Browser::class.java)?.let {
			if (it.dimension != Screen.DEFAULT) {
				driver.manageWindowSize(it.dimension)
			}
		}
		javaClass.declaredMethods.forEach { method ->
			method.getAnnotation(Browser::class.java)?.let {
				if (it.dimension != Screen.DEFAULT) {
					driver.manageWindowSize(it.dimension)
				}
			}
		}
	}

	private fun WebDriver.manageWindowSize(dimension: Screen) {
		when (dimension) {
			Screen.SMALL -> windowResizeTo(359)
			Screen.MEDIUM -> windowResizeTo(599)
			Screen.LARGE -> windowResizeTo(959)
			Screen.XLARGE -> windowResizeTo(1199)
			Screen.XXLARGE -> windowResizeTo(1280)
			Screen.FULLSCREEN -> manage().window().fullscreen()
			else -> manage().window().maximize()
		}
	}

	fun WebDriver.windowResizeTo(width: Int, height: Int = 800) {
		manage().window().size = Dimension(width, height)
	}

	@AfterEach
	fun tearDown() {
		// no op
	}

	fun jq(selector: String, vararg filter: SearchFilter) = `$`(selector, *filter)

	@Suppress("UNCHECKED_CAST")
	fun <T> getProp(key: String): T {
		val path = "src/test/resources/config.properties"
		val props  = javaClass.getResourceAsStream(path).use {
			Properties().apply { load(it) }
		}
		return (props.getProperty(key) as T)?: throw RuntimeException("property '$key' not found")
	}
}
