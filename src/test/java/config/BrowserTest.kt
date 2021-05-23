package config

import config.annotations.Browser
import config.annotations.Screenshot
import config.driver.Breakpoint
import config.driver.DriverFactory
import mu.KotlinLogging
import org.fluentlenium.adapter.junit.jupiter.FluentTest
import org.fluentlenium.assertj.FluentLeniumAssertions
import org.fluentlenium.assertj.custom.FluentListAssert
import org.fluentlenium.assertj.custom.FluentWebElementAssert
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.search.SearchFilter
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver
import java.util.concurrent.TimeUnit
import kotlin.reflect.full.createInstance

@ExtendWith(TestStatusLogger::class)
open class BrowserTest : FluentTest() {

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
		screenshotMode = screenshotMode()
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

	private fun requestedDriver()= javaClass.getAnnotation(Browser::class.java)?.use

	private fun screenshotMode(): ConfigurationProperties.TriggerMode {
		if (javaClass.getAnnotation(Screenshot::class.java) != null) {
			return ConfigurationProperties.TriggerMode.MANUAL
		}
		return ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL
	}

	@BeforeEach
	fun changeBrowserDimension() {
		javaClass.getAnnotation(Browser::class.java)?.let {
			if (it.dimension != Breakpoint.DEFAULT) {
				driver.manageWindowSize(it.dimension)
			}
		}
	}

	private fun WebDriver.manageWindowSize(dimension: Breakpoint) {
		when (dimension) {
			Breakpoint.SMALL -> windowResizeTo(359)
			Breakpoint.MEDIUM -> windowResizeTo(599)
			Breakpoint.LARGE -> windowResizeTo(959)
			Breakpoint.XLARGE -> windowResizeTo(1199)
			Breakpoint.XXLARGE -> windowResizeTo(1280)
			Breakpoint.FULLSCREEN -> manage().window().fullscreen()
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

	@FluentleniumDsl
	inline operator fun <reified T : FluentPage> T.invoke(init: T.() -> Unit) = with(T::class) {
		apply { init() }
	}

	@FluentleniumDsl
	operator fun FluentWebElement.invoke(init: FluentWebElement.() -> Unit) {
		init()
	}

	@FluentleniumDsl
	operator fun String.invoke(init: FluentList<FluentWebElement>.() -> Unit) {
		jq(this).init()
	}

	@FluentleniumDsl
	fun FluentWebElement.assert(init: FluentWebElementAssert.() -> Unit) {
		FluentLeniumAssertions.assertThat(this).init()
	}

	@FluentleniumDsl
	fun FluentList<FluentWebElement>.assert(init: FluentListAssert.() -> Unit) {
		FluentLeniumAssertions.assertThat(this).init()
	}
}

@DslMarker
annotation class FluentleniumDsl
