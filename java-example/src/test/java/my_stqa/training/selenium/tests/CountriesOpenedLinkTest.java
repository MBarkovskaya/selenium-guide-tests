package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class CountriesOpenedLinkTest extends TestBase {


  @Test
  public void countriesOpenedLink() {
    init();
    driver.get(getProperty("web.baseUrl") + "/admin/");
    goTo().loginAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));

    goTo().CountriesPage();
    //заходим на страницу редактирования Canada
    selenium().click(By.cssSelector("tr.row:not(.header):not(.footer) td:last-child"), 5);
    List<WebElement> linkslist = driver.findElements(By.xpath(".//a[./i[@class='fa fa-external-link']]"));
    for (WebElement link : linkslist) {
      String linktext = link.getAttribute("href");
      thereAndBackAgain(By.cssSelector(String.format("a[href='%s']", linktext)));
    }

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
