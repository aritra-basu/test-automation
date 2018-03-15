package com.test.automation.keywordLibrary;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class ActionKeywords {
	
	public static WebDriver driver; 
	public static DesiredCapabilities caps;	
	public static DesiredCapabilities dc;
	public static ChromeOptions options;
	
	public static Properties props;
	public static Session session;
	public static Store store;
	
	public static String mailID;
	public static String mailPassword;
	public static Folder folder;
	public static Message[] messages;
	
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
