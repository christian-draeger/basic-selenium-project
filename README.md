[![Travis](http://badges.herokuapp.com/travis/christian-draeger/basic-selenium-project?env=BROWSER=firefox&label=Firefox&branch=master)](https://travis-ci.org/christian-draeger/basic-selenium-project)

[![Travis](http://badges.herokuapp.com/travis/christian-draeger/basic-selenium-project?env=BROWSER=googlechrome&label=Chrome&branch=master)](https://travis-ci.org/christian-draeger/basic-selenium-project)

[![Travis](http://badges.herokuapp.com/travis/christian-draeger/basic-selenium-project?env=BROWSER=safari&label=Safari&branch=master)](https://travis-ci.org/christian-draeger/basic-selenium-project)

[![AppVeyor](https://img.shields.io/appveyor/ci/christian-draeger/basic-selenium-project.svg?label=IE11)](https://ci.appveyor.com/project/christian-draeger/basic-selenium-project)

Basic Selenium Project
===================================

This scaffold project is written in **[Kotlin](https://kotlinlang.org)** and will serve an example of implementing a 
Selenium test project with [FluentLenium](https://fluentlenium.com) (Selenium3) and [Gradle](https://gradle.org) (with kotlin DSL).
Everything is set up and tests can be added straight away.
Used Testrunner is [JUnit 5](https://junit.org/junit5).
_Since Kotlin has an excellent **Java** interop it's perfectly fine to write your Tests in Java if you want, it will work out of the box._
To execute the tests just browse to the path where the selenium-kotlin-example is located via terminal and type `./gradlew clean test` or execute the tests in your IDE.
The Project will use Chrome Browser in Headless mode by default / if no other browser is stated 
(see list of implemented browsers for more info on how to use them).

If you are looking for a pure Java project that uses maven as build tool please have a look at the [legacy branch](https://github.com/christian-draeger/basic-selenium-project/tree/legacy) of this project.


>#### ℹ️ FluentLenium is a website automation framework which extends Selenium to write reliable and resilient UI functional tests. This framework is React ready. Written and maintained by people who are automating browser-based tests on a daily basis.

### Prerequisites
* \>=**JDK8** installed

#### Features:
* [all popular browsers preconfigured](#-implemented-browsers)
    * downloading OS specific binaries automatically
* [full control by annotations](#-full-control-over-certain-test-methods-and-classes-by-annotations)
* [page object pattern ready](#-page-object-pattern-ready)
* [take screenshot on test failure](#-take-screenshots)
* [highlight clicked elements](#-highlight-clicked-elements)
* [pretty and highly readable console output](#-beautiful-console-output)
* [parallel test execution ready](#-parallel-test-execution)
* [centralized project config](#-centralized-project-config)
* [retries](#-retries)
* [proxy to intercept / modify / mock http-requests](#-proxy) 🔜
* assertions, waits and test extensions
    * [assertions with selenium specific and type safe matchers](#-assertions)
    * [waiting functions](#-waits)
* [meaningful test result report](#-allure-test-result-report)
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
* Edge `./gradlew clean test -Dbrowser=edge` (will work on windows machines only)
* Safari `./gradlew clean test -Dbrowser=safari` (will work on OSX machines only)

---

#### 🕹️ Full control over certain test methods and classes by annotations
The project includes custom annotations to comfortably set some test conditions and/or assumptions
like skip/require certain tests on execution with specific browsers and/or override driver options like browser dimension, headers, cookies, etc.
This will increase the possibility to write easily readable and flexible tests.

##### @Browser
Overwrite used (default) browser by annotating test classes or test methods with:

    @Browser(use = FIREFOX)
    
This will always execute the annotated tests with the selected browser, no matter what has been set as default browser. 
See the full list of possible [parameter values](https://github.com/christian-draeger/basic-selenium-project/blob/8d6d025ec895d831e76b9013c1648307edf0756f/src/test/java/config/driver/DriverFactory.kt#L115).

Furthermore you can conveniently set the Browser windows dimension that is used for the test by setting the dimension field:

    @Browser(dimension = XLARGE)
    
This will lead to a window resize before the actual test starts and is especially helpful if the 
site under test relies on a responsive web design. 
See the full list of [possible dimensions](https://github.com/christian-draeger/basic-selenium-project/blob/8d6d025ec895d831e76b9013c1648307edf0756f/src/test/java/config/driver/WindowManager.kt#L8).
The specific values of the breakpoints can be configured in the [config.properties](https://github.com/christian-draeger/basic-selenium-project/blob/8d6d025ec895d831e76b9013c1648307edf0756f/src/test/resources/config.properties) file.

##### @EnabledOnOs
You can control that a test will ONLY be executed on specific operating systems.
(works on class and method level)

    @EnabledOnOs(LINUX, WINDOWS)

If a test is annotated with `@EnabledOnOs` and the current OS the tests gets executed on is not matching, they will be skipped.

##### @DisabledOnJre
You can control that a test will be skipped if running on specific JRE(s).

    @DisabledOnJre(JAVA_8, JAVA_9)
    
If a test is annotated with `@DisabledOnJre` it will be skipped if tests are running on specified JRE(s).

##### @EnabledIfSystemProperty
Gives control over test execution relying on system properties. The following example will execute the test only
if the current OS is a 64bit system. But it could be any either provided or self defined system property.

    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    
If a test is annotated with `@EnabledIfSystemProperty` it will ONLY be executed if the specified system property (field `named`)
will match the provided regex (field `matches`), otherwise the test will be skipped.

##### @EnabledIfEnvironmentVariable
Gives control over test execution relying on environment variables. The following example will execute the test only
if the environment variable 'ENV' will be present and it's value matches 'ci'. But it could be any environment variable and regex match combination.

    @EnabledIfEnvironmentVariable(named = "ENV", matches = "ci")
    
If a test is annotated with `@EnabledIfEnvironmentVariable` it will ONLY be executed if the specified system property (field `named`)
will match the provided regex (field `matches`), otherwise the test will be skipped.

---

#### 📜 Page Object Pattern ready
The Page-Object-Pattern can be used straight away to specify elements etc.
It will have out-of-the-box support for typical helper methods like `isAt()`, etc...
To instantiate a page object in a test class just the the following:

[kotlin example](https://github.com/christian-draeger/basic-selenium-project/blob/90085a3e77d0f8af9e3990fb2512a0e52255cecc/src/test/java/tests/browser/ExampleIT.kt#L29):

    @Page
    lateinit var page: StartPage

[java example](https://github.com/christian-draeger/basic-selenium-project/blob/90085a3e77d0f8af9e3990fb2512a0e52255cecc/src/test/java/tests/browser/JavaExampleIT.java#L28):

    @Page
    private StartPage startPage;

---

#### 📸 Take Screenshots
On test failures screenshots will automatically be taken and stored under `build/screenshots`.
The screenshot files will be named with a combination of the class name and the test method name.

---

#### 📍 Highlight Clicked Elements
When clicking an element it will be highlighted with a red border. This is helpful to easily understand what 
a certain test is doing while watching a test run. This functionality is working because the project is implementing an event firing webdriver. 
Therefore you have the possibility to hook into a bunch of driver events and do custom stuff if you want to, e.g.:

* `beforeClickOn` / `afterClickOn`
* `beforeNavigateTo` / `afterNavigateTo`
* `beforeFindBy` / `afterFindBy`
* `beforeScript` / `afterScript`
* `beforeGetText` / `afterGetText`
* `register` / `unregister`
* `onException`
* ... and more

---

#### 📟 Beautiful Console Output
The console output is more intuitive and better readable as the default one of Gradle, jUnit and Selenium.
A colored console output will give you a clear overview about which tests are currently running.
Furthermore obvious markers will be set at succeeded (green marker) and failed (red marker) tests.  
To get an even more clear overview of the test execution the project uses the gradle TestLoggerPlugin to pretty print executed tests.

---

#### 👩‍👩‍👦‍👦 Parallel Test Execution
The Project is preconfigured to run the tests in parallel.
The number of test that will be executed at the same time is configurable (defaults to 4) or can be deactivated if required.

---

#### 🎯 Centralized Project Config
All global configurations are living in a properties file (`resources/config.properties`) and can be adjusted.
It gives you the possibility to edit the global project behaviour in one place without messing around with project/setup specific code.
Furthermore all properties can be overridden via system properties.

---

#### 🔁 Retries
Conveniently run a single Test Multiple Times.
It's possible to rerun failing tests automatically when they are flaky with the following options:

* rerun a certain number of times if a test fails
    * usage: annotate test with `@RepeatedIfExceptionsTest(repeats = 3)` instead of `@Test`
* rerun a certain number of times if a test fails because of a certain exception
    * usage in kotlin: annotate test with `@RepeatedIfExceptionsTest(repeats = 2, exceptions = [NoSuchElementException::class])` instead of `@Test`
    * usage in java: annotate test with `@RepeatedIfExceptionsTest(repeats = 2, exceptions = NoSuchElementException.class)` instead of `@Test`
* supports display name formatting for tests that have to rerun
    * usage: `@RepeatedIfExceptionsTest(repeats = 2, name = "Rerun failed test. Attempt {currentRepetition} of {totalRepetitions}")
* includes feature to repeat certain test a bunch of times and expect to succeed a certain number of times`
    * usage: `@RepeatedIfExceptionsTest(repeats = 100, minSuccess = 4)`

---

#### ⛩️ Proxy
##### (in progress, not implemented yet)
The [BrowserMob Proxy](https://github.com/lightbody/browsermob-proxy) is already implemented and can be used to Mock External Requests.
This is especially helpful to mock dynamic data on the page under test, modify parts of the request that are not possible with some browsers (like setting custom headers in Internet Explorer) as well as speeding up 
your tests by mocking thinks that are out of scope of a certain test (for instance tracking scripts).

---

#### 🚨 Assertions
Fluentlenium extends AssertJ with FluentWebElement, FluentList and FluentPage custom assertions.
Therefore you'll be able to write more intuitive and selenium specific assertions to give you the possibility to easily assert things like if an element is displayed etc.

---

#### ⏱️ Waits
Testing web applications that are asynchroniously loading / rerendering parts of the page can become hard to test with Selenium. 
[Awaitility](https://github.com/awaitility/awaitility) is a DSL that allows you to express expectations of an asynchronous system in a concise and easy to read manner and is therefore added to this project.

---

#### 📊 Allure Test Result Report
[Allure](http://allure.qatools.ru) provides a good representation of test execution output and is designed to create 
reports that are clear to everyone by creating graphs regarding test execution time, 
overall test result overviews, test result history, etc.

---

#### 🚀 Template Testing
From time to time we are writing tests that doesn't need browser interactions like clicking or 
execution of Javascript. We'll use template testing using [skrape{it}](https://docs.skrape.it/docs/) 
to achieve these types of tests because it's much much faster and more robust then Selenium. 
Please have a look at the [example test](https://github.com/christian-draeger/selenium-kotlin-example/blob/13c75c3a86be3b09eabf7f70a6b92c5451f95c9d/src/test/kotlin/tests/ExampleTemplateIT.kt)

#### Maintenance
#### check for dependency updates

    ./gradlew dependencyUpdates

____________________
____________________

Hit count:

![HitCount](http://hits.dwyl.io/christian-draeger/basic-selenium-project.svg)