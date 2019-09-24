import com.adarshr.gradle.testlogger.TestLoggerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
    id("com.adarshr.test-logger") version "1.7.0"
    id("io.qameta.allure") version "2.8.1"
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
    // see: https://github.com/FluentLenium/FluentLenium/releases/tag/v3.8.1
    val fluentleniumVersion = "3.8.1"
    val seleniumVersion = "3.141.59"
    val webdriverManagerVersion = "3.7.1"
    val browsermobVersion = "2.1.5"
    val skrapeitVersion = "0.6.0"
    val jUnitVersion = "5.5.2"
    val assertjVersion = "3.13.2"
    val striktVersion = "0.22.1"
    val awaitilityVersion = "4.0.1"
    val rerunnerVersion = "2.1.3"
    val kotlinLoggerVersion = "1.7.6"
    val julToSlf4jVersion = "1.7.28"

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
    val allureVersion = "2.13.0"
    version = allureVersion
    autoconfigure = true
    aspectjVersion = "1.9.4"
    useJUnit5 {
        version = allureVersion
    }
}
