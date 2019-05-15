package tests.template
import it.skrape.core.Mode
import it.skrape.expect
import it.skrape.matchers.toBe
import it.skrape.matchers.toBePresent
import it.skrape.matchers.toContain
import it.skrape.selects.element
import it.skrape.selects.title
import it.skrape.skrape
import org.junit.jupiter.api.Test

class ExampleTemplateIT {

/*
*  See https://docs.skrape.it for further information on how to use Skrape{it}.
*  It's faster than Selenium and doesn't need a browser installation.
*  Testing of User-Journeys is not supported currently but it's the better choice over selenium
*  if you want to check values or appearance of elements or extract data.
*/


    @Test
    fun `template check if title and search are present`() {
        skrape {
            url = "https://github.com"
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

    @Test
    fun `dom tree check if login box is getting rendered by javascript`() {
        skrape {
            mode = Mode.DOM
            url = "https://twitter.com/github"
            expect {
                title {
                    toContain("Twitter")
                }
                element(".signin-dialog-body").toBePresent()
            }
        }
    }
}