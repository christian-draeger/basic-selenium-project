package config

import mu.KotlinLogging
import org.fluentlenium.adapter.FluentTestRunnerAdapter
import org.fluentlenium.adapter.junit.FluentTestRule
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.RuleChain.outerRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

open class FluentTestWithRetry: FluentTestRunnerAdapter() {

	val logger = KotlinLogging.logger {}

	val retryRule = Retry(5, 3000)

	/**
	 * Fluent test adapter JUnit rule.
	 */
	val watchman: TestRule = object : FluentTestRule(this) {

		public override fun starting(description: Description) {
			logger.info { "starting" }

			super.starting(description)
			this@FluentTestWithRetry.starting(description.testClass, description.displayName)
		}

		public override fun finished(description: Description) {
			logger.info { "finished" }

			super.finished(description)
			this@FluentTestWithRetry.finished(description.testClass, description.displayName)
		}

		public override fun failed(e: Throwable?, description: Description) {
			logger.info { "failed" }

			super.failed(e, description)
			this@FluentTestWithRetry.failed(e, description.testClass, description.displayName)
		}
	}

	@get:Rule
	val ruleChain = outerRule(retryRule)
			.around(watchman)


	companion object {
		/**
		 * Fluent test adapter JUnit class rule.
		 */
		@get:ClassRule
		@JvmStatic
		val classWatchman: TestRule = TestRule { base, description ->
			object : Statement() {
				override fun evaluate() {
					try {
						base.evaluate()
					} finally {
						FluentTestRunnerAdapter.afterClass(description.testClass)
					}
				}
			}
		}
	}
}
