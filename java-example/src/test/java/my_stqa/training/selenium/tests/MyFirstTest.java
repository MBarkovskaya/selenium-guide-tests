package my_stqa.training.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

  private WebDriver driver;
  private WebDriverWait wait;

  @Before
  public void start() {
    FirefoxOptions options = new FirefoxOptions().setLegacy(true);
    driver = new FirefoxDriver(options);
    wait = new WebDriverWait(driver, 20);
  }

  @Test
  public void myFirstTest() {
    driver.navigate().to("http://www.bing.com/");
    driver.findElement(By.name("q")).sendKeys("webdriver");
    driver.findElement(By.name("go")).click();
    wait.until(titleIs("webdriver - Bing"));
  }

  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}
