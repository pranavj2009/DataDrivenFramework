package com.w2a.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void bankManagerLoginTest() {
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		if (!TestUtil.isTestRunnable(this.getClass().getSimpleName(), TestUtil.excel)) {
			throw new SkipException("Skipping the testcase as value of RunMode is set to N");
		}
		TestUtil.click("manager_login_button_CSS");
		(new WebDriverWait(driver, Integer.parseInt(CONFIG.getProperty("implicit.wait"))))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty("add_customer_button_XPATH"))));

		Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("add_customer_button_XPATH"))),
				"Login not successful");

		test.log(LogStatus.PASS, "Bank Manager logged in successfully");

		logger.debug("Login test executed successfully");

		Assert.assertEquals("a", "b");	// Test Failure


	}

}
