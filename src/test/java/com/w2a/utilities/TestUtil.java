package com.w2a.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;

public class TestUtil extends TestBase {

	private static WebElement element;

	public static String scrDestFileName = new Date().toString().replace(" ", "").replace(":", "_") + ".jpg";

	public static String scrDestFilePath = System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\";

	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + TestBase.CONFIG.getProperty("CustomerDataSheetLocation"));

	public static void captureScreen() {
		try {

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(scrFile, new File(scrDestFilePath + scrDestFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static WebElement getElement(String locator) {
		if (locator.endsWith("_CSS")) {
			element = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			element = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_LINKTEXT")) {
			element = driver.findElement(By.linkText(OR.getProperty(locator)));
		}

		return element;
	}

	public static void click(String locator) {
		getElement(locator).click();
		test.log(LogStatus.INFO, "Clicked on " + locator);
	}

	public static void type(String locator, String value) {
		getElement(locator).sendKeys(value);
		test.log(LogStatus.INFO, "Entering value: " + value + " in locator " + locator);
	}

	public static void select(String dropdownLocator, String optionText) {
		Select select = new Select(getElement(dropdownLocator));
		select.selectByVisibleText(optionText);
		test.log(LogStatus.INFO, "Selecting value " + optionText + " for dropdown " + dropdownLocator);
	}

	@DataProvider(name = "dp")
	public Object[][] getData(Method m) {

		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];

		Hashtable<String, String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			table = new Hashtable<String, String>();

			for (int colNum = 0; colNum < cols; colNum++) {

				// data[0][0]
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}

		}

		return data;

	}

	public static boolean isTestRunnable(String testName, ExcelReader excel) {

		String sheetName = "TCRM";
		int rows = excel.getRowCount(sheetName);

		for (int rNum = 2; rNum <= rows; rNum++) {

			String testCase = excel.getCellData(sheetName, "TC", rNum);

			if (testCase.equalsIgnoreCase(testName)) {

				String runmode = excel.getCellData(sheetName, "runmode", rNum);

				if (runmode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}

		}
		return false;
	}
}
