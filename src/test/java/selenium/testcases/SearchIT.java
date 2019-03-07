package selenium.testcases;


import static org.assertj.core.api.Assertions.assertThat;
import static selenium.utils.annotations.browser.Browsers.EDGE;
import static selenium.utils.annotations.browser.Browsers.INTERNET_EXPLORER;
import static selenium.utils.browser.Screen.XLARGE;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import selenium.SeleniumTestWrapper;
import selenium.pageobjects.HeaderSearch;
import selenium.pageobjects.SearchResultPage;
import selenium.pageobjects.StartPage;
import selenium.utils.annotations.browser.Browser;
import selenium.utils.annotations.browser.BrowserDimension;

@BrowserDimension(XLARGE)
@Browser(skip = { INTERNET_EXPLORER, EDGE })
public class SearchIT extends SeleniumTestWrapper {

	private StartPage startPage = PageFactory.initElements(getDriver(), StartPage.class);
	private HeaderSearch search = PageFactory.initElements(getDriver(), HeaderSearch.class);
	private SearchResultPage searchResultPage = PageFactory.initElements(getDriver(), SearchResultPage.class);

	@Before
	public void setup() {
		startPage.open();
	}

	@Test
	public void exampleTestForUserSearch() {

		search.searchFor(search.getSearchString());

		// check for correct search value
		assertThat(search.getSearchString()).isEqualTo(searchResultPage.getInputValue());

		searchResultPage.clickNaviElement("Users");

		// check account name is in hit list
		assertThat(searchResultPage.getAccountNames()).contains(search.getSearchString());

	}
}
