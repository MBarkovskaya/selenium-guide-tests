package my_stqa.training.selenium.appmanager;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SeleniumHelper {

  protected WebDriver driver;
  private By locator;
  private String text;

  public SeleniumHelper(WebDriver driver) {
    this.driver = driver;
  }

  public void click(By locator) {
    WebElement element = driver.findElement(locator);
    Assert.assertNotNull(element);
    element.click();
  }

  public void click(By locator, long timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);

    try {
      WebElement element = wait.until((WebDriver d) -> d.findElement(locator));
      Assert.assertNotNull(element);
      element.click();
    } catch (Exception e) {
      Assert.fail("Unable to locate element");
    }
  }

  public WebElement findElement(By locator, long timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);

    try {
      return wait.until((WebDriver d) -> d.findElement(locator));
    } catch (Exception e) {
      return null;
    }
  }


  protected void navigateTo(String url) {
    driver.navigate().to(url);
  }

  public boolean isElementPresent(By locator) {
    //если элемент есть, то он найдется, если нет - будет выброшено исключение
    //так как Selenium не может знать есть элемент на странице или нет, поэтому нужно обернуть попытку поиска элементов в блок try
    try {
      //      явное ожидание
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(presenceOfElementLocated(locator));
      //      неявное ожидание
      //      driver.findElement(locator);
      return true;
    } catch (TimeoutException ex) {
      return false;
      //    } catch (NoSuchElementException ex) {
      //      return false;
    }
  }

  public void verifyFontWeight(String fontWeight) {
    try {
      int mpRegularStyle = Integer.parseInt(fontWeight);
      Assert.assertTrue(mpRegularStyle >= 700 && mpRegularStyle <= 900);
    } catch (NumberFormatException e) {
      Assert.assertEquals("bold", fontWeight);
    }
  }

  public void type(By locator, String text) {
    this.locator = locator;
    this.text = text;
    //метод делает клик по полю ввода
    click(locator);
    //не пытается ввести значения, которые заменят дефолтные
    if (text != null) {
      String existingText = driver.findElement(locator).getAttribute("value");
      //не пытается вводить текст, если он совпадает с существующим
      if (!text.equals(existingText)) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
      }
    }
  }
}
