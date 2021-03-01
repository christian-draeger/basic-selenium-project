import com.adarshr.gradle.testlogger.TestLoggerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("com.adarshr.test-logger") version "2.1.1"
    id("io.qameta.allure") version "2.8.1"
    id("com.github.ben-manes.versions") version "0.36.0"
}

group = "io.github.christian-draeger"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(14))
    }
}

apply<TestLoggerPlugin>()
val isIdea = System.getProperty("idea.version") != null
testlogger {
    setTheme(if (isIdea) "plain" else "mocha-parallel")
    slowThreshold = 6000
    showStandardStreams = true
}

dependencies {
    val fluentleniumVersion = "4.6.1"
    val seleniumVersion = "3.141.59"
    val webdriverManagerVersion = "4.3.1"
    val browsermobVersion = "2.1.5"
    val skrapeitVersion = "1.0.0-alpha8"
    val jUnitVersion = "5.5.2"
    val assertjVersion = "3.19.0"
    val striktVersion = "0.28.2"
    val awaitilityVersion = "4.0.3"
    val rerunnerVersion = "2.1.6"
    val kotlinLoggerVersion = "2.0.4"
    val julToSlf4jVersion = "1.7.28"

    testImplementation(
        group = "org.seleniumhq.selenium",
        name = "selenium-java",
        version = seleniumVersion
    )
    testImplementation(
        group = "io.github.bonigarcia",
        name = "webdrivermanager",
        version = webdriverManagerVersion
    )
    testImplementation(
        group = "net.lightbody.bmp",
        name = "browsermob-proxy",
        version = browsermobVersion
    )
    testImplementation(
        group = "it.skrape",
        name = "skrapeit-core",
        version = skrapeitVersion
    )

    testImplementation(
        group = "org.assertj",
        name = "assertj-core",
        version = assertjVersion
    )
    testImplementation(
        group = "org.fluentlenium",
        name = "fluentlenium-junit-jupiter",
        version = fluentleniumVersion
    )
    testImplementation(
        group = "org.fluentlenium",
        name = "fluentlenium-assertj",
        version = fluentleniumVersion
    )
    testImplementation(
        group = "io.strikt",
        name = "strikt-core",
        version = striktVersion
    )
    testImplementation(
        group = "org.junit.jupiter",
        name = "junit-jupiter",
        version = jUnitVersion
    )
    testImplementation(
        group = "io.github.artsok",
        name = "rerunner-jupiter",
        version = rerunnerVersion
    )
    testImplementation(
        group = "org.awaitility",
        name = "awaitility-kotlin",
        version = awaitilityVersion
    )
    testImplementation(
        group = "io.github.microutils",
        name = "kotlin-logging",
        version = kotlinLoggerVersion
    )
    testImplementation(
        group = "org.slf4j",
        name = "jul-to-slf4j",
        version = julToSlf4jVersion
    )
}

configurations {
    all {
        exclude(module = "junit")
        exclude(module = "htmlunit-driver")
        exclude(module = "slf4j-log4j12")
        exclude(module = "slf4j-simple")
        exclude(module = "log4j")
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        parallelTestExecution()

        systemProperty("browser", System.getProperty("browser"))

        finalizedBy("allureReport")
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            apiVersion = "1.4"
            languageVersion = "1.4"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

fun Test.parallelTestExecution() {
    val parallel = "junit.jupiter.execution.parallel"
    if (!project.hasProperty("serial")) {
        systemProperties = mapOf(
            "$parallel.enabled" to true,
            "$parallel.mode.default" to "concurrent",
            "$parallel.config.dynamic.factor" to 4
        )
    }
}

allure {
    val allureVersion = "2.13.7"
    version = allureVersion
    autoconfigure = true
    aspectjVersion = "1.9.6"
    useJUnit5 {
        version = allureVersion
    }
}
