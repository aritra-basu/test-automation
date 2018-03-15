package com.test.automation.keywordLibrary;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AssertionKeywords {
	
	public static void elementExists(WebElement webElementName) throws Exception {
		Assert.assertTrue(webElementName.isDisplayed());
	}
	
	public static void toURLContains(String expectedURL) throws Exception {
		Assert.assertTrue(ActionKeywords.driver.getCurrentUrl().contains(expectedURL));
	}
	
	public static void emailSubjectContains(String expectedText) throws Exception {
		Assert.assertTrue(ActionKeywords.messages[ActionKeywords.messages.length - 1].getSubject().contains(expectedText));
	}
}
