package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CTP {
	
	WebDriver driver = null;
	
	By state = By.cssSelector("div[class='c-columnContent__column']");
	By NSW = By.cssSelector("a[href='/au/green-slip-insurance/nsw']");
	By renew = By.partialLinkText("Renew your Green Slip");
	
	public CTP(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement viewStates() {
		return driver.findElement(state);
	}
	public WebElement selectNSW() {
		return driver.findElement(NSW);
	}
	public WebElement renewGreneSlip() {
		return driver.findElement(renew);
	}
}
