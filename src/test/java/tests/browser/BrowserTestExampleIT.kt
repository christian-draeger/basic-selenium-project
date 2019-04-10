package tests.browser
import config.BrowserTest
import config.annotations.Browser
import config.annotations.Screenshot
import config.driver.Breakpoint
import io.qameta.allure.Description
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.Test
import tests.browser.pageobjects.StartPage

@Browser(dimension = Breakpoint.XLARGE)
@Screenshot
class BrowserTestExampleIT : BrowserTest() {

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
}