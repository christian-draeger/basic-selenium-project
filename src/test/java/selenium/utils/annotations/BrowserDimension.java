package selenium.utils.annotations;

import static selenium.Screen.SMALL;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import selenium.Screen;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface BrowserDimension {

    Screen value() default SMALL;
}
