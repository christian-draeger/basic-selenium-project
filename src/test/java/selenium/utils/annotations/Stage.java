package selenium.utils.annotations;

import static selenium.utils.annotations.Stage.Stages.STAGING;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Stage {

    enum Stages{
        TESTING,
        STAGING,
        TESTING_AND_STAGING,
        DEV
    }

    Stages value() default STAGING;
}
