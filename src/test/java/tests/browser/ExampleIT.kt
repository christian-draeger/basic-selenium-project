package tests.browser
import config.UiTest
import config.annotations.Browser
import config.annotations.Screenshot
import config.driver.Breakpoint
import config.driver.Browsers
import io.qameta.allure.Description
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.junit.jupiter.api.Test

@Browser(dimension = Breakpoint.XLARGE, use = Browsers.CHROME_HEADLESS)
@Screenshot
class ExampleIT : UiTest() {

    @Test
    @Description("this is a custom description that will be visible in the detailed test report")
    fun `verify search field is available`() {
        goTo("https://github.com")
        assertThat(el("input[name=q]")).isDisplayed
    }
}