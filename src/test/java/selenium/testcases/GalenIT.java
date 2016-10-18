package selenium.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.MultipleFailureException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.validation.ValidationResult;

import selenium.SeleniumTestWrapper;
import selenium.pageobjects.StartPage;

public class GalenIT extends SeleniumTestWrapper {

	StartPage startPage = PageFactory.initElements(getDriver(), StartPage.class);

	static List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

	@Parameters
	// returns required (CSS) breakpoints and mobile UserAgent
	public static Iterable<Object[]> data() {
		// TODO: change UAs as soon as RWD-pages are reachable via desktop
		// browser
		return Arrays.asList(new Object[][] { { "small", new Dimension(359, 800) },
				{ "medium", new Dimension(599, 800) }, { "large", new Dimension(959, 800) },
				{ "xlarge", new Dimension(1199, 800) }, { "xxlarge", new Dimension(1280, 800) } });
	}

	@Parameter(0)
	public String device;

	@Parameter(1)
	public Dimension screenSize;

	// create error message
	private void failIfErrors(final LayoutReport report) throws Throwable {
		List<Throwable> errors = new ArrayList<Throwable>();
		for (ValidationResult result : report.getValidationErrorResults()) {
			if (!result.getError().isOnlyWarn()) {
				String message = StringUtils.join(result.getError().getMessages(), "\n");
				errors.add(new AssertionError(message));
			}
		}
		MultipleFailureException.assertEmpty(errors);
	}

	private void checkSpecFile(final String SpecName) throws Throwable {
		// link to spec file
		LayoutReport layoutReport = Galen.checkLayout(getDriver(), "src/test/resources/specs/" + SpecName + ".gspec", Arrays.asList(device));
		// create test layout report object for the test report
		GalenTestInfo test = GalenTestInfo.fromString(SpecName + " (" + device + ")");
		// add layout report object to the test report
		test.getReport().layout(layoutReport, SpecName);
		GalenIT.tests.add(test);
		failIfErrors(layoutReport);
	}

	@Ignore
	@Test
	public void checkStartPage() throws Throwable {
		checkSpecFile("StartPage");
	}
}
