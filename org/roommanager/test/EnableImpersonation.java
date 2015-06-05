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

public class EnableImpersonation {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeTest
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://172.20.208.174:4042/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test(priority=3)
  public void testVerifyImpersonationIsEnabled() throws Exception {
	driver.get(baseUrl + "/admin/#/login");
	    
	WebElement signInButton = (new WebDriverWait(driver, 60))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button")));
	signInButton.click();
    
    WebElement ImpersonationTab = (new WebDriverWait(driver, 60))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Impersonation")));
    ImpersonationTab.click();

    WebElement impersonationCheckBox = (new WebDriverWait(driver, 60))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='checkbox']")));
    impersonationCheckBox.click();
    
    WebElement impersonationSaveButton = (new WebDriverWait(driver, 60))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.info.pull-right")));
    impersonationSaveButton.click();
    
    WebElement impersonationMessage = (new WebDriverWait(driver, 60))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.ng-binding.ng-scope")));
    
    assertEquals("Impersonation is now enabled.", impersonationMessage.getText());
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

