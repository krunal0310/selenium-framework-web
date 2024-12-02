package com.ecomsite.testcases;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ecomsite.base.BaseClass;
import com.ecomsite.dataprovider.DataProviders;
import com.ecomsite.pageobjects.IndexPage;
import com.ecomsite.pageobjects.LoginPage;
import com.ecomsite.utility.CommonStep;
import com.ecomsite.utility.TestDataSource;

public class TestLoginFunctionality extends BaseClass{
	
	LoginPage loginPage;
	IndexPage indexPage;

	public TestLoginFunctionality() {
		super();
	}
	
	@BeforeMethod
	public void setUp() throws InterruptedException {
		launchApp();
		loginPage=new LoginPage();
		indexPage=new IndexPage();
	}

	@Test(priority = 0)
	public void testLoginPageFields() {
		Assert.assertTrue(loginPage.verifyLoginTitle());
		Assert.assertTrue(loginPage.verifyEmailField());
		Assert.assertTrue(loginPage.verifyPasswordField());
	
	}

	@Test(priority = 1)
	public void testLoginFunctionalityWithCorrectCredentials() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		loginPage.loginWithCredentials(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(indexPage.verifyAutomationLogo());
		Assert.assertEquals(indexPage.verifyIndexPageUrl(), prop.getProperty("indexpage_url"));
	}

	@Test(priority = 2,dataProvider = "dynamicDataProvider", dataProviderClass = DataProviders.class)
	@TestDataSource(filePath = "./data/login_credential_invalid.xlsx", sheetName = "Credentials")
	public void testLoginFunctionalityWithInCorrectCredentials(String uname, String password) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		CommonStep.printValueInReport("Email ID: "+uname);
		CommonStep.printValueInReport("Password: "+password);

		loginPage.loginWithCredentials(uname, password);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement blinkMessage = wait.until(ExpectedConditions.visibilityOf((loginPage.getBlinkErrorMsg())));
            System.out.println("Blink Message Text: " + blinkMessage.getText());
            Assert.assertTrue(loginPage.verifyBlinkErrorMsgPresent());

        } catch (Exception e) {
        	Assert.assertFalse(true);
        }
		
	}
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
