package selenium.testcases;


import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import selenium.SeleniumTestWrapper;
import selenium.pageobjects.StartPage;
import utils.TestUtils;

public class StartPageIT extends SeleniumTestWrapper {

	StartPage startPage = PageFactory.initElements(getDriver(), StartPage.class);

	@Test
	public void startPageTest() {
		startPage.open();
		TestUtils.sleep(3000);
	}

}