package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GreenSlipQuote {

	WebDriver driver = null;
	
	By getQuote = By.id("button_get-quote");
	By plateNumberQuote = By.cssSelector("input[onclick='selectoring(this,1)']");
	By vehicleVINQuote = By.cssSelector("input[onclick='selectoring(this,2)']");
	By anonymousQuote = By.cssSelector("input[onclick='selectoring(this,3)']");
	By billingNumber = By.cssSelector("input[id='billing_number']");
	By plateNumber = By.cssSelector("input[id='plate_number']");
	By vehicleIDSelect = By.cssSelector("select[id='select-vehicle']");
	By vehicleID = By.cssSelector("input[id='vehicleid']");
	By personalIDSelect = By.cssSelector("select[id='select-personal']");
	By personalID = By.cssSelector("input[id='personalid']");
	By vehicleStatus = By.xpath("/html[1]/body[1]/div[1]/div[4]/div[1]/div[3]/qbe-answer[1]/div[1]/form[4]/div[2]/div[1]/select[1]");
	By insuranceStartDate = By.id("form-3_start_date");
	By iStartDay = By.className("ui-state-default");
	By iStartMonth = By.className("ui-datepicker-month");
	By iYear = By.className("ui-datepicker-year");
	By continueButton1 = By.xpath("/html[1]/body[1]/div[1]/div[4]/div[1]/div[3]/qbe-answer[1]/div[1]/form[1]/div[4]/div[2]/button[1]");
	By continueButton2 = By.xpath("/html[1]/body[1]/div[1]/div[4]/div[1]/div[3]/qbe-answer[1]/div[1]/form[2]/div[3]/div[2]");
	By continueButton3 = By.xpath("/html[1]/body[1]/div[1]/div[4]/div[1]/div[3]/qbe-answer[1]/div[1]/form[4]/div[4]/div[2]/button[1]");
	
	public GreenSlipQuote(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement getQuoteButton() {
		return driver.findElement(getQuote);
	}
	public WebElement plateNumberRadioButton() {
		return driver.findElement(plateNumberQuote);
	}
	public WebElement billingNumber() {
		return driver.findElement(billingNumber);
	}
	public WebElement plateNumber() {
		return driver.findElement(plateNumber);
	}
	public WebElement vehicleIDSelect() {
		return driver.findElement(vehicleIDSelect);
	} 
	public WebElement vehicleID() {
		return driver.findElement(vehicleID);
	}
	public WebElement personalIDSelect() {
		return driver.findElement(personalIDSelect);
	}
	public WebElement personalID() {
		return driver.findElement(personalID);
	}
	public WebElement VINRadioButton() {
		return driver.findElement(vehicleVINQuote);
	}
	public WebElement anonymousRadioButton() {
		return driver.findElement(anonymousQuote);
	}
	public WebElement vehicleStatusSelect() {
		return driver.findElement(vehicleStatus);
	}
	public WebElement insuranceDateSelect() {
		return driver.findElement(insuranceStartDate);
	}
	public List<WebElement> startDay() {
		return driver.findElements(iStartDay);
	}
	public WebElement startMonth() {
		return driver.findElement(iStartMonth);
	}
	public WebElement startYear() {
		return driver.findElement(iYear);
	}
	public WebElement continueButton1() {
		return driver.findElement(continueButton1);
	}
	public WebElement continueButton2() {
		return driver.findElement(continueButton2);
	}
	public WebElement continueButton3() {
		return driver.findElement(continueButton3);
	}
}
