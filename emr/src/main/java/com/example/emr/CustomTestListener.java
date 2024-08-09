package com.example.emr;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class CustomTestListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult tr) {
    	super.onTestSuccess(tr);
        // Example: Add a custom log message to the HTML report for successful tests
        //String testName = tr.getName();
        //String testClassName = tr.getTestClass().getName();
    	Reporter.log("Additional details for the passed tests:");
        Reporter.log("TestCase 1. Positive test case - User Login with Valid Credentials.");
        Reporter.log("TestCase 2. Negative test case - Login with Invalid Credentials.");
    }
}