package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class finalQuote {

	WebDriver driver = null;

	By year = By.xpath("//span[contains(text(), 'Year')]");
	By make = By.xpath("//span[contains(text(), 'Make')]");
	By shape = By.xpath("//span[contains(text(), 'Shape')]");
	By usage = By.xpath("//span[contains(text(), 'Usage')]");
	By location = By.xpath("//span[contains(text(), 'Garaged at')]");
	By expand = By.cssSelector("span[class='glyphicon glyphicon-plus']");
	By date = By.xpath("//span[contains(text(), 'Start date')]");
	By duration = By.xpath("//span[contains(text(), 'Duration')]");
	By tax = By.xpath("//span[contains(text(), 'Input Tax')]");
	By dob = By.xpath("//span[contains(text(), 'Born')]");
	By customer = By.xpath("//span[contains(text(), 'Customer')]");
	By age = By.xpath("//span[contains(text(), 'Under 23')]");
	
	public finalQuote(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement quoteYear() {
		return driver.findElement(year);
	}
	public WebElement quoteMake() {
		return driver.findElement(make);
	}
	public WebElement quoteShape() {
		return driver.findElement(shape);
	}
	public WebElement quoteUsage() {
		return driver.findElement(usage);
	}
	public WebElement quoteLocation() {
		return driver.findElement(location);
	}
	public WebElement quoteExpand() {
		return driver.findElement(expand);
	}
	public WebElement quoteDuration() {
		return driver.findElement(duration);
	}
	public WebElement quoteDate() {
		return driver.findElement(date);
	}
	public WebElement quoteTax() {
		return driver.findElement(tax);
	}
	public WebElement quoteDOB() {
		return driver.findElement(dob);
	}
	public WebElement quoteCustomer() {
		return driver.findElement(customer);
	}
	public WebElement quoteAge() {
		return driver.findElement(age);
	}
}
