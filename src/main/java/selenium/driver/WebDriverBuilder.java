package selenium.driver;

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

        switch (browser) {
            case "chrome":
                return new ChromeDriver(capabilities);
            case "edge":
                return new EdgeDriver(capabilities);
            case "internetexplorer":
                return new InternetExplorerDriver(capabilities);
            case "opera":
                return new OperaDriver(capabilities);
            case "phantomjs":
                return new PhantomJSDriver(capabilities);
            default:
                return new MarionetteDriver(capabilities);
        }
    }
}
