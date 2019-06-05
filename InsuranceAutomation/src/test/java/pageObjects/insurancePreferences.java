package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class insurancePreferences {

	WebDriver driver = null;
	
	By customerType = By.cssSelector("input[name='customertype']");
	By customerType_Individual = By.cssSelector("input[value='I']");
	By customerType_Company = By.cssSelector("input[value='C']");
	By customerType_Used = By.cssSelector("input[value='UC']");
	By customerType_Fleet = By.cssSelector("input[value='F']");
	By insuranceTerm = By.cssSelector("input[name='term']");
	By taxCredit = By.cssSelector("input[name='tax']");
	By birthDate = By.cssSelector("input[name='dob']");
	By iContinue = By.cssSelector("button[id='button_forward']");
	
	public insurancePreferences(WebDriver driver) {
		this.driver = driver;
	}
	public List<WebElement> cTypeRadio() {
		return driver.findElements(customerType);
	}
	public WebElement cTypeRadio_Individual() {
		return driver.findElement(customerType_Individual);
	}
	public WebElement cTypeRadio_Company() {
		return driver.findElement(customerType_Company);
	}
	public WebElement cTypeRadio_Used() {
		return driver.findElement(customerType_Used);
	}
	public WebElement cTypeRadio_Fleet() {
		return driver.findElement(customerType_Fleet);
	}
	public List<WebElement> insuranceTerm() {
		return driver.findElements(insuranceTerm);
	}
	public List<WebElement> iTaxCreditRadio() {
		return driver.findElements(taxCredit);
	}
	public WebElement iDOB() {
		return driver.findElement(birthDate);
	}
	public WebElement insuranceContinue() {
		return driver.findElement(iContinue);
	}
}
