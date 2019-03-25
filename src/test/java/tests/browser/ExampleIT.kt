package tests.browser
import config.BrowserTest
import config.annotations.Browser
import config.annotations.EnabledIfReachable
import config.annotations.Screenshot
import config.driver.Breakpoint
import io.qameta.allure.Description
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnJre
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.JRE.JAVA_8
import org.junit.jupiter.api.condition.JRE.JAVA_9
import org.junit.jupiter.api.condition.OS.LINUX
import org.junit.jupiter.api.condition.OS.WINDOWS
import tests.browser.pageobjects.StartPage

@Browser(dimension = Breakpoint.XLARGE)
@Screenshot
class ExampleIT : BrowserTest() {

    @Page
    lateinit var page: StartPage

    @Test
    fun `an example test using page object pattern`() {
        goTo(page)
        assertThat(page.searchField).isDisplayed
    }

    @Test
    @Description("this is a custom description that will be visible in the detailed test report")
    fun `an example test NOT using page object pattern`() {
        goTo("https://github.com")
        assertThat(el("input[name=q]")).isDisplayed
    }

    @Test
    @EnabledOnOs(LINUX, WINDOWS)
    fun `will be skipped on all platforms beside LINUX & WINDOWS`() {
        // do something here
    }

    @Test
    @DisabledOnJre(JAVA_8, JAVA_9)
    fun `will be skipped if tests are running with specified JRE`() {
        // do something here
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    fun `will only run if system properties matches - otherwise skipped`() {
        // do something here
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "ci")
    fun `will only run if environment variable matches - otherwise skipped`() {
        // do something here
    }

    @Test
    @EnabledIfReachable(url = "http://www.google.com", timeoutMillis = 5000)
    fun `will only run if url is reachable - otherwise skipped`() {
        // do something here
    }
}