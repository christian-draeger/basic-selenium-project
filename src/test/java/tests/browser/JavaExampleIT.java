package tests.browser;

import config.UiTest;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

class JavaExampleIT extends UiTest {

    @Test
    @Description("this is a custom description that will be visible in the detailed test report")
    void anExampleTestWrittenInJava() throws InterruptedException {
        goTo("https://github.com");
        assertThat(el("input[name=q]")).isDisplayed();
    }
}
