package com.test.automation.executionEngine;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class TestCaseLogger {
	
	public static Logger logger;
	
	public static void clearLog() throws Exception{
		logger.removeAllAppenders();
	}
	
	public static void onTestPlanStart(String testPlan) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info("Test Plan: " + testPlan + " execution started");
	}
	public static void onTestCaseStart(String testName) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info("-------------------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("Test Case: " + testName + " execution started");
		
	}
	
	public static void onTestStepStart(String testCaseName, String testStepAction, String pageObject, String testData, String step) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info(step + " started execution. Action: " + testStepAction + " | Page Object: " + pageObject + " | Test Data: " + testData);
	}
	
	public static void onTestStepPass(String step) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info(step + " finished execution. Passed");
		
	}
	
	public static void onTestStepFail(String exceptionMessage, String step) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.error(step + " finished execution. Failed with exception " + exceptionMessage);
		
	}
	
	public static void onTestCaseAssertStart(String testCaseName, String expectedCondition, String expectedValue, String step) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info(step + " verification started. Condition: " + expectedCondition + " | Value: " + expectedValue);
		
	}
	
	public static void onTestCaseAssertPass(String step) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info(step + " verification finished. Actual result equals expected result");
		
	}
	
	public static void onTestCasePass(String testName) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info("Test Case: " + testName + " finished execution. Passed");
		
	}
	
	public static void onTestCaseFail(String testName, String exceptionMessage) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.error("Test Case: " + testName + " finished execution. Failed with exception: " + exceptionMessage);
		
	}
	
	public static void onTestCaseSkip(String testName) throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.warn("Test Case: " + testName + " skipped execution.");
		
	}
	
	public static void onTestPlanEnd() throws Exception {
		DOMConfigurator.configure("log4j.xml");
		logger.info("T--E--S--T P--L--A--N E--N--D");
	}
	
}
