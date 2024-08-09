package com.example.emr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(CustomTestListener.class)
public class EMRInit {

	@Test
	public void TestLoginFunc() throws InterruptedException {
		
	    // Automatically download and set up the appropriate ChromeDriver
	    WebDriverManager.chromedriver().setup();
	    // Create a new ChromeDriver instance
	    WebDriver driver = new ChromeDriver();

		driver.get("https://gamutindia.com/emr1/#/login");
		driver.manage().window().maximize();

		String filePath = "C:\\Users\\Admin\\eclipse-workspace\\emr\\Dataset\\Login_data.xlsx";
		String sheetName = "Sheet1";

		try {
			// Load the Excel file
			Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));

			// Get the desired sheet from the workbook
			Sheet sheet = workbook.getSheet(sheetName);

			// Iterate over the rows of the sheet to retrieve login data
			for (Row row : sheet) {

				// Check if the cells are not null before trying to retrieve cell values
				if (row.getCell(0) != null && row.getCell(1) != null) {
					String username = row.getCell(0).getStringCellValue();
					String password = row.getCell(1).getStringCellValue();

					// Skip the header row
					if (row.getRowNum() == 0) {
						continue;
					}

					// Perform login using the retrieved data
					Login.LoginToEMR(driver, username, password);
				} else {
					// Skip this row and continue with the next one
					continue;
				}
			}
			// Close the workbook
			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
		        driver.quit();
		    }
		}
	}

	@AfterSuite
    public void SendEmail() {

		// Replace the placeholder in the log file path pattern
		String logFilePathPattern = "C:\\Users\\Admin\\eclipse-workspace\\emr\\logs\\test-%d{dd-MM-yyyy}.log";

		// Create a SimpleDateFormat instance for formatting the timestamp
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// Format the current date and time
		String formattedTimestamp = dateFormat.format(new Date());

		// Replace the placeholder in the log file path pattern
		String logFilePath = logFilePathPattern.replace("%d{dd-MM-yyyy}", formattedTimestamp);

		// Create a File object using the log file path
		File logFile = new File(logFilePath);

		// emailable-report.html file path
		String emailableReportPath = "C:\\Users\\Admin\\eclipse-workspace\\Maven_EMR_Project\\test-output\\emailable-report.html";

		// Create a File object using the report file path
		File reportFile = new File(emailableReportPath);

		String screenshotPath = "C:\\Users\\Admin\\eclipse-workspace\\Maven_EMR_Project\\ScreenshotPath\\NTC_screenshot.png";

		File finalScreenshot = new File(screenshotPath);

		EmailUtils.sendEmailWithAttachments(reportFile, logFile, finalScreenshot);
    }
    
}
