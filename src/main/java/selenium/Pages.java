package selenium;

import org.openqa.selenium.WebDriver;

import selenium.configurations.TypedProperties;

public abstract class Pages extends SeleniumFunctions {

	public Pages(final WebDriver driver) {
		super(driver);
	}

	private String baseUrl = new TypedProperties("/test_config.properties").getValue("base_url");

	protected void open(String path){
		driver.get(baseUrl + path);
	}

	protected void open(){
		driver.get(baseUrl);
	}

}
