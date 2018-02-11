package com.test.automation.keywordLibrary;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class ActionKeywords {
	
	public static WebDriver driver;
	
	public static Actions builder;
	
	public static DesiredCapabilities caps;
	
	public static DesiredCapabilities dc;
	public static ChromeOptions options;
	
	public static Map<String, Object> chromeOptions;
	public static Map<String, String> mobileEmulation;
	
	public static ChromeDriverService chromeDriverService;
	
	public static Connection con;
	
	public static String parentWinHandle;
	
	public static Properties props;
	public static Session session;
	public static Store store;
	
	public static String authenticationCredentials;
	public static String proposalID;
	public static String orderID;
	public static String psName;
	public static String productURL;
	public static String proposalPhotosURL;
	public static Float itemPrice;
	public static String itemPriceText;
	public static String itemName;
	public static String banner;
	
	public static String mailID;
	public static String mailPassword;
	public static Folder folder;
	public static Message[] messages;
	
	public static String deviceName;
	public static List<String> agrsList;
	
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
	
	public static void openChromeMobile(WebElement object, String data) throws Exception{
		
		ActionKeywords.deviceName = data;
		ActionKeywords.agrsList.add("start-maximized");
		ActionKeywords.agrsList.add("disable-web-security");
		ActionKeywords.agrsList.add("allow-running-insecure-content");
		ActionKeywords.agrsList.add("incognito");
		ActionKeywords.agrsList.add("ignore-certificate-errors");
		ActionKeywords.mobileEmulation.put("deviceName", ActionKeywords.deviceName);
		ActionKeywords.chromeOptions.put("mobileEmulation", ActionKeywords.mobileEmulation);
		ActionKeywords.chromeOptions.put("args", ActionKeywords.agrsList);
		ActionKeywords.dc.setCapability(ChromeOptions.CAPABILITY, ActionKeywords.chromeOptions);
		driver = new ChromeDriver(dc);
	}

	public static void navigate(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		driver.get(data);
	}

	public static void click(WebElement object, String data) throws Exception{
			Thread.sleep(2000);
			object.click();
	}
	
	public static void actionClick(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		Actions actions = new Actions(driver);
		actions.moveToElement(object).click().perform();
	}
	
	public static void submit(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		object.submit();		
	}
	
	public static void hover(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		builder = new Actions(driver);
		builder.moveToElement(object).build().perform();	
	}
	
	
	public static void input(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		object.clear();
		object.sendKeys(data);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
	}
	
	public static void actionInput(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		Actions actions = new Actions(driver);
		actions.moveToElement(object);
		actions.click();
		actions.sendKeys(data);
		actions.build().perform();
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
	}
	
	public static void pressEnter(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		object.sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
/*	public static void upload(WebElement object, String data) throws Exception{	
		object.click();
		Thread.sleep(2000);
		Runtime.getRuntime().exec(GlobalConstants.AutoITFileUploadScript + " " + GlobalConstants.TestResourcesDirectory + "\\" + data);
		Thread.sleep(2000);
	}*/
	
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
	
	public static void scrollIntoView(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", object);
	}
	
	public static void scrollToTop(WebElement object, String data) throws Exception{
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,0)");
	}
	
	public static void switchToNewWindow(WebElement object, String data) throws Exception{
		parentWinHandle = driver.getWindowHandle();
		for(String winHandle : driver.getWindowHandles()){
			if(!winHandle.equals(parentWinHandle))
				driver.switchTo().window(winHandle);
		}
	}
	
	public static void switchToDefaultWindow(WebElement object, String data) throws Exception{
		driver.switchTo().window(parentWinHandle);
	}
	
	public static void switchToIframe(WebElement object, String data) throws Exception{
		driver.switchTo().frame(data);
		
	}
	
	public static void switchToDefaultIframe(WebElement object, String data) throws Exception{
		driver.switchTo().parentFrame();
	
	}
	
	public static void loginGmail(WebElement object, String data) throws Exception{	
		props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		session = Session.getInstance(props, null);
		
		String[] mailCredentials = data.split(":");
		store = session.getStore();
		store.connect("imap.gmail.com", mailCredentials[0], mailCredentials[1]);
		
		folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        
        messages = folder.getMessages();  
    }

}
