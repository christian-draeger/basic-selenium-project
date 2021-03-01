package tests.browser

import config.annotations.EnabledIfReachable
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.*


class ConditionalExecutionExampleIT {

    @Test
    @EnabledOnOs(OS.LINUX, OS.WINDOWS)
    fun `will be skipped on all platforms beside LINUX & WINDOWS`() {
        // do something here
    }

    @Test
    @DisabledOnJre(JRE.JAVA_8, JRE.JAVA_9)
    fun `will be skipped if tests are running with specified JRE`() {
        // do something here
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    fun `will only run if system properties matches - otherwise skipped`() {
        // do something here
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "ci")
    fun `will only run if environment variable matches - otherwise skipped`() {
        // do something here
    }

    @Disabled // TODO: re-enable after skrapeit has been bumped to use latest okhttp
    @Test
    @EnabledIfReachable(url = "http://www.google.com", timeoutMillis = 5000)
    fun `will only run if url is reachable - otherwise skipped`() {
        // do something here
    }
}