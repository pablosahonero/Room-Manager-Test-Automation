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

public class DisableImpersonation{
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

	  @Test(priority=4)
	  public void testVerifyImpersonationIsDisabled() throws Exception {
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
	  		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='toast-container']/div")));
	    
	    assertEquals("Impersonation is now disabled.", driver.findElement(By.xpath("//div[@id='toast-container']/div")).getText());
	  }

	  @AfterTest
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	}
