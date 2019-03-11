package tests;

import config.AbstractUiIT;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class JavaExampleIT extends AbstractUiIT {

    @Test
    public void anExampleTestWrittenInJava() {
        goTo("https://github.com");
        assertThat(el("input[name=q]")).isDisplayed();
    }
}
