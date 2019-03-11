>#### This is the legacy version of the basic-selenium-project 
>#### Please use the master branch for a more modern and advanced stack.


[![N|Solid](https://api.travis-ci.org/christian-draeger/basic-selenium-project.svg?branch=legacy)](https://travis-ci.org/christian-draeger/basic-selenium-project)

# basic-selenium-project
This project is written in java and will serve an example of implementing a Selenium test project with Selenium3 and Maven.
Everything is set up and tests can be added straight away.
Used Testrunner is the Failsafe plugin.
To execute the tests just browse to the path where the basic-selenium-project is located in your terminal and type `mvn clean verify -Pbrowser-chrome` or execute the tests in your IDE. maven profiles for all browsers exists in the [pom.xml](https://github.com/christian-draeger/basic-selenium-project/blob/master/pom.xml).

>If you want to make template tests that doesn't need browser interactions like clicking or execution of Javascript just have a look at [Paco Framework](https://github.com/christian-draeger/page-content-tester). It's much much faster then Selenium and less fragile.

## Implemented Browsers
Thanks to the awesome [webdrivermanager](https://github.com/bonigarcia/webdrivermanager) it supports the following browsers and automatically downloads OS specific binaries for:
* Chrome
* Chrome Headless
* Firefox
* Firefox Headless
* Opera
* Internet Explorer
* Edge

#### The Webdriver Setup
The webdriver setup is based on the [WebDriverBuilder](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/main/java/selenium/driver/WebDriverBuilder.java) and the [DesiredCapabilitiesFactory](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/main/java/selenium/driver/DesiredCapabilitiesFactory.java)
to have a separation between driver instantiation and browser specific settings.

## Page Objects Pattern
page object pattern is used to have reusable WebElements/small helper methods separated from actual test classes and give the opportunity to have nice structured and easily readable tests (without the overhead of BDD-Frameworks like Cucumber or JBehave).

## Annotations
Beside a bunch of [Convenient Methods] the basic-selenium-project provides some nice custom annotations to comfortably set some test conditions and/or assumptions
Example test that uses several annotations: [SearchIT.java](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/test/java/selenium/testcases/SearchIT.java)

#### @Browser
The `@Browser` annotation includes or excludes certain browsers from the test execution

skip test if browser equals firefox:
```
@Browser(skip = FIREFOX)
```

it also supports list of browsers, e.g. skip test if browser equals firefox or phantomjs
```
@Browser(skip = { FIREFOX, OPERA })
```

it can be used the other way around as well e.g. if you want a certain test just to be executed with phantomjs you can do something like this:
```
@Browser(require = CHROME)
```
The browser require option is working equivalent to the skip option and also supports list of browsers

#### @BrowserDimension
If you want to test a responsive website it can be handy to set the browsers to some specific viewports.
To configure your breakpoints just edit them in the [test_data.properties](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/test/resources/test_data.properties)

Resizing the browser window for specific tests can be done by e.g.:
``` 
@BrowserDimension(LARGE)
```

#### @UserAgent
UserAgents can be overwritten and give the possibility to emulate the behaviour of an website if special devices visiting it.
For Example if you want to test a mobile switch for devices like smartphones and/or tablets etc.
``` 
@UserAgent(IPHONE_I_OS_9)
```
There already is a list of user agents in the [UserAgents Enum](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/main/java/selenium/driver/UserAgents.java).
All user agents located in that file will can be used with @UserAgent


## Convenient Methods
you can find a couple of convenient methods like waits, isElementPresent/Visible, hover, dragAndDrop, etc. in [SeleniumFunctions.java](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/main/java/selenium/SeleniumFunctions.java) 
as well as helper methods for cookie, localstorage and sessionstorage handling.
Example tests that [do some stuff with cookies](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/test/java/selenium/testcases/DoSomeThingWithCookiesIT.java)
and [do some stuff with localstorage/sessionstorage](https://github.com/christian-draeger/basic-selenium-project/blob/master/src/test/java/selenium/testcases/DoSomeThingWithLocalStorageIT.java)
are added and should show the the usage self-evident.
