package config

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestWatcher
import java.util.*


class TestStatusLogger : BeforeEachCallback, TestWatcher {

    private val RESET = "\u001B[0m"

    private val RED = "\u001B[31m"
    private val GREEN = "\u001B[32m"
    private val BLUE = "\u001B[34m"
    private val WHITE = "\u001B[37m"
    private fun color(string: String, ansi: String) = "$ansi$string$RESET"

    override fun beforeEach(context: ExtensionContext) {
        println(color("▶️ - test \"${context.displayName}\" has been started", BLUE))
    }

    override fun testSuccessful(context: ExtensionContext) {
        println(color("✅ - test \"${context.displayName}\" succeeded", GREEN))
    }

    override fun testFailed(context: ExtensionContext, cause: Throwable) {
        println(color("⛔ - test \"${context.displayName}\" failed", RED))
    }

    override fun testDisabled(context: ExtensionContext, reason: Optional<String>) {
        println(color("\uD83D\uDCA4 - test \"${context.displayName}\" has been skipped", WHITE))
    }

    override fun testAborted(context: ExtensionContext, cause: Throwable) {
        println(color("\uD83D\uDD19 - test ${context.displayName} has been aborted", BLUE))
    }
}
