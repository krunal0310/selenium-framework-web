package com.ecomsite.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestProductFunctionality {
	WebDriver driver;

	@BeforeMethod
	public void setUp() throws InterruptedException {
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.id("userEmail")).sendKeys("postman.learning@yopmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Test@123#");
		driver.findElement(By.xpath("//input[@name='login']")).click();
	}

	@Test(priority = 0)
	public void testEndToEndProductCheckOutProcess() throws InterruptedException {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		Assert.assertTrue(driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']/label")).getText().equals(""));
		WebElement addToCartButton= driver.findElement(By.xpath("//div[@class='card']//b[contains(text(),'Malton')]/ancestor::div[@class='card-body']/button[text()=' Add To Cart']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
        wait.pollingEvery(Duration.ofSeconds(1)).until(ExpectedConditions.visibilityOf(addToCartButton));
       
        js.executeScript("arguments[0].click();", addToCartButton);
        
        wait.pollingEvery(Duration.ofSeconds(1)).until(ExpectedConditions.textToBe(By.xpath("//button[@routerlink='/dashboard/cart']/label"), "1"));
		Assert.assertTrue(driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']/label")).getText().equals("1"));
	
	}

	

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
