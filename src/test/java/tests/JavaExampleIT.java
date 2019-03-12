package tests;

import config.UiTest;
import org.junit.jupiter.api.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

class JavaExampleIT extends UiTest {

    @Test
    void anExampleTestWrittenInJava() {
        goTo("https://github.com");
        assertThat(el("input[name=q]")).isDisplayed();
    }
}
