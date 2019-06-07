package stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	//Enum used to ensure correct conditions are checked for the customer type of test case
	enum customerType{
		Individual,
		Company,
		Used,
		Fleet
    }

	//Excel Sheet data reading
	String path = "C:\\Users\\777632\\eclipse-workspace\\InsuranceAutomation\\src\\main\\java\\resources\\testData.xlsx";
	File file = new File(path);
	Workbook dataWorkbook = null;
	Sheet dataSheet = null;
	
	//Global variables used to store the values inputted and selected along process to then assert/verify at final page
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
	String checkLicence = null;
	String checkDemeritLoss = null;
	
	
	//Initializes browsers, excel datasheets and launches the webpage
	@Given("^I'm on the homepage$")
    public void im_on_the_homepage() throws Throwable {
        
		//Setup browser and launch to specified website
		driver = initialize();
		setPropertyFile();
		
		//Setup Excel sheet for data reading
		FileInputStream fs = new FileInputStream(file);
		String extension = path.substring(path.lastIndexOf("."));
		
		//Differentiate between excel formats
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
	
	//Checks what the requested type of quote to be used and fills in initial details required for each
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
    	
    	//First option provided for when user suppies car plate number and previous bill
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	String qType = System.getProperty("quoteType");
    	if(qType.equals("billing/plate")) {
	    	quote.plateNumberRadioButton().click();
    		quote.billingNumber().sendKeys("1234567");
    		quote.plateNumber().sendKeys("SNP901");
    		quote.continueButton1().click();
    		
    		//Terminates program as no further processes completed due to lack on test data
    		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		driver.findElement(By.cssSelector("button[id='modal_close']")).click();
    		Thread.sleep(2000);
	    	driver.quit();
    	}
    	//Second option provided for when user supplies details about vehicle
    	else if(qType.equals("vNumber")) {
    		quote.VINRadioButton().click();
    		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		Select rms = new Select(quote.vehicleIDSelect());
    		rms.selectByVisibleText("Plate number");
    		
    		quote.vehicleID().sendKeys("SNP901");
    		
    		Select personal = new Select(quote.personalIDSelect());
    		personal.selectByVisibleText("NSW Drivers licence number");
    		quote.personalID().sendKeys("210000");
    		
    		//Terminates program as no further processes completed due to lack on test data
    		quote.continueButton2().click();
    		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		driver.findElement(By.cssSelector("button[id='modal_close']")).click();
    		Thread.sleep(2000);
	    	driver.quit();
    	}
    	//Last option is anonymous and doesnt require already set data to complete
    	else if(qType.equals("anonymous")) {
    		quote.anonymousRadioButton().click();
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	
	    	Select status = new Select(quote.vehicleStatusSelect());
	    	status.selectByValue("1");
	    	
	    	quote.insuranceDateSelect().click();
	    	//Uses data stored in property file
	    	String startDay = prop.getProperty("day");
	    	String startMonth = prop.getProperty("month");
	    	String startYear = prop.getProperty("year");
	    	
	    	Select year = new Select(quote.startYear());
	    	year.selectByVisibleText(startYear);
	    	
	    	Select month = new Select(quote.startMonth());
	    	month.selectByVisibleText(startMonth);
	    	
	    	//List contains each day in the curret month and then clicks on desired day
	    	List<WebElement> day = quote.startDay();
			String temp = day.get(0).getText();
			for(int i = 0; i < 31; i++) {
				String text = day.get(i).getText();
				if(text.equalsIgnoreCase(startDay)) {
					day.get(i).click();
					break;
				}
			}
			//Grabs inputted date to assert/verify agaisnt final quote
			checkDate = quote.insuranceDateSelect().getAttribute("value");
			checkDate = checkDate.replaceAll("/", ".");
			quote.continueButton3().click();
    	}
    }
	
	//Verifies all inputted data is now presented on final quote page, Asserts all inputted data
    @Then("^Once all detail entered I'll be provided with a quote$")
    public void once_all_detail_entered_ill_be_provided_with_a_quote() throws Throwable {
    	Thread.sleep(4000);
    	String str[] = null;
    	String check = null;
    	finalQuote quote = new finalQuote(driver);
    	
    	/*
    	 * Note:
    	 * This section required a large amount of string manipulation to be able to assert data.
    	 * Formatting is different with the same elements or data across different pages
    	 * so needed to be manipulated to be compared correctly.
    	 */
    	
    	
    	//String manipulation to allow for assert equals check
    	//Splits Year up and assert
    	str = quote.quoteYear().getText().split(" ", 2);
    	check = str[1];
    	Assert.assertEquals(checkYear, check);
    	
    	//Splits Make up
    	str = quote.quoteMake().getText().split(" ", 2);
    	check = str[1];
    	Assert.assertEquals(checkMake, check);
    	
    	//Split shape up and assert
    	str = quote.quoteShape().getText().split(" ", 2);
    	check = str[1];
    	Assert.assertEquals(checkShape, check);
    	
    	//Split usage and assert
    	str = quote.quoteUsage().getText().split(" ", 2);
    	check = str[1];
    	Assert.assertEquals(checkUsage, check);
    	
    	//Expands the required boxes to get full text
    	quote.quoteExpand().click();
    	//Needed to deselect previously clicked expanding box
    	quote.quoteLocation().click();
    	try {
    		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[4]/div[1]/div[1]/div[2]/span[1]/a[1]")).click();
    	}
    	catch(NoSuchElementException e) {
    		System.out.println("Element not visible, continuing");
    	}
    	
    	
    	//Below is needed to re-format both elements being checked as this also used different formats
    	//Just works to get everything into one string without spaces which won't affect the comparison
    	if(checkCustType.equals("Individual / sole trader")) {
	    	str = quote.quoteLocation().getText().split("at ", 2);
	    	check = str[1];
    		check = check.replaceAll("\\ ", "");
	    	check = check.replaceAll("\\...", "");
    	}
    	else {
    		str = quote.quoteLocation().getText().split("at ", 2);
	    	check = str[1];
    		check = check.replaceAll("\\ ", "");
    	}
    	checkLocation = checkLocation.replace(" ", "");
    	Assert.assertEquals(checkLocation, check);
    	
    	//Split date and check and assert
    	str = quote.quoteDate().getText().split("date ", 2);
    	check = str[1];
    	Assert.assertEquals(checkDate, check);
    	
    	
    	str = quote.quoteCustomer().getText().split(" ", 2);
    	check = str[1];
    	//Needed this due to formatting differences on the final quote page
    	if(checkCustType.equals("Individual / sole trader")) {
    		check = check.replaceAll("\\/ ... ", "\\ / ");
    		check = check.replace("S", "s");
    	}
    	
    	//Chrome and firefox use different formatting and spaces so below is needed
    	check = check.replaceAll(" ", "");
		checkCustType = checkCustType.replaceAll(" ", "");
    	Assert.assertEquals(checkCustType, check);
    	
    	//Split tax and assert
    	str = quote.quoteTax().getText().split("tax ", 2);
    	check = str[1];
    	Assert.assertEquals(checkTax, check);
    	
    	System.out.println("Duration: " + checkDuration);
    	str = quote.quoteDuration().getText().split("Duration ", 2);
    	check = str[1];
    	check = check.replace("M", "m");
    	Assert.assertEquals(checkDuration, check);
    	
    	/*
    	 * Check if <= 1992 for extra steps
    	 * check if any drivers under 23
    	 * 		if no then need to go to next step
    	 * 		check if valid licence
    	 * 			if yes go to next step
    	 * 			check for demerit point loss
    	 */
    	if(checkCustType.equals("Individual / sole trader")) {
	    	checkDOB = checkDOB.replaceAll("/", ".");
	    	str = quote.quoteDOB().getText().split(" ", 2);
	    	check = str[1];
	    	Assert.assertEquals(checkDOB, check);
	    	
	    	//Check for the conditional elements and verify
	    	if(checkAge != null) {
	    		str = quote.quoteAge().getText().split("23 ", 2);
	    		check = str[1];
	    		System.out.println("checkAge: " + checkAge + " | check:" + check);
	    		Assert.assertEquals(checkAge, check);
	    	}
	    	if(checkLicence != null) {
	    		str = quote.quoteLicence().getText().split("licence ", 2);
	    		check = str[1];
	    		System.out.println("checkLicence: " + checkLicence + " | check:" + check);
	    		Assert.assertEquals(checkLicence, check);
	    	}
	    	if(checkDemeritLoss != null) {
	    		str = quote.quoteDemerits().getText().split("demerits ", 2);
	    		check = str[1];
	    		System.out.println("checkDemerits: " + checkDemeritLoss + " | check:" + check);
	    		Assert.assertEquals(checkDemeritLoss, check);
	    	}
    	}
    	
    	Thread.sleep(2000);
    	
    	System.out.println("All Assertions Passed");
    	//Writes to excel file that test passed
    	 Cell cell = dataSheet.getRow(1).getCell(8);
         if(cell == null){
         	dataSheet.getRow(1).createCell(8);
         }
         dataSheet.getRow(1).getCell(8).setCellValue("Pass");
         
         FileOutputStream fos = new FileOutputStream(file);
         dataWorkbook.write(fos);
         fos.close();
    	
    	driver.quit();
    }
    
    //Method navigates through various pages to get to quote generation panel, also asserts some elements
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
    
    //Method enters all details about vehicle to be quoted grabbin data from properties file
    @And("^I enter my vehicle details$")
    public void i_enter_my_vehicle_details() throws Throwable {
        vehicleDetails vehicle = new vehicleDetails(driver);
        
        //Replace with data parametizaton methods
        String manufacturingYear = prop.getProperty("manufacturing");
        String vMake = prop.getProperty("make");
        String vShape = prop.getProperty("shape");
        String vUsage = prop.getProperty("usage");
        String vPostcode = prop.getProperty("postcode");
        
        //Sends keys to input field
        vehicle.mYear().sendKeys(manufacturingYear);
        checkYear = manufacturingYear;
        
        //Select variables select based on mostly visible text
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

        //Continues to insurance preferences screen
        vehicle.vehicleContinue().click();
        
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    //Method enters insurance preferences which are stored and read from excel sheet
    @And("^I enter insurance preferences$")
    public void i_enter_insurance_preferences() throws Throwable {
    	
    	WebDriverWait wait = new WebDriverWait(driver, 5);
    	
    	//Assert tittle of page
    	String expectedTitle = "QBE Insurance Group - NSW Green Slips";
    	Assert.assertTrue(driver.getTitle().equals(expectedTitle));
    	
    	insurancePreferences insurance = new insurancePreferences(driver);
    	
    	//Reads data from excel sheet and stores to be used for input and select fields
        String cType = dataSheet.getRow(1).getCell(1).getStringCellValue();
        String duration = dataSheet.getRow(1).getCell(2).getStringCellValue();
        String tCredit = dataSheet.getRow(1).getCell(3).getStringCellValue();
        String dob = dataSheet.getRow(1).getCell(4).getStringCellValue();
        String underAge = dataSheet.getRow(1).getCell(5).getStringCellValue();
        String validLicence = dataSheet.getRow(1).getCell(6).getStringCellValue();
        int dpLoss = (int) dataSheet.getRow(1).getCell(7).getNumericCellValue();
        
        //Sorting out which customer type is to be selected
        int index = 0;
        customerType custType[] = customerType.values();
        //Enum used to index each customer type
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
        //Checks what the duration is in excel and selections hard set index as automatic selection not possible
       if(duration.equals("12 months")) {
        	insurance.insuranceTerm().get(0).click();
        	checkDuration = "12 months";
        }
        else {
        	insurance.insuranceTerm().get(1).click();
        	checkDuration = "6 months";
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='tax']")));
        //Select tax credit
        for(int i = 0; i < insurance.iTaxCreditRadio().size(); i++) {
    		if(insurance.iTaxCreditRadio().get(i).getAttribute("value").equals(tCredit)) {
    			insurance.iTaxCreditRadio().get(i).click();
    			checkTax = insurance.iTaxCreditRadio().get(i).getAttribute("value");
    		}
    	}
        
        //Only appears when individual selected as more options and conditions appear
        if(cType.equals("Individual")) {
	        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	        //Send user date of birth
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='dob']")));
	        insurance.iDOB().sendKeys(dob);
	        checkDOB = insurance.iDOB().getAttribute("value");
	        
	        //If any driver of vehicle is underage the above check will appear and continue with the next steps
	        String ageCheck[] = checkDOB.split("/", 3);
	    	String age = ageCheck[2];
	    	
	    	//Check user input age as site automatically generates Yes or no under certain conditions
	    	//Todays date to check agaisnt inputted birth date
	    	if(Integer.parseInt(age) <= 1992) {
	    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='underage']")));
	    		Thread.sleep(2000);
	    		WebElement element = driver.findElement(By.cssSelector("input[name='underage']"));
	        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	    		if(underAge.equals("Yes")) {
	    			insurance.insuranceAge().get(1).click();
	    			checkAge = insurance.insuranceAge().get(1).getAttribute("value");
	    			System.out.println("Age: " + checkAge);
	    		}
	    		else {//Licence and demerit point details
	    			insurance.insuranceAge().get(0).click();
	    			checkAge = insurance.insuranceAge().get(0).getAttribute("value");
	    			System.out.println("Age: " + checkAge);
	    			//Checks if previous condition requires further information to be inputted
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='licence']")));
	    			if(validLicence.equals("No")) {
	    				insurance.validLicence().get(0).click();
	    				checkLicence = insurance.validLicence().get(0).getAttribute("value");
	    				System.out.println("Licence: " + checkLicence);
	    			}
	    			else {
	    				insurance.validLicence().get(1).click();
	    				checkLicence = insurance.validLicence().get(1).getAttribute("value");
	    				System.out.println("Licence: " + checkLicence);
	    				
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='demerit']")));
	    				//Now checks and inputs demerit points
	    				if(dpLoss == 0) {
	    					insurance.demeritPoints().get(0).click();
	    					checkDemeritLoss = insurance.demeritPoints().get(0).getAttribute("value");
	    					System.out.println("Demerit: " + checkDemeritLoss);
	    				}
	    				else {
	    					insurance.demeritPoints().get(1).click();
	    					checkDemeritLoss = insurance.demeritPoints().get(1).getAttribute("value");
	    					System.out.println("Demerit: " + checkDemeritLoss);
	    				}
	    			}
	    		}
	    		
	    	}
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[id='button_forward']")));
        Thread.sleep(2000);
        insurance.insuranceContinue().click();
        
    }
}
