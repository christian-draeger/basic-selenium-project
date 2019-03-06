package selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverBuilder {

    private String name;
    private final WebDriverConfig webDriverConfig;
    private String userAgent;
    private boolean disableCookies;

    public WebDriverBuilder(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void userAgent(final UserAgents userAgent) {
        this.userAgent = userAgent.getValue();
    }

    public void disableCookies(boolean cookies) {
        this.disableCookies = cookies;
    }


    public WebDriver toWebDriver() {
        DesiredCapabilitiesFactory desiredCapabilitiesFactory = new DesiredCapabilitiesFactory();
        DesiredCapabilities capabilities = desiredCapabilitiesFactory.initDesiredCapabilities(webDriverConfig);
        String browser = webDriverConfig.getBrowserName();
        WebDriver driver;

        switch (browser) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = desiredCapabilitiesFactory.chromeOptions(capabilities, userAgent, disableCookies);
                driver = new ChromeDriver(options);
                break;
            }
            case "chrome-headless": {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = desiredCapabilitiesFactory.chromeOptions(capabilities, userAgent, disableCookies);
                driver = new ChromeDriver(options.setHeadless(true));
                break;
            }
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions().merge(capabilities);
                driver = new EdgeDriver(options);
                break;
            }
            case "internetexplorer": {
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions options = new InternetExplorerOptions().merge(capabilities);
                driver = new InternetExplorerDriver(options);
                break;
            }
            case "opera": {
                WebDriverManager.operadriver().setup();
                OperaOptions options = desiredCapabilitiesFactory.operaOptions(capabilities, userAgent);
                driver = new OperaDriver(options);
                break;
            }
            case "firefox-headless": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = desiredCapabilitiesFactory.firefoxOptions(capabilities, userAgent);
                driver = new FirefoxDriver(options.setHeadless(true));
                break;
            }
            default:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = desiredCapabilitiesFactory.firefoxOptions(capabilities, userAgent);
                driver = new FirefoxDriver(options);
        }

        driver.manage().window().maximize();
        return driver;
    }
}
