package com.ecomsite.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ecomsite.actiondriver.Action;
import com.ecomsite.base.BaseClass;

public class LoginPage extends BaseClass {

	Action action=new Action();
	
	@FindBy(xpath = "//h1[@class='login-title']")
	WebElement loginTitle;

	@FindBy(id = "userEmail")
	WebElement emailField;

	@FindBy(id = "userPassword")
	WebElement passwordField;
	
	@FindBy(xpath = "//input[@name='login']")
	WebElement loginButton;

	@FindBy(xpath = "//div[@aria-label='Incorrect email or password.']")
    WebElement blinkErrorMsg;

	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	public boolean verifyLoginTitle() { 
		return action.isDisplayed(driver, loginTitle);
	}

	public boolean verifyEmailField() {
		return action.isDisplayed(driver, emailField);
	}

	public boolean verifyPasswordField() {
		return action.isDisplayed(driver, passwordField);
	}
	
	public void loginWithCredentials(String userName,String password) {
		action.type(emailField, userName);
		action.type(passwordField, password);
		action.click(driver, loginButton);
	}

	public WebElement getBlinkErrorMsg() {
		return blinkErrorMsg;
	}

	public boolean verifyBlinkErrorMsgPresent() {
		return action.isDisplayed(driver, blinkErrorMsg);
	}
}
