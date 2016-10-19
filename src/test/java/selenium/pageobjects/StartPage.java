package selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import selenium.Pages;

public class StartPage extends Pages {

	public StartPage(final WebDriver driver) {
		super(driver);
	}

	public void open(){
		super.open();
	}

	@FindBy(css = ".header-logo-invertocat")
	private WebElement logo;

	@FindBy(css = "nav a")
	private List<WebElement> navis;

	public enum Navi {
		PERSONAL("Personal"),
		OPEN_SOURCE("Open source"),
		BUSINESS("Business"),
		EXPLORE("Explore");

		private String value;

		Navi(final String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public void clickNavigation(Navi someNavbarElement) {
		for (WebElement navi : navis) {
			if (navi.getText().equalsIgnoreCase(someNavbarElement.getValue())){
				navi.click();
			}
			break;
		}
	}
}
