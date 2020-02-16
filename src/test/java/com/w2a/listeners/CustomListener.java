package com.w2a.listeners;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.MonitoringMail;
import com.w2a.utilities.TestConfig;
import com.w2a.utilities.TestUtil;

public class CustomListener extends TestBase implements ITestListener, ISuiteListener {

	public void onTestStart(ITestResult result) {
		test = rep.startTest(result.getName().toUpperCase());

	}

	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("Capturing screenshot");
		TestUtil.captureScreen();
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.scrDestFileName + "\">Screen Shot</a>");
		test.log(LogStatus.FAIL,
				result.getName().toUpperCase() + " failed due to " + result.getThrowable().getMessage());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.scrDestFileName));
		rep.endTest(test);
		rep.flush();

	}

	public void onTestSkipped(ITestResult result) {
		
		test.log(LogStatus.SKIP, "Test skipped as value Runnable is N");
		rep.endTest(test);
		rep.flush();

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {

	}

	public void onStart(ISuite suite) {
		MonitoringMail mail = new MonitoringMail();
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenFrameworkW2a/Extent_20Report/extent.html");
			String messageBody = InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenFrameworkW2a/Extent_20Report/extent.html";

			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

}
