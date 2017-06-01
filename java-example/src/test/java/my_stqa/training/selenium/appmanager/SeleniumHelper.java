package my_stqa.training.selenium.appmanager;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumHelper {

  protected WebDriver driver;

  public SeleniumHelper(WebDriver driver) {
    this.driver = driver;
  }

  public void loginAdmin(String username, String password) {
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.name("login"));
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

  public void AppearancePage() {
    click(By.xpath("//ul[@id='box-apps-menu']//li[@id='app-']/a[span='Appearence']"));
  }

  public void HomePage() {
    driver.navigate().to("http://localhost/litecart/admin/");
  }

  public void TemplatePage() {
    click(By.xpath(".//li[@id='doc-template']/a[span='Template']"));
  }

  public void LogotypePage() {
    click(By.xpath(".//li[@id='doc-logotype']/a[span='Logotype']"));
  }
}
