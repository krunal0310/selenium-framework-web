package com.ecomsite.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.ecomsite.actiondriver.Action;
import com.ecomsite.base.BaseClass;

public class ListenerClass extends ExtentManager implements ITestListener {

	Action action = new Action();

	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Pass Test case is: " + result.getName());
		}
	}

	public void onTestFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			test.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			String imgPath = action.screenShot(BaseClass.getDriver(), result.getName());

			test.fail("ScreenShot is Attached", MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());

			Throwable throwable = result.getThrowable();
			if (throwable != null) {
				// Find the stack trace element pointing to the test method in the source code
				StackTraceElement[] stackTraceElements = throwable.getStackTrace();
				for (StackTraceElement element : stackTraceElements) {
					// Check if the stack trace element belongs to the user's test class (i.e.,
					// avoid internal classes)
					if (element.getClassName().equals(result.getTestClass().getName())) {
						// Get the class name, method name, file name, and line number
						String qualifiedClassName = element.getClassName();
						String methodName = element.getMethodName();
						String fileName = element.getFileName();
						int lineNumber = element.getLineNumber();

						// Log these details to the report
						test.log(Status.FAIL, "Failure occurred in file: " + fileName + ", Class: " + qualifiedClassName
								+ ", Method: " + methodName + ", Line: " + lineNumber);
						try (BufferedReader reader = new BufferedReader(
								new FileReader("src/test/java/" + qualifiedClassName.replace('.', '/') + ".java"))) {
							String codeLine = null;
							for (int i = 1; i <= lineNumber; i++) {
								codeLine = reader.readLine(); // Read line by line until reaching the failure line
								if (codeLine == null)
									break; // Stop if end of file is reached
							}
							if (codeLine != null) {
								test.log(Status.FAIL, "Failure code: " + codeLine.trim());
							} else {
								test.log(Status.FAIL, "Could not retrieve the exact line of code.");
							}
						} catch (IOException e) {
							test.log(Status.FAIL, "Failed to read source code due to: " + e.getMessage());
						}
						break; // Exit after finding the first relevant stack trace element
					}
				}
			}

			
		}
	}

	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}
}