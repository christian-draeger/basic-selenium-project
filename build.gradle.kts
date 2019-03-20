
import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.TestLoggerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.20"
    id("com.adarshr.test-logger") version "1.6.0"
    id("io.qameta.allure") version "2.7.0"
}

group = "io.github.christian-draeger"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

apply<TestLoggerPlugin>()
configure<TestLoggerExtension> {
    setTheme("mocha-parallel")
    slowThreshold = 6000
    showStandardStreams = true
}

dependencies {
    // use backported fluentlenium version (3.8.1) to support jdk 8
    // it contains all features from the 4.x.x version
    // see: https://github.com/FluentLenium/FluentLenium/releases/tag/v3.8.1
    val fluentleniumVersion = "3.8.1"
    val seleniumVersion = "3.141.59"
    val webdriverManagerVersion = "3.3.0"
    val browsermobVersion = "2.1.5"

    implementation(kotlin("stdlib-jdk8"))

    testCompile("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testCompile("io.github.bonigarcia:webdrivermanager:$webdriverManagerVersion")
    testCompile("net.lightbody.bmp:browsermob-core:$browsermobVersion")
    compile("it.skrape:core:0.3.1")

    testCompile("org.assertj:assertj-core:3.12.0")
    testCompile("org.fluentlenium:fluentlenium-junit-jupiter:$fluentleniumVersion")
    testCompile("org.fluentlenium:fluentlenium-assertj:$fluentleniumVersion")
    testCompile("org.junit.jupiter:junit-jupiter:5.4.0")

    testCompile("org.awaitility:awaitility-kotlin:3.1.6")

    testCompile(group = "io.github.microutils", name = "kotlin-logging", version = "1.6.25")
    testCompile(group = "org.slf4j", name = "jul-to-slf4j", version = "1.7.26")

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
    autoconfigure = true
    version = "2.7.0"

    useJUnit5 {
        version = "2.7.0"
    }
}
