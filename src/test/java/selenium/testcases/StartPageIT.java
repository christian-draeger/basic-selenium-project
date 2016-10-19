package selenium.testcases;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static selenium.pageobjects.StartPage.Navi.PERSONAL;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import selenium.SeleniumTestWrapper;
import selenium.pageobjects.StartPage;

public class StartPageIT extends SeleniumTestWrapper {

	StartPage startPage = PageFactory.initElements(getDriver(), StartPage.class);

	@Test
	public void checkPageTest() {
		startPage.open();
		startPage.clickNavigation(PERSONAL);
		assertThat(getDriver().getCurrentUrl(), containsString("personal"));
	}
}