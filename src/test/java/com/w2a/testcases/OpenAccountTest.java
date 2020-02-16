package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {

		if (!TestUtil.isTestRunnable(this.getClass().getSimpleName(), TestUtil.excel)) {
			throw new SkipException("Skipping the testcase as value of RunMode is set to N");
		}
		//System.out.println("Here: "+data.get("customer"));
		TestUtil.click("open_account_button_XPATH");
		TestUtil.select("open_account.customer_CSS", data.get("Customer"));
		TestUtil.select("open_account.currency_CSS", data.get("Currency"));
		TestUtil.click("open_account.proceed_button_XPATH");
		Thread.sleep(2000);
		Alert alert = (new WebDriverWait(driver, 30)).until(ExpectedConditions.alertIsPresent());
		alert.accept();

	}

}
