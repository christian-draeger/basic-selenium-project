package selenium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.MultipleFailureException;
import org.openqa.selenium.Dimension;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.validation.ValidationResult;

import utils.TestUtils;

/**
 * Check RWD Layouts on all page templates with Galen Framework
 */
@RunWith(Parameterized.class)
public class LayoutTestSetup extends SeleniumTestWrapper {

    static List<GalenTestInfo> tests = new LinkedList<>();

    @Parameters(name="test for {0} viewport {1}")
    // returns required (CSS) breakpoints and mobile UserAgent
    public static Iterable<Object[]> data() {
        // TODO: change UAs as soon as RWD-pages are reachable via desktop browser
        return Arrays.asList(new Object[][] {
            { Screen.SMALL.name, Screen.SMALL.dimension },
            { Screen.MEDIUM.name, Screen.MEDIUM.dimension },
            { Screen.LARGE.name, Screen.LARGE.dimension },
            { Screen.XLARGE.name, Screen.XLARGE.dimension },
            { Screen.XXLARGE.name, Screen.XXLARGE.dimension }
        });
    }

    @Parameter
    public String device;

    @Parameter(1)
    public Dimension screenSize;

    @Before
    public void init() throws IOException {
        getDriver().manage().window().setSize(screenSize);
        TestUtils.sleep(500); // wait until window is resized
    }

    protected boolean isSmall() {
        return device.equals(Screen.SMALL.name);
    }

    protected boolean isMedium() {
        return device.equals(Screen.MEDIUM.name);
    }

    protected boolean isLarge() {
        return device.equals(Screen.LARGE.name);
    }

    protected boolean isXLarge() {
        return device.equals(Screen.XLARGE.name);
    }

    protected boolean isXXLarge() {
        return device.equals(Screen.XXLARGE.name);
    }

    protected void checkSpecFile(final String SpecName) throws Exception {
        // link to spec file
        LayoutReport layoutReport = Galen.checkLayout(getDriver(), "src/test/resources/specs/" + SpecName + ".gspec", Arrays.asList(device));

        // create test layout report object for the test report
        GalenTestInfo test = GalenTestInfo.fromString(SpecName + " (" + device + ")");

        // add layout report object to the test report
        test.getReport().layout(layoutReport, SpecName);
        LayoutTestSetup.tests.add(test);

        failIfErrors(layoutReport);
    }

    // create error message
    private void failIfErrors(final LayoutReport report) throws Exception {
        List<Throwable> errors = new ArrayList<>();

        for (ValidationResult result : report.getValidationErrorResults()) {
            if (!result.getError().isOnlyWarn()) {
                String message = StringUtils.join(result.getError().getMessages(), "\n");
                errors.add(new AssertionError(message));
            }
        }

        MultipleFailureException.assertEmpty(errors);
    }

    @AfterClass
    // export test report to html page into target
    public static void tearDown() throws IOException {
        new HtmlReportBuilder().build(LayoutTestSetup.tests, "target/galen-html-reports-mobil-idealo-de");
    }
}
