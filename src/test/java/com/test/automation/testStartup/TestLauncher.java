 package com.test.automation.testStartup;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.config.GlobalConstants;
import com.test.automation.executionEngine.TestCaseDriver;
import com.test.automation.executionEngine.TestCaseLogger;
import com.test.automation.keywordLibrary.ActionKeywords;
import com.test.automation.keywordLibrary.AssertionKeywords;
import com.test.automation.testMetrics.SuiteWiseReportGenerator;
import com.test.automation.utilities.DateUtils;
import com.test.automation.utilities.ExcelUtils;
import com.test.automation.utilities.FileUtils;
import com.test.automation.utilities.MapUtils;


public class TestLauncher {

	
	public static String TestCasesFile;
	public static String RepositoriesFile;
	public static String ProcessesedTestCasesFile;
	public static String ArchivedTestCasesFile;
	public static String TestMetrics;
	public static String TestReport;
	
	public static ExtentReports report;
	@Test(dataProvider = "testCaseNameProvider")
	  public void executeTestCase(String testCaseName) throws Exception {
		  try{
			  
			  TestCaseDriver.logger = TestLauncher.report.startTest(testCaseName);
			  
			  TestCaseLogger.onTestCaseStart(testCaseName);
			  
			  TestCaseDriver.executeTestSteps(TestCaseDriver.testCasesExcelMap, TestCaseDriver.objectRepositoryMap, TestCaseDriver.dataRepositoryMap, testCaseName, GlobalConstants.testCaseExecuted, GlobalConstants.identifyBy, GlobalConstants.identifyByValue, GlobalConstants.data);
			  
			  TestCaseDriver.assertTC(TestCaseDriver.testCasesExcelMap, TestCaseDriver.objectRepositoryMap, TestCaseDriver.dataRepositoryMap, testCaseName, GlobalConstants.expectedResultCondition, GlobalConstants.webElement, GlobalConstants.identifyBy, GlobalConstants.identifyByValue, GlobalConstants.data);
			  
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testCaseExecuted, GlobalConstants.executed);
			  
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.testCasePassed);
			  
