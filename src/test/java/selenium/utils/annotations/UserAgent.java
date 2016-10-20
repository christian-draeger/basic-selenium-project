package selenium.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import selenium.driver.UserAgents;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface UserAgent {
    UserAgents value();
}
