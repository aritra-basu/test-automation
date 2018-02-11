package com.test.automation.testMetrics;

import java.util.Map;
import java.util.Map.Entry;

import com.test.automation.executionEngine.TestCaseDriver;
import com.test.automation.utilities.MapUtils;

public class SuiteWiseReportGenerator {
	
	public static void loadTestSuites(Map<Integer, Object[]> testCasesMap, Map<String, Object[]> reportMap, String testSuiteIdColumn, String testSuiteNameColumn, String testSuiteReportColumn) throws Exception {
		int testSuiteIdColumnIndex = MapUtils.getMapObjectIndexByHeader(testCasesMap, testSuiteIdColumn);
		int testSuiteNameColumnIndex = MapUtils.getMapObjectIndexByHeader(testCasesMap, testSuiteNameColumn);
		int testSuiteReportColumnIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, testSuiteReportColumn);
		for(Entry<Integer, Object[]> entry : testCasesMap.entrySet()) {
			if(entry.getKey() > 0) {
				Object[] obj = entry.getValue();
				Object[] reportObj = new Object[10];
				reportObj[testSuiteReportColumnIndex] = obj[testSuiteIdColumnIndex].toString() + " - " + obj[testSuiteNameColumnIndex].toString();
				reportMap.put(obj[testSuiteNameColumnIndex].toString(), reportObj);
			
			}
		}
	
	}
	
	public static void loadTestCasesInSuite(Map<Integer, Object[]> testCasesMap, Map<String, Object[]> reportMap, String baseReportColumn, String reportColumnToLoad) throws Exception{
		String testSuiteName = null;
		int baseColumnIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, baseReportColumn);
		int columnIndexToLoad = MapUtils.getAltMapObjectIndexByHeader(reportMap, reportColumnToLoad);
		for(Entry<String, Object[]> entry : reportMap.entrySet()) {
			if(entry.getKey() != "0") {
				Object[] reportObj = entry.getValue();
				testSuiteName = reportObj[baseColumnIndex].toString().substring(reportObj[baseColumnIndex].toString().indexOf("-")+1).trim();
				reportObj[columnIndexToLoad] = MapUtils.getNumberOfMapEntriesBySingleValue(testCasesMap, testSuiteName);
			}
		}
	}
	
	public static void loadTestCasesInSuiteByResult(Map<Integer, Object[]> testCasesMap, Map<String, Object[]> reportMap, String status, String baseReportColumn, String reportColumnToLoad) throws Exception{
		String testSuiteName = null;
		int baseColumnIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, baseReportColumn);
		int columnIndexToLoad = MapUtils.getAltMapObjectIndexByHeader(reportMap, reportColumnToLoad);
		for(Entry<String, Object[]> entry : reportMap.entrySet()) {
			if(entry.getKey() != "0") {
				Object[] reportObj = entry.getValue();
				testSuiteName = reportObj[baseColumnIndex].toString().substring(reportObj[baseColumnIndex].toString().indexOf("-")+1).trim();
				reportObj[columnIndexToLoad] = MapUtils.getNumberOfMapEntriesByTwoValues(testCasesMap, testSuiteName, status);
			}
		}
	}
	
	public static void loadFinalResults(Map<String, Object[]> reportMap, String testSuiteColumn, String totalTestCasesInSuiteColumn, String skippedColumn, String totalPassedColumn, String totalFailedColumn) throws Exception {
		int testSuiteReportIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, testSuiteColumn);
		int totalTestCasesInSuiteReportIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, totalTestCasesInSuiteColumn);	
		int testCasesSkippedInSuiteReportIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, skippedColumn);
		int testCasesPassedInSuiteReportIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, totalPassedColumn);
		int testCasesFailedInSuiteReportIndex = MapUtils.getAltMapObjectIndexByHeader(reportMap, totalFailedColumn);
		
		
		Object[] finalObject = new Object[10];
		finalObject[testSuiteReportIndex] = "Total";
		finalObject[totalTestCasesInSuiteReportIndex] = MapUtils.getTotalOfColumn(reportMap, totalTestCasesInSuiteColumn);
		finalObject[testCasesSkippedInSuiteReportIndex] = MapUtils.getTotalOfColumn(reportMap, skippedColumn);
		finalObject[testCasesPassedInSuiteReportIndex] = MapUtils.getTotalOfColumn(reportMap, totalPassedColumn);
		finalObject[testCasesFailedInSuiteReportIndex] = MapUtils.getTotalOfColumn(reportMap, totalFailedColumn);

		TestCaseDriver.suiteWiseReportMap.put("Total", finalObject);
	}

}
