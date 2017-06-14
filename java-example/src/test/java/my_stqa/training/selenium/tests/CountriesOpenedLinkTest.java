package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class CountriesOpenedLinkTest extends TestBase {


  @Test
  public void countriesOpenedLink() {
    init();
    driver.get(getProperty("web.baseUrl") + "/admin/");
    goTo().loginAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));

    goTo().CountriesPage();
    //заходим на страницу редактирования Canada
    selenium().click(By.xpath(".//form[@name='countries_form']//tr[39]//i[@class='fa fa-pencil']"), 5);
    thereAndBackAgain(By.cssSelector("a[href='http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2']"));
    thereAndBackAgain(By.cssSelector("a[href='http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3']"));
    thereAndBackAgain(By.cssSelector("a[href='https://en.wikipedia.org/wiki/Regular_expression']"));
    thereAndBackAgain(By.cssSelector("a[href='http://www.addressdoctor.com/en/countries-data/address-formats.html']"));
    thereAndBackAgain(By.cssSelector("a[href='https://en.wikipedia.org/wiki/Regular_expression']"));
    thereAndBackAgain(By.cssSelector("a[href='https://en.wikipedia.org/wiki/List_of_countries_and_capitals_with_currency_and_language']"));
    thereAndBackAgain(By.cssSelector("a[href='https://en.wikipedia.org/wiki/List_of_country_calling_codes']"));
  }
  private void thereAndBackAgain(By locator) {
    //запоминаем идентификатор текущего окна
    String originalWindow = driver.getWindowHandle();
    //запоминаем идентификаторы уже открытых окон
    Set<String> existingWindows = driver.getWindowHandles();
    //кликаем кнопrу, которая открывает новое окно
    selenium().click(locator, 10);

    // ожидание появления нового окна,
    // идентификатор которого отсутствует в списке existingWindows,
    WebDriverWait wait = new WebDriverWait(driver, 15);
    String newWindow = wait.until(anyWindowOtherThan(existingWindows));
    //переключаемся в новое окно
    driver.switchTo().window(newWindow);
    // закрываем его
    driver.close();
    //возвращаемся в исходное окно
    driver.switchTo().window(originalWindow);
  }

  public ExpectedCondition<String> anyWindowOtherThan(Set<String> existingWindows) {
    return driver -> {
      Set<String> handles = driver.getWindowHandles();
      handles.removeAll(existingWindows);
      return handles.size() > 0 ? handles.iterator().next() : null;
    };
  }
}
