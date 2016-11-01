package selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import selenium.Pages;

public class SearchResultPage extends Pages {

	public SearchResultPage(final WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "q")
	private WebElement searchInput;

	@FindBy(css = "nav.menu a")
	private List<WebElement> sideNavi;

	@FindBy(css = ".counter")
	private WebElement counter;

	@FindBy(css = ".user-list-info")
	private List<WebElement> userList;

	@FindBy(css = ".user-list-info a em")
	private List<WebElement> userListAccountNames;

	public String getInputValue(){
		return searchInput.getAttribute("value");
	}

	public void clickNaviElement(String naviElement) {
		waitForElement(counter, 10);
		for (WebElement key : sideNavi) {
			if (key.getText().contains(naviElement)){
				key.click();
			}
		}
	}

	public String getExpectedResult(){
		return getTestData("search.result");
	}

	public String getRealNames(int index) {
		return userList.get(index).getText();
	}

	public String getAccountNames(int index) {
		waitForElement(userList.get(0));
		return userListAccountNames.get(index).getText();
	}
}
