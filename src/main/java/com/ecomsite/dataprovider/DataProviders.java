package com.ecomsite.dataprovider;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.ecomsite.utility.ExcelLibrary;
import com.ecomsite.utility.TestDataSource;

public class DataProviders {

	@DataProvider(name = "dynamicDataProvider")
	public Object[][] dynamicDataProvider(Method method, ITestContext context) {
		// Fetch the custom file path and sheet name from the test annotation
		TestDataSource testDataSource = method.getAnnotation(TestDataSource.class);
		if (testDataSource == null) {
			throw new IllegalArgumentException("Missing @TestDataSource annotation on test method!");
		}
		String filePath = testDataSource.filePath();
		String sheetName = testDataSource.sheetName();

		// Use ExcelUtility to read data
		return ExcelLibrary.getExcelData(filePath, sheetName);
	}

}