			  TestCaseLogger.onTestCasePass(testCaseName);  	  
			  
		  }
		  catch(InvocationTargetException ite){
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.failedWith + ite.getClass().getSimpleName());
			  TestCaseDriver.logger.log(LogStatus.FAIL, TestCaseDriver.stepName, "failed with exception: " + ite.getClass().getSimpleName());
			  TestLauncher.report.endTest(TestCaseDriver.logger);
			  TestCaseLogger.onTestStepFail(ite.getClass().getSimpleName(), TestCaseDriver.stepName);
			  TestCaseLogger.onTestCaseFail(testCaseName, ite.getClass().getSimpleName());
			  throw ite;
		  }
		  catch (NoSuchElementException nsee) {
			  TestCaseDriver.logger.log(LogStatus.FAIL, TestCaseDriver.stepName, "failed with exception: " + nsee.getClass().getSimpleName());
			  TestLauncher.report.endTest(TestCaseDriver.logger);
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.failedWith + nsee.getClass().getSimpleName());
			  TestCaseLogger.onTestStepFail(nsee.getClass().getSimpleName(), TestCaseDriver.stepName);
			  TestCaseLogger.onTestCaseFail(testCaseName, nsee.getClass().getSimpleName());
			  throw nsee;
		  }
		  catch (ElementNotVisibleException enve) {
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.failedWith + enve.getClass().getSimpleName());
			  TestCaseDriver.logger.log(LogStatus.FAIL, TestCaseDriver.stepName, "failed with exception: " + enve.getClass().getSimpleName());
			  TestLauncher.report.endTest(TestCaseDriver.logger);		  
			  TestCaseLogger.onTestStepFail(enve.getClass().getSimpleName(), TestCaseDriver.stepName);
			  TestCaseLogger.onTestCaseFail(testCaseName, enve.getClass().getSimpleName());
			  throw enve;
		  }
		  catch (IllegalArgumentException iae) {
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.failedWith + iae.getClass().getSimpleName());
			  TestCaseDriver.logger.log(LogStatus.FAIL, TestCaseDriver.stepName, "failed with exception: " + iae.getClass().getSimpleName());
			  TestLauncher.report.endTest(TestCaseDriver.logger);
			  TestCaseLogger.onTestStepFail(iae.getClass().getSimpleName(), TestCaseDriver.stepName);
			  TestCaseLogger.onTestCaseFail(testCaseName, iae.getClass().getSimpleName());
			  throw iae;
		  }
		  catch (NullPointerException npe) {
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.failedWith + npe.getClass().getSimpleName());
			  TestCaseDriver.logger.log(LogStatus.FAIL, TestCaseDriver.stepName, "failed with exception: " + npe.getClass().getSimpleName());
			  TestLauncher.report.endTest(TestCaseDriver.logger);
			  TestCaseLogger.onTestStepFail(npe.getClass().getSimpleName(), TestCaseDriver.stepName);
			  TestCaseLogger.onTestCaseFail(testCaseName, npe.getClass().getSimpleName());
			  throw npe;
		  }
		  catch (SkipException se) {
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.testCaseSkipped);
			  TestCaseDriver.logger.log(LogStatus.SKIP, "Skipped as test case is already executed.");
			  TestLauncher.report.endTest(TestCaseDriver.logger );
			  TestCaseLogger.onTestCaseSkip(testCaseName);
			  throw se;	  
		  }
		  catch(AssertionError ae){
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.failedWith + ae.getClass().getSimpleName());
			  TestCaseDriver.logger.log(LogStatus.FAIL, TestCaseDriver.stepName, "actual result doesnt match expected result");
			  TestLauncher.report.endTest(TestCaseDriver.logger);
			  TestCaseLogger.onTestStepFail(ae.getClass().getSimpleName(), TestCaseDriver.stepName);
			  TestCaseLogger.onTestCaseFail(testCaseName, ae.getClass().getSimpleName());
			  throw ae;
		  }
		  catch(UnhandledAlertException uae){
			  Alert alert = ActionKeywords.driver.switchTo().alert();
			  alert.accept();
		  }
		  catch (Exception e) {
			  MapUtils.setValueInMapObjectByIdentifier(TestCaseDriver.testCasesExcelMap, testCaseName, GlobalConstants.testResult, GlobalConstants.failedWith + e.getClass().getSimpleName());
			  TestCaseDriver.logger.log(LogStatus.FAIL, TestCaseDriver.stepName, "failed with exception: " + e.getClass().getSimpleName());
			  TestLauncher.report.endTest(TestCaseDriver.logger);
			  TestCaseLogger.onTestStepFail(e.getClass().getSimpleName(), TestCaseDriver.stepName);
			  TestCaseLogger.onTestCaseFail(testCaseName, e.getClass().getSimpleName());
			  throw e;   
		  }
		  
		  TestLauncher.report.endTest(TestCaseDriver.logger); 
	  }
	  
	  
	  @DataProvider(name = "testCaseNameProvider")
	  public String[][] dataProvider() throws Exception {
		  String[][] objMap = new String[TestCaseDriver.testCasesExcelMap.size()-1][1];
		  for(Entry<Integer, Object[]> entry : TestCaseDriver.testCasesExcelMap.entrySet()) {
			  Object[] obj = entry.getValue();
			  if(entry.getKey()!=0){
				  objMap[entry.getKey()-1][0] = obj[MapUtils.getMapObjectIndexByHeader(TestCaseDriver.testCasesExcelMap, GlobalConstants.testCaseName)].toString();
				  
			  }
			  
		  }
		  
		  return objMap;
	    
	  }
	  
	  
	  @BeforeSuite
	  public void beforeSuite(ITestContext ctx) throws Exception {
	        
		  System.setProperty("current_date", DateUtils.getSimpleDateFormat());
		  
		  TestCaseLogger.logger = Logger.getLogger(Log.class.getName());
		  TestCaseLogger.onTestPlanStart(FileUtils.getFileNameWithoutExtension(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier));
			
		  
		  ActionKeywords.dc = DesiredCapabilities.chrome();
		  ActionKeywords.options = new ChromeOptions();
		  ActionKeywords.agrsList = new ArrayList<String>();
		  ActionKeywords.chromeOptions = new HashMap<String, Object>();
		  ActionKeywords.mobileEmulation = new HashMap<String, String>();
		  
		  System.setProperty(GlobalConstants.ChromeDriverProperty, GlobalConstants.ChromeDriverPath);
		  
		  
		  ActionKeywords.caps = DesiredCapabilities.internetExplorer(); 
		  ActionKeywords.caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		  ActionKeywords.caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		  System.setProperty(GlobalConstants.IEDriverProperty, GlobalConstants.IEDriverPath);
		  
		  
		  TestCaseDriver.actionKeywords = new ActionKeywords();
		  TestCaseDriver.method = TestCaseDriver.actionKeywords.getClass().getMethods();
		  
		  TestCaseDriver.assertions = new AssertionKeywords();
		  TestCaseDriver.assertionMethod = TestCaseDriver.assertions.getClass().getMethods();
		  
		  
		  TestCaseDriver.testCasesExcelMap = new HashMap<Integer, Object[]>();	  
		  TestCaseDriver.objectRepositoryMap = new HashMap<Integer, Object[]>();
		  TestCaseDriver.dataRepositoryMap = new HashMap<Integer, Object[]>();
		  
		  TestCaseDriver.suiteWiseReportMap = new LinkedHashMap<String, Object[]>();
		  TestCaseDriver.testCaseWiseReportMap = new LinkedHashMap<String, Object[]>();
		  TestCaseDriver.resultWiseReportMap = new LinkedHashMap<Integer, String>();
		  
		  TestLauncher.TestCasesFile = FileUtils.getFilePathFromDirectory(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier);
		  TestLauncher.ProcessesedTestCasesFile = GlobalConstants.TestExecutionDirectory+"/"+FileUtils.getFileNameWithoutExtension(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier)+"_"+DateUtils.getSimpleDateFormat()+FileUtils.getFileExtension(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier);
		  TestLauncher.ArchivedTestCasesFile = GlobalConstants.ArchivedTestCasesDirectory+"/"+FileUtils.getFileNameWithoutExtension(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier)+"_"+DateUtils.getSimpleDateFormat()+FileUtils.getFileExtension(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier);
		  
		  TestLauncher.RepositoriesFile = FileUtils.getFilePathFromDirectory(GlobalConstants.TestExecutionDirectory, GlobalConstants.RepositoryFileIdentifier);
		  
		  TestLauncher.TestReport = GlobalConstants.ArchivedHTMLTestReportsDirectory+"/"+FileUtils.getFileNameWithoutExtension(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier)+"_report_"+DateUtils.getSimpleDateFormat()+".html";
		  TestLauncher.report = new ExtentReports(TestLauncher.TestReport);
		  
		  TestLauncher.TestMetrics = GlobalConstants.ArchivedExcelTestReportsDirectory+"/"+FileUtils.getFileNameWithoutExtension(GlobalConstants.TestExecutionDirectory, GlobalConstants.TestCasesFileIdentifier)+"_report_excel_"+DateUtils.getSimpleDateFormat()+".xls";
		  
		  ExcelUtils.setExcelFile(TestLauncher.TestCasesFile, 0);
		  ExcelUtils.loadExcelSheetIntoMap(TestCaseDriver.testCasesExcelMap);
		  MapUtils.setValueToEmptyFieldInAllMapObjects(TestCaseDriver.testCasesExcelMap, GlobalConstants.testSuiteName);
		  MapUtils.setValueToEmptyFieldInAllMapObjects(TestCaseDriver.testCasesExcelMap, GlobalConstants.testSuiteId);
		  
		  ExcelUtils.setExcelFile(TestLauncher.RepositoriesFile, 0);
		  ExcelUtils.loadExcelSheetIntoMap(TestCaseDriver.objectRepositoryMap);
		  
		  ExcelUtils.setExcelFile(TestLauncher.RepositoriesFile, 1);
		  ExcelUtils.loadExcelSheetIntoMap(TestCaseDriver.dataRepositoryMap);
		  
		  
	  }

	  
	  @AfterSuite
	  public void afterSuite() throws Exception { 
		  
		  if(ActionKeywords.driver!=null)
			  ActionKeywords.driver.quit();
		  
		  TestCaseLogger.onTestPlanEnd();
		  TestCaseLogger.clearLog();
		  
		  TestLauncher.report.flush();
		  
		  FileUtils.copyFileUsingChannel(TestLauncher.TestCasesFile, TestLauncher.ProcessesedTestCasesFile);
		  FileUtils.moveFile(TestLauncher.ProcessesedTestCasesFile, TestLauncher.ArchivedTestCasesFile);
		  
		  ExcelUtils.setExcelFile(TestLauncher.ArchivedTestCasesFile, 0);
		  ExcelUtils.writeToExcelColumn(TestCaseDriver.testCasesExcelMap, GlobalConstants.testResult, TestLauncher.ArchivedTestCasesFile);
		  ExcelUtils.writeToExcelColumn(TestCaseDriver.testCasesExcelMap, GlobalConstants.testCaseExecuted, TestLauncher.ArchivedTestCasesFile);
		  
		  
		  
		  
		  ExcelUtils.setExcelFile(TestLauncher.RepositoriesFile, 1);
		  ExcelUtils.writeToExcelColumn(TestCaseDriver.dataRepositoryMap, GlobalConstants.data,TestLauncher.RepositoriesFile);
		  
		  MapUtils.createMapHeader(TestCaseDriver.suiteWiseReportMap, GlobalConstants.suiteWiseReportHeader);
		  SuiteWiseReportGenerator.loadTestSuites(TestCaseDriver.testCasesExcelMap, TestCaseDriver.suiteWiseReportMap, GlobalConstants.testSuiteId, GlobalConstants.testSuiteName, GlobalConstants.report_TestSuite);
		  SuiteWiseReportGenerator.loadTestCasesInSuite(TestCaseDriver.testCasesExcelMap, TestCaseDriver.suiteWiseReportMap, GlobalConstants.report_TestSuite, GlobalConstants.totalTestCases);
		  SuiteWiseReportGenerator.loadTestCasesInSuiteByResult(TestCaseDriver.testCasesExcelMap, TestCaseDriver.suiteWiseReportMap, GlobalConstants.testCaseSkipped, GlobalConstants.report_TestSuite, GlobalConstants.skipped);
		  SuiteWiseReportGenerator.loadTestCasesInSuiteByResult(TestCaseDriver.testCasesExcelMap, TestCaseDriver.suiteWiseReportMap, GlobalConstants.testCasePassed, GlobalConstants.report_TestSuite, GlobalConstants.passed);
		  SuiteWiseReportGenerator.loadTestCasesInSuiteByResult(TestCaseDriver.testCasesExcelMap, TestCaseDriver.suiteWiseReportMap, GlobalConstants.testCaseFailed, GlobalConstants.report_TestSuite, GlobalConstants.failed);
		  SuiteWiseReportGenerator.loadFinalResults(TestCaseDriver.suiteWiseReportMap, GlobalConstants.report_TestSuite, GlobalConstants.totalTestCases, GlobalConstants.skipped, GlobalConstants.passed, GlobalConstants.failed);
		  ExcelUtils.createExcelFromMap(TestCaseDriver.suiteWiseReportMap, TestLauncher.TestMetrics, GlobalConstants.TestMetricsSuiteWise);
		  TestCaseDriver.suiteWiseReportMap.clear();
		  
		  ExcelUtils.setExcelFile(TestLauncher.TestMetrics, 0);
		  ExcelUtils.createChart(TestLauncher.TestMetrics);
	  }
}
