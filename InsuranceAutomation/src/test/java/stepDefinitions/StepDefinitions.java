package stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.CTP;
import pageObjects.GreenSlipQuote;
import pageObjects.finalQuote;
import pageObjects.homePage;
import pageObjects.insurancePreferences;
import pageObjects.vehicleDetails;
import resources.initializeBrowser;

public class StepDefinitions extends initializeBrowser{

	WebDriver driver = null;
	enum customerType{
		Individual,
		Company,
		Used,
		Fleet
    }
	enum radioChoice{
		No,
		Yes
	}
	//Excel Sheet data reading
	String path = "C:\\Users\\777632\\eclipse-workspace\\InsuranceAutomation\\src\\main\\java\\resources\\testData.xlsx";
	File file = new File(path);
	Workbook dataWorkbook = null;
	Sheet dataSheet = null;
	
	String checkYear = null;
	String checkMake = null;
	String checkShape = null;
	String checkUsage = null;
	String checkLocation = null;
	String checkDate = null;
	String checkDuration = null;
	String checkCustType = null;
	String checkTax = null;
	String checkDOB = null;
	String checkAge = null;
	/*
	 * valid license
	 * lost demerits
	 */
	
	@Given("^I'm on the homepage$")
    public void im_on_the_homepage() throws Throwable {
        
		//Setup browser and launch to specified website
		driver = initialize();
		
		//Setup Excel sheet for data reading
		FileInputStream fs = new FileInputStream(file);
		
		String extension = path.substring(path.lastIndexOf("."));
		
		if((extension.equals(".xlsx"))){
			dataWorkbook = new XSSFWorkbook(fs);
		}
		else if((extension.equals(".xls"))) {
			dataWorkbook = new HSSFWorkbook(fs);
		}
		
		dataSheet = dataWorkbook.getSheet("insuranceData");
		
		WebElement div = driver.findElement(By.cssSelector("div[class='c-header']"));
		List<WebElement> list = div.findElements(By.tagName("a"));
		
		System.out.println("Number of Menu Links: " + list.size());
		
		homePage home = new homePage(driver);
		
		home.carAndVehicleDropDown().click();
		
		home.CTPLink().click();
		
	}
	
	@When("^I select anonymous quote$")
    public void i_select_anonymous_quote() throws Throwable {
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		
		GreenSlipQuote quote = new GreenSlipQuote(driver);
    	
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='button_get-quote']")));
    	
    	
    	//Verifying that Compulsory Third Party NSW is displayed on the page
    	//Alternatively could specifically grab the element we think it is displayed on and use isDisplayed()
    	//Reason for this is its generic for checking agaisnt whole page as step doesn't specific page title
    	Assert.assertTrue(driver.getPageSource().contains("Compulsory Third Party NSW"));
    	
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	
    	quote.getQuoteButton().click();
    	
    	
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	
    	String qType = System.getProperty("quoteType");
    	
