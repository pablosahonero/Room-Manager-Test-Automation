package org.roommanager.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateResource {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeTest
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://172.20.208.174:4042/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    WebElement html = driver.findElement(By.tagName("html"));
    html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
  }

  @Test(priority=1)
  public void testVerifyResourceIsCreated() throws Exception {
    driver.get(baseUrl + "/admin/#/login");
    
    WebElement signInButton = (new WebDriverWait(driver, 60))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button")));
    signInButton.click();
    
    WebElement resourcesTab = (new WebDriverWait(driver, 60))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Resources")));
    resourcesTab.click();
    
    WebElement createResourceButton = (new WebDriverWait(driver, 60))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/div/button")));

    createResourceButton.click();
    
    WebElement createResourceTab = (new WebDriverWait(driver, 60))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.modal-header.ng-scope")));
    
    String resourceName = generateRandomString(10);
    String resourceDisplayName = generateRandomString(10);
    
    driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
    driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(resourceName);
    driver.findElement(By.xpath("(//input[@type='text'])[4]")).clear();
    driver.findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys(resourceDisplayName);
    driver.findElement(By.xpath("//textarea")).clear();
    driver.findElement(By.xpath("//textarea")).sendKeys(resourceName);
    driver.findElement(By.cssSelector("button.info")).click();
   
    WebElement searchResourceTextField = (new WebDriverWait(driver, 180))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text']")));
    searchResourceTextField.clear();
    searchResourceTextField.sendKeys(resourceName);
    
    WebElement createdResourceName = (new WebDriverWait(driver, 60))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.ng-scope > span.ng-binding")));

    assertEquals(resourceName, createdResourceName.getText());
  }

  @AfterTest
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  
  private String generateRandomString(int size){
	  char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	  StringBuilder sb = new StringBuilder();
	  Random random = new Random();
	  for (int counter = 0; counter < size; counter++) {
	      char character = chars[random.nextInt(chars.length)];
	      sb.append(character);
	  }
	  return sb.toString();
  }
}
