import com.adarshr.gradle.testlogger.TestLoggerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    id("com.adarshr.test-logger") version "1.7.1"
    id("io.qameta.allure") version "2.8.1"
    id("com.github.ben-manes.versions") version "0.36.0"
}

group = "io.github.christian-draeger"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
}

apply<TestLoggerPlugin>()
val isIdea = System.getProperty("idea.version") != null
testlogger {
    setTheme(if (isIdea) "plain" else "mocha-parallel")
    slowThreshold = 6000
    showStandardStreams = true
}

dependencies {
    // use backported fluentlenium version (3.8.1) to support jdk 8
    // it contains all features from the 4.x.x version
    // see: https://github.com/FluentLenium/FluentLenium/releases/tag/v3.10.1
    val fluentleniumVersion = "3.10.1"
    val seleniumVersion = "3.141.59"
    val webdriverManagerVersion = "4.2.2"
    val browsermobVersion = "2.1.5"
    val skrapeitVersion = "0.6.0"
    val jUnitVersion = "5.7.0"
    val assertjVersion = "3.18.1"
    val striktVersion = "0.28.1"
    val awaitilityVersion = "4.0.3"
    val rerunnerVersion = "2.1.6"
    val kotlinLoggerVersion = "1.12.0"
    val julToSlf4jVersion = "1.7.30"

    implementation(kotlin("stdlib-jdk8"))

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
    // We need to stick with jsoup 1.11.3 as long as we are using skrape.it in version 0.6.*
    testImplementation("org.jsoup:jsoup") {
        version {
            strictly("1.11.3")
        }
    }
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
        kotlinOptions.jvmTarget = "1.8"
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
