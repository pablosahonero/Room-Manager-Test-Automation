package org.roommanager.test;

import org.testng.annotations.AfterTest;
//This is a small change

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;

public class CreateOutOfOrder {
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @BeforeTest
	  public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    baseUrl = "http://172.20.208.174:4042/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	  }

	  @Test(priority=0)
	  public void testVerifyRoomIsOutOfOrder() throws Exception {
		driver.get(baseUrl + "/admin/#/login");
		    
		WebElement signInButton = (new WebDriverWait(driver, 60))
		  	.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button")));
		signInButton.click();
	    
	    WebElement conferenceRoomsTab = (new WebDriverWait(driver, 60))
	  		  .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Conference Rooms")));
	    conferenceRoomsTab.click();
	    
	    WebElement roomsTable = (new WebDriverWait(driver, 60))
	    		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='roomsGrid']/div/div[2]/div/div/div[2]/div/div")));
	    
	    int numberOfRooms = (driver.findElements(By.xpath("//div[@id='roomsGrid']/div[2]/div/*")).size());
	    Random random = new Random();
	    int elementIndex = random.nextInt(numberOfRooms);
	   
	    Actions action = new Actions(driver);
	    action.doubleClick(driver.findElement(By.xpath("//div[@id='roomsGrid']/div[2]/div/div["+elementIndex+"]/div[3]/div[2]/div/span[2]")));
	    action.perform();
	    
	    WebElement roomsTableHeader = (new WebDriverWait(driver, 60))
	  		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.modal-header.ng-scope")));

	    driver.findElement(By.linkText("Out of Order Planning")).click();
	    
	    WebElement outOfOrderSaveButton  = (new WebDriverWait(driver, 60))
	    		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.info")));

	    outOfOrderSaveButton.click();

	    WebElement confirmationMessage  = (new WebDriverWait(driver, 60))
	  		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.ng-binding.ng-scope")));
	    
	    assertEquals("Room successfully Modified", confirmationMessage.getText());
	  }

	  @AfterTest
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }
}