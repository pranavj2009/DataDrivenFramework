package com.w2a.testcases;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@Test(dataProvider = "dp", dataProviderClass = TestUtil.class)
	public void addCustomerTest(Hashtable<String, String> table) {

		if (!TestUtil.isTestRunnable(this.getClass().getSimpleName(), TestUtil.excel)) {
			throw new SkipException("Skipping the testcase as value of RunMode is set to N");
		}
		
		// driver.findElement(By.cssSelector(OR.getProperty("manager_login_button"))).click();
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty("add_customer_button_XPATH"))));

		TestUtil.click("add_customer_button_XPATH");

		driver.manage().timeouts().implicitlyWait(Integer.parseInt(CONFIG.getProperty("implicit.wait")),
				TimeUnit.SECONDS);

		if (!table.get("runmode").equalsIgnoreCase("Y")) {
			throw new SkipException("Skipping the test-case as the value of runmode is N");
		}

		TestUtil.type("customer.firstName_CSS", table.get("firstName"));
		TestUtil.type("customer.lastName_CSS", table.get("lastName"));
		TestUtil.type("customer.postalCode_CSS", table.get("postCode"));
		TestUtil.click("addcustomer.add_button_CSS");

		new WebDriverWait(driver, Integer.parseInt(CONFIG.getProperty("implicit.wait")))
				.until(ExpectedConditions.alertIsPresent());

		Alert alert = driver.switchTo().alert();

		String message = alert.getText();

		Assert.assertTrue(message.contains("Customer added successfully"), "Customer added successfully");

		alert.accept();
		
	}

}
