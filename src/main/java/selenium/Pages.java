package selenium;

import org.openqa.selenium.WebDriver;

public abstract class Pages {

	protected WebDriver driver;

	public Pages(final WebDriver driver) {
		this.driver = driver;
	}

	protected void open(String path){
		driver.get("http://github.com" + path);
	}

	protected void open(){
		driver.get("http://github.com");
	}
}
