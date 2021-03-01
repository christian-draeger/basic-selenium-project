package tests.template

import it.skrape.core.fetcher.BrowserFetcher
import it.skrape.core.fetcher.HttpFetcher
import it.skrape.core.htmlDocument
import it.skrape.expect
import it.skrape.matchers.toBe
import it.skrape.matchers.toBePresent
import it.skrape.matchers.toContain
import it.skrape.selects.html5.input
import it.skrape.selects.html5.title
import it.skrape.skrape
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled // TODO: re-enable after skrapeit has been bumped to use latest okhttp
class ExampleTemplateIT {

/*
*  See https://docs.skrape.it for further information on how to use Skrape{it}.
*  It's faster than Selenium and doesn't need a browser installation.
*  Testing of User-Journeys is not supported currently but it's the better choice over selenium
*  if you want to check values or appearance of elements or extract data.
*/

    @Test
    fun `template check if title and search are present`() {
        skrape(HttpFetcher) {
            request {
                url = "https://www.github.com"
            }
            expect {
                htmlDocument {
                    title {
                        findFirst {
                            text toContain "GitHub"
                        }
                    }
                    input {
                        withAttribute = "name" to "q"
                        findFirst {
                            attribute("placeholder") toBe "Search GitHub"
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `dom tree check if login box is getting rendered by javascript`() {
        skrape(BrowserFetcher) {
            request {
                url = "https://twitter.com/github"
            }
            expect {
                htmlDocument {
                    title {
                        findFirst {
                            text toContain "Twitter"
                        }
                    }
                    ".signin-dialog-body" {
                        findFirst {
                            toBePresent
                        }
                    }
                }
            }
        }
    }
}