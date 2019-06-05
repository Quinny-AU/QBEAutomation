package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class initializeBrowser {

	public Properties prop = new Properties();
	FileInputStream fs;
	
	public WebDriver initialize() throws IOException {
		
		WebDriver driver = null;
		
		//Grabs the inputted value of browser from command line
		String browserName = System.getProperty("browser");
		
		/*Distinguishes which browser has bene request and then creates WebDriver of set type, 
		either Chrome or Gecko(FireFox)
		Can add more else if statements for inclusion of other WebDriver types
		*/
		if(browserName.equals("chrome")) {
			//Sets path for the WebDriver
			System.setProperty("webdriver.chrome.driver","C:\\Users\\777632\\webdriver\\chromedriver_win32_71_73\\chromedriver.exe");
			driver = new ChromeDriver();
			//Increases window size to full screen upon browser launch
			driver.manage().window().maximize();
			//Loads URL stored in test data file
			driver.get("https://www.qbe.com/au");
		}
		else if(browserName.equals("firefox")) {
			//Sets path for the WebDriver
			System.setProperty("webdriver.gecko.driver","C:\\Users\\777632\\webdriver\\Firefox\\geckodriver.exe");
			driver = new FirefoxDriver();
			//Increases window size to full screen upon browser launch
			driver.manage().window().maximize();
			//Loads URL stored in test data file
			driver.get("https://www.qbe.com/au");
		}
		//Returns the initialized type of driver and launches browser
		return driver;
	}
}
