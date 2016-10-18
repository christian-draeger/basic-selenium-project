package selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class SeleniumFunctions {

	protected WebDriver driver;

	public SeleniumFunctions(final WebDriver driver) {
		this.driver = driver;
	}

	// Methods

	protected void clickWebElement(WebElement element) {
		element.click();
	}

	protected void clickWebElement(List<WebElement> element, int index) {
		element.get(index).click();
	}

	public boolean isWebElementContainingSpecificText(WebElement element, String text) {
		String webElementText = getWebElementText(element);
		return webElementText.contains(text);
	}

	public String getWebElementText(WebElement element) {
		return element.getText();
	}

	public String getWebElementText(List<WebElement> element, int index) {
		return element.get(index).getText();
	}

	public List<String> getWebElementTextAndReturnAsAStringList(List<WebElement> element) {
		List<String> webElementListToReturn = new ArrayList<String>();
		for (WebElement webElement : element) {
			webElementListToReturn.add(webElement.getText());
		}
		return webElementListToReturn;
	}

	public boolean isWebElementAttributeTitleContainsThisText(WebElement element, String thisText) {
		String webElementAttributeTitleText = getAttributTitleFromWebElement(element);
		return webElementAttributeTitleText.contains(thisText);
	}

	public String getAttributTitleFromWebElement(WebElement element) {
		return element.getAttribute("title");
	}

	public boolean checkVisibilityOfListElements(List<WebElement> elementList){
		boolean isVisibleReturn = true;
		for (int i = 0; i < elementList.size(); i++) {
			boolean isVisible = elementList.get(i).isDisplayed();
			if (!isVisible) {
				isVisibleReturn = false;
			}
			System.out.println(i + "-->: " + isVisible + "     --> " + elementList.get(i));
		}
		return isVisibleReturn;
	}

}