    	if(qType.equals("billing/plate")) {
	    	quote.plateNumberRadioButton().click();

    		quote.billingNumber().sendKeys("1234567");
    		
    		quote.plateNumber().sendKeys("SNP901");
    		
    		quote.continueButton1().click();
    		
    		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		driver.findElement(By.cssSelector("button[id='modal_close']")).click();
    		Thread.sleep(2000);
    		
	    	driver.quit();
    	}
    	else if(qType.equals("vNumber")) {
    		quote.VINRadioButton().click();
    		
    		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		Select rms = new Select(quote.vehicleIDSelect());
    		rms.selectByVisibleText("Plate number");
    		
    		quote.vehicleID().sendKeys("SNP901");
    		
    		Select personal = new Select(quote.personalIDSelect());
    		personal.selectByVisibleText("NSW Drivers licence number");
    		
    		quote.personalID().sendKeys("210000");
    		
    		quote.continueButton2().click();
    		
    		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		driver.findElement(By.cssSelector("button[id='modal_close']")).click();
    		
    		Thread.sleep(2000);
	    	driver.quit();
    	}
    	else if(qType.equals("anonymous")) {
    		quote.anonymousRadioButton().click();
	    	
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	
	    	Select status = new Select(quote.vehicleStatusSelect());
	    	status.selectByVisibleText("Current");
	    	
	    	quote.insuranceDateSelect().click();
	    	
	    	String startDay = "30";
	    	String startMonth = "Jul";
	    	String startYear = "2019";
	    	
	    	Select year = new Select(quote.startYear());
	    	year.selectByVisibleText(startYear);
	    	
	    	Select month = new Select(quote.startMonth());
	    	month.selectByVisibleText(startMonth);
	    	
	    	List<WebElement> day = quote.startDay();
			String temp = day.get(0).getText();
			for(int i = 0; i < 31; i++) {
				String text = day.get(i).getText();
				if(text.equalsIgnoreCase(startDay)) {
					day.get(i).click();
					break;
				}
			}
			checkDate = quote.insuranceDateSelect().getAttribute("value");
			checkDate = checkDate.replaceAll("/", ".");
			quote.continueButton3().click();
    	}
    }

    @Then("^Once all detail entered I'll be provided with a quote$")
    public void once_all_detail_entered_ill_be_provided_with_a_quote() throws Throwable {
    	Thread.sleep(4000);
    	String str[] = null;
    	String check = null;
    	finalQuote quote = new finalQuote(driver);
    	
    	//Assert.assertTrue(quote.quoteYear().getText().contains(checkYear));	
    	//Assert.assertTrue(quote.quoteMake().getText().contains(checkMake)); 	
    	//Assert.assertTrue(quote.quoteShape().getText().contains(checkShape)); 	
    	//Assert.assertTrue(quote.quoteUsage().getText().contains(checkUsage));
    	
    	//String manipulation to allow for assert equals check
    	str = quote.quoteYear().getText().split(" ", 2);
    	check = str[1];
    	
    	Assert.assertEquals(checkYear, check);
    	
    	str = quote.quoteMake().getText().split(" ", 2);
    	check = str[1];
    	
    	Assert.assertEquals(checkMake, check);
    	
    	str = quote.quoteShape().getText().split(" ", 2);
    	check = str[1];
    	
    	Assert.assertEquals(checkShape, check);
    	
    	str = quote.quoteUsage().getText().split(" ", 2);
    	check = str[1];
    	
    	Assert.assertEquals(checkUsage, check);
    	
    	quote.quoteExpand().click();
    	
    	str = quote.quoteLocation().getText().split("\\ ... ", 2);
    	check = str[0] + str[1];
    	
    	str = check.split("at ", 2);
    	check = str[1];
    	
    	Assert.assertEquals(checkLocation, check);
    	
    	str = quote.quoteDate().getText().split("date ", 2);
    	check = str[1];
    	
    	Assert.assertEquals(checkDate, check);
    	
    	str = quote.quoteCustomer().getText().split(" ", 2);
    	check = str[1];
    	
    	//Assert.assertTrue(checkCustType.contains(check));
    	
    	driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[4]/div[1]/div[1]/div[2]/span[1]/a[1]")).click();
    	
    	//Assert.assertEquals(checkCustType, check);
    	
    	System.out.println("Tax: " + checkTax);
    	
    	System.out.println("Duration: " + checkDuration);
    	
    	checkDOB = checkDOB.replaceAll("/", ".");
    	str = quote.quoteDOB().getText().split(" ", 2);
    	check = str[1];
    	
    	Assert.assertEquals(checkDOB, check);
    	
    	Thread.sleep(2000);
    	
    	System.out.println("All Assertions Passed");
    	
    	 Cell cell = dataSheet.getRow(1).getCell(5);
         if(cell == null){
         	dataSheet.getRow(1).createCell(5);
         }
         dataSheet.getRow(1).getCell(5).setCellValue("Pass");
         
         FileOutputStream fos = new FileOutputStream(file);
         dataWorkbook.write(fos);
         fos.close();
    	
    	driver.quit();
    }

    @And("^I navigate to getting a quote in NSW$")
    public void i_navigate_to_getting_a_quote() throws Throwable {
    	
    	CTP c = new CTP(driver);
    	
    	//scroll contents into view to select state
    	WebElement element = c.viewStates();
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    	
    	c.selectNSW().click();
    	
    	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    	
    	//Verify title of page and panel
    	String title = driver.getTitle();
    	String pageTitle = driver.findElement(By.tagName("h1")).getText();
    	
    	Assert.assertTrue(title.contains(pageTitle));
    	
    	c.renewGreneSlip().click();
    	
    }
    @And("^I enter my vehicle details$")
    public void i_enter_my_vehicle_details() throws Throwable {
        vehicleDetails vehicle = new vehicleDetails(driver);
        
        //replace with data parametizaton methods
        String manufacturingYear = "2006";
        String vMake = "Mitsubishi";
        String vShape = "SED";
        String vUsage = "PRIV";
        String vPostcode = "2527-ALBION PARK";
        
        vehicle.mYear().sendKeys(manufacturingYear);
        checkYear = manufacturingYear;
        
        Select make = new Select(vehicle.selectMake());
        make.selectByVisibleText(vMake);
        checkMake = vehicle.selectMake().getAttribute("value");
        
        Select shape = new Select(vehicle.selectShape());
        shape.selectByValue(vShape);
        checkShape = vehicle.selectShape().getAttribute("value");
        
        Select usage = new Select(vehicle.selectUsage());
        usage.selectByValue(vUsage);
        checkUsage = vehicle.selectUsage().getAttribute("value");
        
        Select postcode = new Select(vehicle.postcode());
        postcode.selectByValue(vPostcode);
        checkLocation = vehicle.postcode().getAttribute("value");
        
        System.out.println("CheckLoc: " + checkLocation);
        
        vehicle.vehicleContinue().click();
        
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        
    }
    @And("^I enter insurance preferences$")
    public void i_enter_insurance_preferences() throws Throwable {
    	
    	WebDriverWait wait = new WebDriverWait(driver, 5);
    	
    	String expectedTitle = "QBE Insurance Group - NSW Green Slips";
    	
    	Assert.assertTrue(driver.getTitle().equals(expectedTitle));
    	
    	insurancePreferences insurance = new insurancePreferences(driver);
    	
    	//Change to data manipluation later
        String cType = dataSheet.getRow(1).getCell(1).getStringCellValue();
        String duration = dataSheet.getRow(1).getCell(2).getStringCellValue();
        String tCredit = dataSheet.getRow(1).getCell(3).getStringCellValue();
        String dob = dataSheet.getRow(1).getCell(4).getStringCellValue();
        
        //Sorting out which customer type is to be selected
        int index = 0;
        
        customerType custType[] = customerType.values();
        
        SEARCH:
        for(customerType cust : custType) {
        		if(cust.toString().equals(cType)) {
        			index = cust.ordinal();
        			insurance.cTypeRadio().get(cust.ordinal()).click();
        			checkCustType = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/form[1]/div[1]/div[1]/div[2]/qbe-answer[1]/div[" + (index+1) + "]/label[1]")).getText();
        			break SEARCH;
        		}
        }
        
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Selecting insurace duration
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='term']")));
        
       if(duration.equals("12 months")) {
        	insurance.insuranceTerm().get(0).click();
        	checkDuration = insurance.insuranceTerm().get(0).getAttribute("label");
        }
        else {
        	insurance.insuranceTerm().get(1).click();
        	checkDuration = insurance.insuranceTerm().get(1).getAttribute("label");
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='tax']")));
        //Select tax credit
        for(int i = 0; i < insurance.iTaxCreditRadio().size(); i++) {
    		if(insurance.iTaxCreditRadio().get(i).getAttribute("value").equals(tCredit)) {
    			insurance.iTaxCreditRadio().get(i).click();
    			checkTax = insurance.iTaxCreditRadio().get(i).getAttribute("value");
    		}
    	}
        
        //only appears when individual selected
        if(cType.equals("Individual")) {
	        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	        //Send user date of birth
	        insurance.iDOB().sendKeys(dob);
	        checkDOB = insurance.iDOB().getAttribute("value");
	        
	        //add extra values present on page
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='button_forward']")));
        Thread.sleep(2000);
        insurance.insuranceContinue().click();
        
    }
}