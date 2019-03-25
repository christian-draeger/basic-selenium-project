package tests.template
import it.skrape.element
import it.skrape.expect
import it.skrape.matchers.toBe
import it.skrape.matchers.toContain
import it.skrape.skrape
import it.skrape.title
import org.junit.jupiter.api.Test

class ExampleTemplateIT {

    @Test
    fun `template check if title and search are present`() {
        skrape {
            url = "https://github.com"
            timeout = 10000
            expect {
                title {
                    toContain("GitHub")
                }
                element("input[name=q]") {
                    attr("placeholder") toBe "Search GitHub"
                }
            }
        }
    }
}