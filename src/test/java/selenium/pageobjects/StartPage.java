package selenium.pageobjects;

import org.openqa.selenium.WebDriver;

import selenium.SeleniumFunctions;

public class StartPage extends SeleniumFunctions {

	public StartPage(final WebDriver driver) {
		super(driver);
	}

	public void open(){
		driver.get("http://github.com");
	}
	
}
