[![Build Status](https://travis-ci.org/christian-draeger/basic-selenium-project.svg?branch=master)](https://travis-ci.org/christian-draeger/basic-selenium-project)

## 🚧🚸 Work in Progress

Basic Selenium Project
===================================

This project is written in **[Kotlin](https://kotlinlang.org)** and will serve an example of implementing a 
Selenium test project with [FluentLenium](https://fluentlenium.com) (Selenium3) and [Gradle](https://gradle.org) (with kotlin DSL).
Everything is set up and tests can be added straight away.
Used Testrunner is JUnit.
_Since Kotlin has an excellent **Java** interop it's perfectly fine to write your Tests in Java if you want, it will work out of the box._
To execute the tests just browse to the path where the selenium-kotlin-example is located via terminal and type `./gradlew clean test` or execute the tests in your IDE.
The Project will use Chrome Browser in Headless mode by default / if no other browser is stated 
(see list of implemented browsers for more info on how to use them).

If you are looking for a pure Java project that uses maven as build tool please have a look at the [legacy branch](https://github.com/christian-draeger/basic-selenium-project/tree/legacy) of this project.


>#### ℹ️ FluentLenium is a website automation framework which extends Selenium to write reliable and resilient UI functional tests. This framework is React ready. Written and maintained by people who are automating browser-based tests on a daily basis.

### Prerequisites
* \>=**JDK11** installed

#### Features:
* [couple of browsers preconfigured](#-implemented-browsers)
    * downloading OS specific binaries automatically
* [custom annotations](#-custom-annotations) 🔜
* [take screenshot on test failure](#-take-screenshots)
* [highlight clicked elements](#-highlight-clicked-elements)
* [pretty and highly readable console output](#-beautiful-console-output)
* [parallel test execution ready](#-parallel-test-execution) 🔜
* [centralized project config](#-centralized-project-config) 🔜
* [retries](#-retries)
* include / exclude test from execution depending on browser 🔜
    * idiomatic usage via provided annotations
* [proxy to intercept / modify / mock http-requests](#-proxy) 🔜
* assertions, waits and test extensions
    * [assertions with selenium specific and type safe matchers](#-assertions)
    * [waiting functions](#-waits)
* [meaningful test result report](#-allure-test-result-report) 🔜
* [template testing](#-template-testing)

## Benefits

#### 💻 Implemented Browsers
Thanks to the awesome [webdrivermanager](https://github.com/bonigarcia/webdrivermanager) it supports the following browsers and automatically downloads OS specific binaries for:
* Chrome Headless (default) `./gradlew clean test`
* Chrome `./gradlew clean test -Dbrowser=chrome`
* Firefox Headless `./gradlew clean test -Dbrowser=firefox-headless`
* Firefox `./gradlew clean test -Dbrowser=firefox`
* Opera `./gradlew clean test -Dbrowser=opera`
* Internet Explorer `./gradlew clean test -Dbrowser=ie` (will work on windows machines only)
* Edge `./gradlew clean test -Dbrowser=ie` (will work on windows machines only)
* Safari `./gradlew clean test -Dbrowser=safari` (will work on OSX machines only)

#### 🔥 Custom Annotations
##### (in progress, not implemented yet)
The project includes custom annotations to comfortably set some test conditions and/or assumptions
like skip/require certain tests on execution with specific browsers and/or override driver options like browser dimension, headers, cookies, etc.
This will increase the possibility to write easily readable and flexible tests.

#### 📸 Take Screenshots
On test failures screenshots will automatically be taken and stored under `build/screenshots`.
The screenshot files will be named with a combination of the class name and the test method name.

#### 📍 Highlight Clicked Elements
When clicking an element it will be highlighted with a red border. This is helpful to easily understand what 
a certain test is doing while watching a test run.

#### 📟 Beautiful Console Output
The console output is more intuitive and better readable as the default one of Gradle, jUnit and Selenium.
A colored console output will give you a clear overview about which tests are currently running.
Furthermore obvious markers will be set at succeeded (green marker) and failed (red marker) tests.  
To get an even more clear overview of the test execution the project uses the gradle TestLoggerPlugin to pretty print executed tests.

#### 👩‍👩‍👦‍👦 Parallel Test Execution
##### (in progress, not implemented yet)
The Project is preconfigured to run the tests in parallel.
The number of test that will be executed at the same time is configurable (defaults to 4) or can be deactivated if required.

#### 🎯 Centralized Project Config
##### (in progress, not implemented yet)
All global configurations are living in a properties file (`resources/config.properties`) and can be adjusted.
It gives you the possibility to edit the global project behaviour in one place without messing around with project/setup specific code.
Furthermore all properties can be overridden via system properties.

#### 🔁 Retries
Conveniently run a single Test Multiple Times by using the implemented Test Rule.
It's possible to rerun failing tests automatically and mark them as flaky.

#### ⛩️ Proxy
##### (in progress, not implemented yet)
The [BrowserMob Proxy](https://github.com/lightbody/browsermob-proxy) is already implemented and can be used to Mock External Requests.
This is especially helpful to mock dynamic data on the page under test, modify parts of the request that are not possible with some browsers (like setting custom headers in Internet Explorer) as well as speeding up 
your tests by mocking thinks that are out of scope of a certain test (for instance tracking scripts).

#### 🚨 Assertions
Fluentlenium extends AssertJ with FluentWebElement, FluentList and FluentPage custom assertions.
Therefore you'll be able to write more intuitive and selenium specific assertions to give you the possibility to easily assert things like if an element is displayed etc.

#### ⏱️ Waits
Testing web applications that are asynchroniously loading / rerendering parts of the page can become hard to test with Selenium. 
[Awaitility](https://github.com/awaitility/awaitility) is a DSL that allows you to express expectations of an asynchronous system in a concise and easy to read manner and is therefore added to this project.

#### 📊 Allure Test Result Report
##### (in progress, not implemented yet)
[Allure](http://allure.qatools.ru) provides a good representation of test execution output and is designed to create 
reports that are clear to everyone by creating graphs regarding test execution time, 
overall test result overviews, test result history, etc.

#### 🚀 Template Testing
From time to time we are writing tests that doesn't need browser interactions like clicking or 
execution of Javascript. We'll use template testing using [skrape{it}](https://docs.skrape.it/docs/) 
to achieve these types of tests because it's much much faster and more robust then Selenium. 
Please have a look at the [example test](https://github.com/christian-draeger/selenium-kotlin-example/blob/13c75c3a86be3b09eabf7f70a6b92c5451f95c9d/src/test/kotlin/tests/ExampleTemplateIT.kt)
