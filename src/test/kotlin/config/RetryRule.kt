package config

import mu.KotlinLogging
import org.junit.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class Retry(private val retryCount: Int, private val sleepTime: Long) : TestRule {

	val logger = KotlinLogging.logger {}

	override fun apply(base: Statement, description: Description): Statement {
        return statement(base, description)
    }

    private fun statement(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {
                var caughtThrowable: Throwable? = null

                // implement retry logic here
                repeat (retryCount) {
                    try {
						logger.info { "run #$it ${description.displayName}"}
                        base.evaluate()
                        return
                    } catch (e: Exception) {
						if (e is AssumptionViolatedException) {
							throw e
						}

                        caughtThrowable = e

                        logger.warn("Test {} run {} failed: {}", description.displayName, it + 1, e.message)
                        Thread.sleep(sleepTime)
                    }
                }
                throw caughtThrowable!!
            }
        }
    }
}
