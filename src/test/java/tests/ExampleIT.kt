package tests
import config.AbstractUiIT
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.junit.Test

class ExampleIT : AbstractUiIT() {

    @Test
    fun `verify search field is available`() {
        goTo("https://github.com")
        assertThat(el("input[name=q]")).isDisplayed
    }
}