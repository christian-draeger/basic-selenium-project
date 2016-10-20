package selenium.driver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static selenium.driver.DesiredCapabilitiesFactory.initDesiredCapabilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverBuilder {

    private String name;
    private String userAgent;
    private final WebDriverConfig webDriverConfig;

    public WebDriverBuilder(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void userAgent(final UserAgents userAgent) {
        this.userAgent = userAgent.getValue();
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebDriver toWebDriver() {
        DesiredCapabilities capabilities = initDesiredCapabilities(webDriverConfig);
        String browser = webDriverConfig.getBrowserName();

        switch (browser) {
            case "chrome":
                final ChromeDriver chromeDriver = new ChromeDriver();
                chromeDriver.manage().window().maximize();
                return chromeDriver;
            case "edge":
                final EdgeDriver edgeDriver = new EdgeDriver();
                edgeDriver.manage().window().maximize();
                return edgeDriver;
            case "internetexplorer":
                final InternetExplorerDriver internetExplorerDriver = new InternetExplorerDriver();
                internetExplorerDriver.manage().window().maximize();
                return internetExplorerDriver;
            case "opera":
                final OperaDriver operaDriver = new OperaDriver();
                operaDriver.manage().window().maximize();
                return operaDriver;
            case "phantomjs":
                final PhantomJSDriver phantomJsWebDriver = new PhantomJSDriver(capabilities);
                phantomJsWebDriver.manage().timeouts().implicitlyWait(webDriverConfig.getImplicitlyWait(), SECONDS);
                phantomJsWebDriver.manage().timeouts().setScriptTimeout(webDriverConfig.getDomMaxScriptRunTime(), SECONDS);
                phantomJsWebDriver.manage().window().maximize();
                return phantomJsWebDriver;
            default:
                return new MarionetteDriver(capabilities);
        }
    }
}
