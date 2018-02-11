package com.test.automation.config;

public class GlobalConstants {
	
	public static String ChromeDriverProperty = "webdriver.chrome.driver";
	public static String ChromeDriverPath = "drivers/chromedriver";
	
	public static String IEDriverProperty = "webdriver.ie.driver";
	public static String IEDriverPath = "";
	
	public static String TestExecutionDirectory = "testrepo/test-library";
	public static String ArchivedTestCasesDirectory = "testrepo/test-library/archived/test-cases";
	public static String ArchivedHTMLTestReportsDirectory = "testrepo/test-library/archived/test-reports/html-reports";
	public static String ArchivedExcelTestReportsDirectory = "testrepo/test-library/archived/test-reports/excel-reports";
	public static String TestResourcesDirectory = "testrepo/test-library/test-resources";
	
	public static String TestCasesFileIdentifier = "test_plan";
	public static String RepositoryFileIdentifier = "object_data_repository";
	
	public static String testSuiteId = "Test Suite ID";
	public static String testSuiteName = "Test Suite Name";
	public static String testCaseId = "Test Case ID";
	public static String testCaseName = "Test Case Name";
	public static String testCaseExecuted = "Test Case Executed";
	public static String expectedResultCondition = "Expected Result Condition";
	public static String testResult = "Test Result";
	public static String testSteps = "Test Steps";
	
	public static String webElement = "Web Element";
	public static String identifyBy = "Identify By";
	public static String identifyByValue = "Value";
	
	public static String fieldName = "Field Name";
	public static String data = "Data";
	
	public static String searchedTerm = "Instawork";
	
	public static String TestMetricsSuiteWise = "Suite-Wise Report";
	public static String report_TestSuite = "Test Suite";
	public static String totalTestCases = "Total Test Cases";
	public static String skipped = "Skipped";
	public static String passed = "Passed";
	public static String failed = "Failed";
	public static String[] suiteWiseReportHeader = {report_TestSuite, totalTestCases, skipped, passed, failed};
	
	public static String executed = "Y";
	public static String notYetExecuted = "N";
	public static String testCasePassed = "PASSED";
	public static String testCaseFailed = "FAILED";
	public static String testCaseSkipped = "SKIPPED";
	public static String failedWith = "FAILED with ";



}
