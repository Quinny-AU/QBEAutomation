package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class vehicleDetails {
	
	WebDriver driver = null;
	
	By manufacturYear = By.id("a1");
	By vehicleMake = By.cssSelector("select[name='a2']");
	By vehicleShape = By.id("a3");
	By vehicleUsage = By.id("a4");
	By postcode = By.id("a5");
	By vContinue = By.id("button_forward");
	
	public vehicleDetails(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement mYear() {
		return driver.findElement(manufacturYear);
	}
	public WebElement selectMake() {
		return driver.findElement(vehicleMake);
	}
	public WebElement selectShape() {
		return driver.findElement(vehicleShape);
	}
	public WebElement selectUsage() {
		return driver.findElement(vehicleUsage);
	}
	public WebElement postcode() {
		return driver.findElement(postcode);
	}
	public WebElement vehicleContinue() {
		return driver.findElement(vContinue);
	}
	
}
