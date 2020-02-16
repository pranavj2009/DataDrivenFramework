package com.w2a.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;

public class TestBase {

	public static WebDriver driver;
	public static final Properties CONFIG = new Properties();
	protected static final Properties OR = new Properties();
	private static FileInputStream fis;
	public static Logger logger = Logger.getLogger("devpinoyLogger");
	public static ExtentReports rep = ExtentManager.getExtent();
	public static ExtentTest test;
	public String browser;

	// public static WebDriverWait wait = new WebDriverWait(driver,
	// Integer.parseInt(CONFIG.getProperty("implicit.wait")));

	public static void loadConfigProperties() {

		try {
			fis = new FileInputStream("./src/test/resources/properties/Config.properties");
			CONFIG.load(fis);
			logger.debug("Config properties loaded");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static void loadORProperties() {

		try {
			fis = new FileInputStream("./src/test/resources/properties/OR.properties");
			OR.load(fis);
			logger.debug("OR properties loaded");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private String getBrowser() {
		logger.debug("Getting browser");
		if (System.getenv("browser") != null || !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
			logger.debug("Browser provided by system");

		} else {
			browser = CONFIG.getProperty("browser");
		}

		CONFIG.setProperty("browser", browser);

		return browser;
	}

	private void loadBrowser() {

		if (getBrowser().equalsIgnoreCase("chrome")) {
			driver = getChromeDriver();
			logger.debug("Chrome Driver instance created");
		} else if (getBrowser().equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
	}

	private WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/executables/chromedriver.exe");
		return new ChromeDriver();
	}

	public boolean isElementPresent(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(CONFIG.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@BeforeSuite
	public void setUp() {
		loadConfigProperties();
		loadORProperties();
		loadBrowser();
		driver.get(CONFIG.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(CONFIG.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		logger.debug("URL " + CONFIG.getProperty("testsiteurl") + " launched successfully");
		
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			logger.debug("Browser quit successfully");
		}
	}
}
