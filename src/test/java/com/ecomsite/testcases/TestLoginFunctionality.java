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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ecomsite.base.BaseClass;
import com.ecomsite.pageobjects.IndexPage;
import com.ecomsite.pageobjects.LoginPage;
import com.ecomsite.utility.CommonStep;

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
		Assert.assertTrue(!indexPage.verifyAutomationLogo());
		Assert.assertEquals(indexPage.verifyIndexPageUrl(), prop.getProperty("indexpage_url"));
	}
	
	@Test(priority = 2)
	public void testLoginFunctionalityWithInCorrectCredentials() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		CommonStep.printValueInReport("Email ID: "+prop.getProperty("username"));
		CommonStep.printValueInReport("Password: "+prop.getProperty("invalid_password"));
		loginPage.loginWithCredentials(prop.getProperty("username"), prop.getProperty("invalid_password"));
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
