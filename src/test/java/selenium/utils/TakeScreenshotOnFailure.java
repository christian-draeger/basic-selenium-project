package selenium.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;

import selenium.driver.WebDriverConfig;

/**
 * Saves a screentshot if a test fails.
 */
public class TakeScreenshotOnFailure extends TestWatcher {
    private final WebDriverProvider webDriverProvider;
    private final WebDriverConfig webDriverConfig;

    public TakeScreenshotOnFailure(final WebDriverProvider webDriverProvider, final WebDriverConfig webDriverConfig) {
        this.webDriverProvider = webDriverProvider;
        this.webDriverConfig = webDriverConfig;
    }

    @Override
    protected void failed(final Throwable e, final Description description) {
        if (this.webDriverProvider.existsDriver()
                && !(e instanceof UnsupportedCommandException)
                && !((e instanceof WebDriverException) && e.getMessage().contains("BROWSER_TIMEOUT"))) {
            takeScreenshot(description);
        }
    }

    private void takeScreenshot(final Description description) {
        File scrFile = this.webDriverProvider.getDriver().getScreenshotAs(OutputType.FILE);
        File destFile = getFileForScreenshot(description);
        try {
            FileUtils.copyFile(scrFile, destFile);
        } catch (final IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public File getFileForScreenshot(final Description description) {
        String buildNumber = getBuildNumber();
        String testName = getClass().getSimpleName() + "-" + description.getMethodName();
        for (int i = 0; ; ++i) {
            File file = new File("./target/screens/" + buildNumber + "/" + testName + "-" + i + ".png");
            if (!file.exists()) {
                return file;
            }
        }
    }

    private String getBuildNumber() {
        String buildNumber = System.getProperty("BUILD_NUMBER"); // Is a Jenkins variable.
        if (buildNumber == null) { // Replace BUILD_NUMBER with date, when tests was started local.
            GregorianCalendar cal = new GregorianCalendar();
            Date now = new Date();
            cal.setTime(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(now);
        } else {
            return buildNumber;
        }
    }
}
