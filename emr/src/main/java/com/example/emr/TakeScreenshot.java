package com.example.emr;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshot {
	
	public static void Screenshot(WebDriver driver, String customPath) {
		try {
			if (driver instanceof TakesScreenshot) {
				TakesScreenshot ts = (TakesScreenshot) driver;
				File screenshotFile = ts.getScreenshotAs(OutputType.FILE);

				// Create a Path object from the customPath provided
				File destination = new File(customPath);
				if (!destination.exists()) {
					destination.mkdirs(); // Create the directory if it doesn't exist
				}
				//String timestamp = Long.toString(System.currentTimeMillis());
				//String screenshotFileName = "screenshot_" + timestamp + ".png";
				String screenshotFileName = "NTC_screenshot.png";

				File finalScreenshot = new File(destination, screenshotFileName);
				screenshotFile.renameTo(finalScreenshot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
