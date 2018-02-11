package com.test.automation.keywordLibrary;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.test.automation.config.GlobalConstants;

public class AssertionKeywords {
	
	public static void elementExists(WebElement webElementName) throws Exception {
		Assert.assertTrue(webElementName.isDisplayed());
	}
	
	public static void elementContainsText(WebElement webElementName) throws Exception {
		Assert.assertTrue(webElementName.getText().contains(GlobalConstants.searchedTerm));
	}
	
	public static void toURLContains(String expectedURL) throws Exception {
		Assert.assertTrue(ActionKeywords.driver.getCurrentUrl().contains(expectedURL));
	}
}
