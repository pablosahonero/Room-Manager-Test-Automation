package org.roommanager.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchASpecificRoom {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeTest
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://172.20.208.174:4042/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();
  }

  @Test(priority=2)
  public void testVerifyASpecificRoomIsFound() throws Exception {
	driver.get(baseUrl + "/admin/#/login");
	    
	WebElement signInButton = (new WebDriverWait(driver, 60))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button")));
	signInButton.click();
    
    WebElement resourcesTab = (new WebDriverWait(driver, 60))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Resources")));
    resourcesTab.click();
    
    WebElement searchResourceTextField = (new WebDriverWait(driver, 60))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text']")));

    searchResourceTextField.clear();
    searchResourceTextField.sendKeys("43");
    
    WebElement foundResourceName = (new WebDriverWait(driver, 60))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.ng-scope > span.ng-binding")));

    assertEquals("43", foundResourceName.getText());
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

