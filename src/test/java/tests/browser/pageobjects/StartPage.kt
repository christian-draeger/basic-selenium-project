package tests.browser.pageobjects

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

@PageUrl("https://github.com") // specifies getUrl()
@FindBy(css=".enterprise-prompt") // specifies isAt()
open class StartPage : FluentPage() {

    @FindBy(css = "input[name=q]")
    lateinit var searchField: FluentWebElement

    @FindBy(css = "header a[href^='/login']")
    lateinit var loginButton: FluentWebElement

}
