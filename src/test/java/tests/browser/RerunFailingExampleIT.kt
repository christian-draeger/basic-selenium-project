package tests.browser

import io.github.artsok.RepeatedIfExceptionsTest
import org.junit.jupiter.api.DisplayName

class RerunFailingExampleIT {

    @RepeatedIfExceptionsTest(repeats = 3)
    fun `will be repeated max three times if test failed`() {
        // throw Exception("Error in Test") would lead to repeatition of test
    }

    @RepeatedIfExceptionsTest(repeats = 2, exceptions = [NoSuchElementException::class])
    fun `will only be repeated on certain exception`() {
        // throw NoSuchElementException("Error in Test") would lead to repeatition of test
    }

    @RepeatedIfExceptionsTest(
        repeats = 10,
        exceptions = [NoSuchElementException::class],
        name = "Rerun failed test. Attempt {currentRepetition} of {totalRepetitions}"
    )
    fun `demonstrate how to use display name formatter`() {}

    @DisplayName("Test Case Name")
    @RepeatedIfExceptionsTest(repeats = 100, minSuccess = 4)
    fun `repeat certain amount of times and expect to succeed a minimum number of times`() {}
}