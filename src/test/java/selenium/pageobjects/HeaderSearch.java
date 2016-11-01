package selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import selenium.Pages;

public class HeaderSearch extends Pages {

	public HeaderSearch(final WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input.header-search-input")
	WebElement searchInput;

	public void searchFor(String searchString){
		searchInput.sendKeys(searchString);
		searchInput.submit();
	}

	public String getSearchString(){
		return getTestData("search.string");
	}
}
