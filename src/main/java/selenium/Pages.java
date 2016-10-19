package selenium;

import org.openqa.selenium.WebDriver;

import selenium.configurations.TypedProperties;

public abstract class Pages {

	protected WebDriver driver;

	public Pages(final WebDriver driver) {
		this.driver = driver;
	}

	private String baseUrl = new TypedProperties("/test_config.properties").getValue("base_url");

//	public Pages(final WebDriver driver) {
//		super(driver);
//	}

	protected void open(String path){
		driver.get(baseUrl + path);
	}

	protected void open(){
		driver.get(baseUrl);
	}
}
