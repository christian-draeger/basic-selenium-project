package selenium.driver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.phantomjs.PhantomJSDriverService.PHANTOMJS_CLI_ARGS;
import static selenium.driver.DesiredCapabilitiesFactory.initDesiredCapabilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverBuilder {

    private String name;
    private final WebDriverConfig webDriverConfig;

    public WebDriverBuilder(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebDriver toWebDriver() {
        DesiredCapabilities capabilities = initDesiredCapabilities(webDriverConfig);
        String browser = webDriverConfig.getBrowserName();

        if (browser.equalsIgnoreCase("phantomjs")) {
            capabilities.setCapability(PHANTOMJS_CLI_ARGS, new String[] { "--webdriver-loglevel=ERROR" });//NONE,ERROR
            Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.WARNING);
            final PhantomJSDriver phantomJsWebDriver = new PhantomJSDriver(capabilities);
            phantomJsWebDriver.manage().timeouts().implicitlyWait(webDriverConfig.getImplicitlyWait(), SECONDS);
            phantomJsWebDriver.manage().timeouts().setScriptTimeout(webDriverConfig.getDomMaxScriptRunTime(), SECONDS);
            phantomJsWebDriver.manage().window().maximize();
            return phantomJsWebDriver;
        } else if(browser.equalsIgnoreCase("chrome")) {
            return new ChromeDriver(capabilities);
        } else if(browser.equalsIgnoreCase("opera")) {
            return new OperaDriver(capabilities);
        } else {
            return new MarionetteDriver(capabilities);
        }
    }
}
