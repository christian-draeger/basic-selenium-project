package selenium.utils.annotations;

import static selenium.utils.annotations.RequiredBrowser.Browsers.FIREFOX;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface RequiredBrowser {

    enum Browsers{
        FIREFOX,
        PHANTOMJS,
        CHROME,
        EDGE,
        INTERNET_EXPLORER,
        OPERA
    }

    Browsers value() default FIREFOX;
}
