package config.annotations

import it.skrape.extract
import it.skrape.skrape
import org.junit.jupiter.api.extension.ConditionEvaluationResult
import org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled
import org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled
import org.junit.jupiter.api.extension.ExecutionCondition
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.util.AnnotationUtils.findAnnotation
import java.lang.String.format
import java.lang.reflect.AnnotatedElement
import java.net.UnknownHostException
import kotlin.annotation.AnnotationTarget.*


@Target(FUNCTION, TYPE, CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(EnabledIfReachableCondition::class)
annotation class EnabledIfReachable(val url: String, val timeoutMillis: Int = 1000)

internal class EnabledIfReachableCondition : ExecutionCondition {

    override fun evaluateExecutionCondition(
        context: ExtensionContext
    ): ConditionEvaluationResult {
        val element = context
            .element
            .orElseThrow<IllegalStateException> { IllegalStateException() }
        return findAnnotation(element, EnabledIfReachable::class.java)
            .map { annotation -> disableIfUnreachable(annotation, element) }
            .orElse(ENABLED_BY_DEFAULT)
    }

    private fun disableIfUnreachable(annotation: EnabledIfReachable, element: AnnotatedElement): ConditionEvaluationResult {

        val urlToReach = annotation.url
        val timeoutMillis = annotation.timeoutMillis

        return if (isAvailable(urlToReach, timeoutMillis)) {
            enabled(format("%s is enabled because %s is reachable", element, urlToReach))
        } else {
            disabled(format("%s is disabled because %s could not be reached in %dms", element, urlToReach, timeoutMillis))
        }
    }

    private fun isAvailable(urlToReach: String, timeoutMillis: Int): Boolean {
        return try {
            val statusCode = skrape {
                url = urlToReach
                timeout = timeoutMillis
                extract { statusCode }
            }
            statusCode in 200..399
        } catch (e: UnknownHostException) {
            false
        }
    }

    companion object {
        private val ENABLED_BY_DEFAULT = ConditionEvaluationResult.enabled(
            "@EnabledIfReachable is not present"
        )
    }

}