package tests
import config.UiTest
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.junit.jupiter.api.Test

class ExampleIT : UiTest() {

    @Test
    fun `verify search field is available`() {
        goTo("https://github.com")
        assertThat(el("input[name=q]")).isDisplayed
    }
}