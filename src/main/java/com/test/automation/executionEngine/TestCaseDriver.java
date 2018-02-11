package com.test.automation.executionEngine;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.keywordLibrary.ActionKeywords;
import com.test.automation.keywordLibrary.AssertionKeywords;
import com.test.automation.utilities.MapUtils;

public class TestCaseDriver {
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static WebElement sWebElement;
	public static String sData;
	public static Method method[];
	

	public static Method assertionMethod[];
	public static WebElement webElement;
	public static String expectedValue;
	public static AssertionKeywords assertions;
	
	public static Map<Integer, Object[]> testCasesExcelMap;
	
	public static Map<Integer, Object[]> objectRepositoryMap;
	
	public static Map<Integer, Object[]> dataRepositoryMap;
	
	public static Map<String, Object[]> suiteWiseReportMap;
	
	public static Map<String, Object[]> testCaseWiseReportMap;
	
	public static Map<Integer, String> resultWiseReportMap;
	
	public static String[] splitExpressions;
	
	public static String[] assertExpressions;
	
	public static int stepNumber;
	
	public static String stepName;
		
	
	public static void executeTestSteps(Map<Integer, Object[]> testCasesMap, Map<Integer, Object[]> objectMap, Map<Integer, Object[]> dataMap, String testCaseName, String testCaseExecuted, String identifyByColumn, String valueColumn, String dataColumn) throws Exception{
		
		String executedStatus = null;
		stepNumber = 0;
		splitExpressions = null;
		Object[] testCasesMapObj = new Object[100];
		testCasesMapObj = MapUtils.getMapEntryByIdentifier(testCasesMap, testCaseName);
		executedStatus = MapUtils.getValueInMapObjectByIdentifier(testCasesMap, testCaseName, testCaseExecuted);
		if(executedStatus.equals("N")){
			for(Object ob: testCasesMapObj){			
				if (ob != null && ob.toString().contains("#")){
					stepNumber++;
					stepName = "Step: " + stepNumber;
					splitExpressions = ob.toString().split("#", -1);
					TestCaseLogger.onTestStepStart(testCaseName, splitExpressions[0], splitExpressions[1], splitExpressions[2], stepName);
					TestCaseDriver.logger.log(LogStatus.INFO, stepName, "executing... Action: " + splitExpressions[0] + " | Page Object: " + splitExpressions[1] + "  | Test Data: " + splitExpressions[2]);					
					sActionKeyword = splitExpressions[0];
					sWebElement = TestCaseDriver.getWebElement(objectMap, splitExpressions[1], identifyByColumn, valueColumn);
					sData = MapUtils.getValueInMapObjectByIdentifier(dataMap, splitExpressions[2], dataColumn);					
					TestCaseDriver.executeTest_Actions();
					TestCaseLogger.onTestStepPass(stepName);
					TestCaseDriver.logger.log(LogStatus.PASS, stepName, " executed successfully");
				}
				
			}
		}
		else{
			throw new SkipException("Already Executed");
		}
		

	}

	public static void executeTest_Actions() throws Exception{
		 
		for(int i=0;i<method.length;i++){
			
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sWebElement, sData);
				break;
				}
			}
		}

	public static void assertTC(Map<Integer, Object[]> testCasesMap, Map<Integer, Object[]> objectMap, Map<Integer, Object[]> dataMap, String testMethodName, String expectedConditionColumn, String webElementNameColumn, String identifyByColumn, String valueColumn, String dataColumn) throws Exception {
		String assertString = null;
		assertExpressions = new String[2];
		assertString = MapUtils.getValueInMapObjectByIdentifier(testCasesMap, testMethodName, expectedConditionColumn);
		stepName = "Assertion";
		if(assertString.contains("=")){
			assertExpressions = assertString.split("=");
			TestCaseLogger.onTestCaseAssertStart(testMethodName, assertExpressions[0], assertExpressions[1], stepName);
			TestCaseDriver.logger.log(LogStatus.INFO, stepName, "asserting... Condition: " + assertExpressions[0] + " | Value: " + assertExpressions[1]);
			if(MapUtils.verifyIfStringValuePresentInMap(objectMap, webElementNameColumn, assertExpressions[1])){
				webElement = TestCaseDriver.getWebElement(objectMap, assertExpressions[1], identifyByColumn, valueColumn);
				
				for(int i=0;i<assertionMethod.length;i++){				
					if(assertionMethod[i].getName().equals(assertExpressions[0])){
						assertionMethod[i].invoke(assertions, webElement);
						break;
						}
					}
				
			}
			else{
				expectedValue = MapUtils.getValueInMapObjectByIdentifier(dataMap, assertExpressions[1], dataColumn);
				
				for(int i=0;i<assertionMethod.length;i++){				
					if(assertionMethod[i].getName().equals(assertExpressions[0])){
						assertionMethod[i].invoke(assertions, expectedValue);
						break;
						}
					}	
			}
				
		}
		else{
			TestCaseLogger.onTestCaseAssertStart(testMethodName, "NA", "NA", stepName);
			TestCaseDriver.logger.log(LogStatus.INFO, stepName, "asserting... N/A");
			Assert.assertTrue(true);
		}
			
		TestCaseLogger.onTestCaseAssertPass(stepName);		
		TestCaseDriver.logger.log(LogStatus.PASS, stepName, "actual result equals expected result");
	}

	public static WebElement getWebElement(Map<Integer, Object[]> map, String webElement, String identifyByColumn, String valueColumn) throws Exception{
		WebElement element = null;
		
		String identifyBy = null;
		int identifyByIndex = MapUtils.getMapObjectIndexByHeader(map, identifyByColumn);
		int valueIndex = MapUtils.getMapObjectIndexByHeader(map, valueColumn);
		Object[] mapObj = new Object[100];
		if(!webElement.equals(" ")){
			mapObj = MapUtils.getMapEntryByIdentifier(map, webElement);
			identifyBy = mapObj[identifyByIndex].toString();
			switch(identifyBy){
			case "id":
				element = ActionKeywords.driver.findElement(By.id(mapObj[valueIndex].toString()));
				break;
			case "name":
				element = ActionKeywords.driver.findElement(By.name(mapObj[valueIndex].toString()));
				break;
			case "xpath":
				element = ActionKeywords.driver.findElement(By.xpath(mapObj[valueIndex].toString()));
				break;
			case "cssSelector":
				element = ActionKeywords.driver.findElement(By.cssSelector(mapObj[valueIndex].toString()));
				break;
			case " ":
				element = null;
				break;
			default:
				element = null;
				break;
			}
			
		}
		return element;
	}

	public static ExtentTest logger;

	
}
