# test-automation

# feature-overview
1. This is a keyword driven test automation framework.
2. The automation engine is the code-base containing the keywords functionality, test execution logic and all utilities - the project is a Maven project.
3. Input is an excel sheet containing test cases (which are fed into the automation engine).
4. There is one testNG class which contains one test method which takes the test cases names from the data provider and is executed n number of times for all test cases.
5. The test cases excel sheet containing the test cases with all relevant information and the data and object repository excel are read into the system using Apache POI.
6. The test hierarchy, as seen in the test sheet are: 1 test suite would contain multiple test cases and one test case would contain multiple test steps.
7. Each test step consists of 3 components (separated by a '#' separator): actionKeyword#pageObject#testData (if any component is not applicable for a step, it stays as empty)
8. Each test case can be either executed or not based on a flag set in the test sheet (already executed flag), this means not every time all test cases have to run but any number of test cases can be chosen to run at one go.
9. The output of test execution are different kind of reports - HTML reports using ExtentReports plugin and also custom Excel reports.

# test-execution

1. To execute test cases using the automation engine locally, Eclipse IDE should be installed (TestNG should be installed in the Eclipse IDE) and the project folder imported into workspace or cloned from github.

2. The test repository folder is named as testrepo. 
<br/>2.1. Test cases sheet, named test_plan_<<test_plan_name>>, is placed within the folder test-library. Test cases to be executed should have the flag 'N' in the ALready Executed column.
<br/>2.2. Object and data repository sheet, named object_data repository (which containis 2 sheets - Object-Repository which contains the required page objects to be interacted with and the Data-Repository contains the test data labels) is also placed with the test-library. 

3. Once the test cases sheet is placed in the location, either right-click to run testng.xml as testng suite or right-click to run pom.xml with Maven test run configuration.

4. After execution complete, following type of output are generated
<br/>4.1. All console output in Eclipse console
<br/>4.2. All testng default reports in the test-output folder
<br/>4.3. The test cases sheet which was executed, is archived with timestamp appended under testrepo --> test-library --> archived --> test-cases (PASSED/FAILED written in the Test Result column and Y written in the Already Executed column for the executed test cases).
<br/>4.4. The HTML reports with timestamp are generated under test-library --> archived --> test-reports --> html-reports.
<br/>4.5. The custom excel reports with timestamp are generated under test-library --> archived --> test-reports --> excel-reports.
<br/>4.6. The test execution logs with timestamp are generated under test-library --> archived --> test-logs.

5. Also data is written to the Data-Repository sheet of the object_data_repository sheet in any field which is set through test execution steps.
