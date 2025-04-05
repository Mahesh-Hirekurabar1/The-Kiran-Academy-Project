package com.project;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginWithCredentials extends SwagLabWebAutomating {

	// TC_001:Valid Credentials.
	@Test(priority = 1)
	public void ValidCredentials() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		String Actual = driver.getTitle();
		Assert.assertEquals(Actual, "Swag Labs");

	}

	// TC_002:InValid UserName and valid Password Credentials.
	@Test
	public void InValidUserCredentials() {
		driver.findElement(By.id("user-name")).sendKeys("Mahesh Hirekurabar");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		String Actual = driver.getTitle();
		Assert.assertEquals(Actual, "Products", "Invalid Credentials");

	}

	// TC_003:Valid UserName and Invalid Password Credentials.
	@Test
	public void InValidPsdCredentials() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("Mahesh@123");
		driver.findElement(By.id("login-button")).click();
		String Actual = driver.getTitle();
		Assert.assertEquals(Actual, "Products", "Invalid Credentials");

	}

	// TC_004:InValid Credentials.
	@Test
	public void InValidCredentials() {
		driver.findElement(By.id("user-name")).sendKeys("Mahesh Hirekurabar");
		driver.findElement(By.id("password")).sendKeys("Mahesh@123");
		driver.findElement(By.id("login-button")).click();
		String Actual = driver.getTitle();
		Assert.assertEquals(Actual, "Products");

	}

	// TC_005:When user not enter any Credentials.
	@Test
	public void NoCredentials() {
		driver.findElement(By.id("login-button")).click();
		String Actual = driver.getTitle();
		Assert.assertEquals(Actual, "Prodcuts");

	}

	// Test Case for Clicking on Menu button and selecting 1 Option using Fluent
	// wait.
	@Test
	public void MenuButton() {
		// Log in first
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		// Wait for the menu button to be clickable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
		menuButton.click();

		// Verify menu button by visibility of logout button
		WebElement logout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
		Assert.assertTrue(logout.isDisplayed(), "Logout link not visible - Menu may not opened.");
	}

	// TC for drop down using Select class
	@Test
	public void dropDown() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		// Using Wait and condition
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement dropDown = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("product_sort_container")));
		Select sc = new Select(dropDown);
		sc.selectByIndex(0);

		// Without using wait dropDown Selecting
//		WebElement dropDown=driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select"));
//		Select sc=new Select(dropDown);
//		sc.selectByIndex(0);

		WebElement Selectoption = sc.getFirstSelectedOption();
		System.out.println(Selectoption);
		Assert.assertEquals(Selectoption, "Name (A to Z)", "First Value is Expected!!!");

	}

	// TC for Adding Elements to cart
	@Test
	public void AddingItemTOCart() {
		// Step 1: Login
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		// Step 2: Add item to cart
		driver.findElement(By.name("add-to-cart-sauce-labs-bike-light")).click();

		// Step 3: Go to cart
		driver.findElement(By.className("shopping_cart_link")).click();

		// Step 4: Verify item is visible in cart
		WebElement item = driver.findElement(By.className("inventory_item_name"));
		String actualItemName = item.getText();
		Assert.assertEquals(actualItemName, "Sauce Labs Bike Light", "Item not found inside the cart!");
	}

	// TC for Adding item and remove item from element
	@Test
	public void RemoveItemFromCart() {
		// Step 1: Login
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		// Step 2: Add item first
		driver.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();

		// Step 3: Go to cart
		driver.findElement(By.className("shopping_cart_link")).click();

		// Step 4: Remove item
		driver.findElement(By.id("remove-sauce-labs-backpack")).click();

		// Step 5: Verify item is not present
		boolean isItemPresent = driver.findElements(By.className("inventory_item_name")).stream()
				.anyMatch(el -> el.getText().equals("Sauce Labs Backpack"));

		Assert.assertFalse(isItemPresent, "Item was not removed from the cart!");
	}

}
