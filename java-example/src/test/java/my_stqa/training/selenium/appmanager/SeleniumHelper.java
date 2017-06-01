package my_stqa.training.selenium.appmanager;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumHelper {

  protected WebDriver driver;

  public SeleniumHelper(WebDriver driver) {
    this.driver = driver;
  }

  protected void type(By locator, String text) {
    click(locator);
    //проверяем хранится ли в переменной ссылка на объект
    if (text != null) {
      //тогда извлекаем из поля то значение, которое в нем хранится
      //по правилам вебприложения, тот текст, который мы видим в поле ввода, является значением атрибута value
      //обычный метод getText всегда возвращает пустую строчку
      //для всех остальных аргументов, кроме полей ввода нужно использовать метод getText
      String existingText = driver.findElement(locator).getAttribute("value");
      //если текст не совпадает с существующим текстом, тогда выполняем какие-то действия с полем ввода
      if (!text.equals(existingText)) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
      }
    }
  }

  protected void click(By locator) {
    WebElement element = driver.findElement(locator);
    Assert.assertNotNull(element);
    element.click();
  }

  protected void click(By locator, long timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);

    try {
      WebElement element = wait.until((WebDriver d) -> d.findElement(locator));
      Assert.assertNotNull(element);
      element.click();
    } catch (Exception e) {
      Assert.fail("Unable to locate element");
    }
  }

  protected void navigateTo(String url) {
    driver.navigate().to(url);
  }

}
