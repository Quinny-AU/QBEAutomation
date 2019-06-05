package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class homePage {

	WebDriver driver = null;
	
	By carAndVehicle = By.cssSelector("a[aria-label='Car & Vehicle']");
	By CTP = By.cssSelector("a[data-cta='CTP']");
	
	public homePage(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement carAndVehicleDropDown() {
		return driver.findElement(carAndVehicle);
	}
	public WebElement CTPLink() {
		return driver.findElement(CTP);
	}
}
