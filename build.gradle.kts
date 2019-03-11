import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.TestLoggerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.20"
    id("com.adarshr.test-logger") version "1.6.0"
}

group = "io.github.christian-draeger"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

apply<TestLoggerPlugin>()
configure<TestLoggerExtension> {
    setTheme("mocha-parallel")
    slowThreshold = 6000
}

dependencies {
    val fluentleniumVersion = "3.7.1"
    val seleniumVersion = "3.141.59"
    val webdriverManagerVersion = "3.3.0"
    val browsermobVersion = "2.1.5"

    implementation(kotlin("stdlib-jdk8"))

    testCompile("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testCompile("io.github.bonigarcia:webdrivermanager:$webdriverManagerVersion")
    testCompile("net.lightbody.bmp:browsermob-core:$browsermobVersion")
    testCompile("it.skrape:core:0.3.1")

    testCompile(kotlin("test-junit"))
    testCompile("org.assertj:assertj-core:3.12.0")
    testCompile("org.fluentlenium:fluentlenium-junit:$fluentleniumVersion")
    testCompile("org.fluentlenium:fluentlenium-assertj:$fluentleniumVersion")

    testCompile("org.awaitility:awaitility-kotlin:3.1.6")

    testCompile("io.github.microutils:kotlin-logging:1.6.25")
}

tasks {
    val test by getting(Test::class) {
        systemProperty("browser", System.getProperty("browser"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}