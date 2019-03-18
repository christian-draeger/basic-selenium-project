package tests.browser;

import config.UiTest;
import config.annotations.Browser;
import config.driver.Breakpoint;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@Browser(dimension = Breakpoint.XLARGE)
class JavaExampleIT extends UiTest {

    @Test
    @Description("this is a custom description that will be visible in the detailed test report")
    void anExampleTestWrittenInJava() {
        goTo("https://github.com");
        assertThat(el("input[name=q]")).isDisplayed();
    }
}
