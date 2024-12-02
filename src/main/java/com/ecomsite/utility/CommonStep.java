package com.ecomsite.utility;

import com.aventstack.extentreports.Status;

public class CommonStep extends ExtentManager{

	public static void printValueInReport(String value) {
		test.log(Status.INFO, value);
		
	}
}
