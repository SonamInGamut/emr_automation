package com.example.emr;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

	private static Logger log = LogManager.getLogger(Login.class);

	static void LoginToEMR(WebDriver driver, String username, String password) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(360));

		// Locate and enter the user name in the user name element.
		WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"/html/body/app-root/ui-view/app-root/ui-view/app-login/div/mat-sidenav-container/mat-sidenav-content/div/form/div/mat-form-field[1]/div/div[1]/div[2]/input")));
		usernameInput.sendKeys(username);

		// Locate and enter the password in the password element.
		WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"/html/body/app-root/ui-view/app-root/ui-view/app-login/div/mat-sidenav-container/mat-sidenav-content/div/form/div/mat-form-field[2]/div/div[1]/div[2]/input")));
		passwordInput.sendKeys(password);

		// Click login button
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"/html/body/app-root/ui-view/app-root/ui-view/app-login/div/mat-sidenav-container/mat-sidenav-content/div/form/div/button/span")))
				.click();

		Thread.sleep(400);

		String pageTitle = driver.getTitle();

		// Output login result
		if (pageTitle.equals("EMR > Schedule")) {

			log.info("Login attempt successful for email: \"" + username + "\", and password: \"" + password + "\"");

			WebElement Profile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/app-root/ui-view/app-dashboard/div/app-pages/div/header/mat-toolbar/div/div[3]/app-user-menu/button/span/mat-icon")));
			Profile.click();

			WebElement Logout = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/app-root/ui-view/app-dashboard/div/div[2]/div[2]/div/div/div/a[4]/span")));
			Logout.click();

		} else {
			log.error("Invalid login attempt with username: \"" + username + "\", and password: \"" + password + "\"");

			String screenshotPath = "C:\\Users\\Admin\\eclipse-workspace\\Maven_EMR_Project\\ScreenshotPath\\";
			TakeScreenshot.Screenshot(driver, screenshotPath);
			
			usernameInput.clear();
			passwordInput.clear();
		}
	}
}
