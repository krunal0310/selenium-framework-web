package com.ecomsite.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ecomsite.actiondriver.Action;
import com.ecomsite.base.BaseClass;

public class IndexPage extends BaseClass{

	Action action=new Action();
	
	@FindBy(xpath = "//*[@class='logo']//h3[text()='Automation']")
	WebElement automationLogo;

	public IndexPage() {
		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyAutomationLogo() {
		return action.isDisplayed(driver, automationLogo);
	}
	
	public String verifyIndexPageUrl() {
		return action.getCurrentURL(driver);
	}
	
}
