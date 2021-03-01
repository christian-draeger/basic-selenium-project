package tests.browser;

import config.annotations.EnabledIfReachable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;

import static org.junit.jupiter.api.condition.JRE.JAVA_8;
import static org.junit.jupiter.api.condition.JRE.JAVA_9;
import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.junit.jupiter.api.condition.OS.MAC;

class JavaConditionalExecutionExampleIT {

    @Test
    @EnabledOnOs({ LINUX, MAC })
    void will_be_skipped_on_all_platforms_beside_LINUX_and_MAC() {
        // do something here
    }

    @Test
    @DisabledOnJre({ JAVA_8, JAVA_9 })
    void will_be_skipped_if_tests_are_running_with_specified_JRE() {
        // do something here
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void will_only_run_if_system_properties_matches__otherwise_skipped() {
        // do something here
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "ci")
    void will_only_run_if_environment_variable_matches__otherwise_skipped() {
        // do something here
    }

    @Disabled // TODO: re-enable after skrapeit has been bumped to use latest okhttp
    @Test
    @EnabledIfReachable(url = "http://www.google.com", timeoutMillis = 5000)
    void will_only_run_if_url_is_reachable__otherwise_skipped() {
        // do something here
    }
}
