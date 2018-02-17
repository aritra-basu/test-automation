package com.test.automation.keywordLibrary;

import java.util.concurrent.TimeUnit; 

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.test.automation.config.GlobalConstants;
import com.test.automation.executionEngine.TestCaseDriver;
import com.test.automation.utilities.MapUtils;


public class ActionKeywords {
	
	public static WebDriver driver; 
	public static DesiredCapabilities caps;	
	public static DesiredCapabilities dc;
	public static ChromeOptions options;

	public static String attributeValue;
	public static String tagText;
	
	public static void openBrowser(WebElement object, String data) throws Exception{	
		switch (data) {
		case "IE":
			driver = new InternetExplorerDriver(caps);
			break;
		case "Chrome":
			ActionKeywords.options.addArguments("--start-maximized");
			ActionKeywords.options.addArguments("--disable-web-security");
	        ActionKeywords.options.addArguments("--allow-running-insecure-content");
	        ActionKeywords.options.addArguments("--incognito");
			ActionKeywords.dc.setCapability(ChromeOptions.CAPABILITY,ActionKeywords.options);
			ActionKeywords.dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(dc);
			break;
		default:
			break;
		}
	}

	public static void navigate(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		driver.get(data);
	}

	public static void click(WebElement object, String data) throws Exception{
			Thread.sleep(2000);
			object.click();
	} 
	
	public static void input(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		object.clear();
		object.sendKeys(data);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
	}
	
	public static void pressEnter(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		object.sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	public static void waitFor(WebElement object, String data) throws Exception{		
		Thread.sleep(2000);
	}
	
	public static void refreshBrowser(WebElement object, String data)throws Exception {
		driver.navigate().refresh();
	}	

	public static void closeBrowser(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		if (driver!=null)
			driver.quit();
	} 
	
	public static void getAttribute(WebElement object, String data) throws Exception{
		 attributeValue = object.getAttribute(data);
	}
	
	public static void setAttribute(WebElement object, String data) throws Exception{
		MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.dataRepositoryMap, TestCaseDriver.splitExpressions[2], GlobalConstants.data, attributeValue);
	}
	
	public static void printAttribute(WebElement object, String data) throws Exception{
		System.out.println(attributeValue);
	}
	
	public static void getText(WebElement object, String data) throws Exception{
		 tagText = object.getText();
	}
	
	public static void setText(WebElement object, String data) throws Exception{
		MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.dataRepositoryMap, TestCaseDriver.splitExpressions[2], GlobalConstants.data, tagText);
	}
	
	public static void printText(WebElement object, String data) throws Exception{
		System.out.println(tagText);
	}
}
