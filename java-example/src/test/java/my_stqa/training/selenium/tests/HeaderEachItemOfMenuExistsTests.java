package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class HeaderEachItemOfMenuExistsTests extends TestBase {

  @Test
  public void HeaderItemExists() throws InterruptedException {
    init();
    int j = 0;
    int numBut = driver.findElements(By.cssSelector("li#app-")).size();
    while (j < numBut) {
      driver.get(getProperty("web.baseUrl") + "/admin/");
      List<WebElement> elements = driver.findElements(By.cssSelector("li#app-"));
      elements.get(j).click();
      assertTrue(selenium().isElementPresent(By.xpath(".//h1")));
      if (driver.findElements(By.cssSelector("li#app- > ul.docs")).size() > 0) {
        //получаем список элементов по локатору для вложенных списков
        List<WebElement> items = driver.findElements(By.cssSelector("li#app- ul.docs"));
        //определяем количество элементов в списке
        int num = driver.findElements(By.xpath(".//ul[@class='docs']/li")).size();
        int i = 0;
        while (i < num) {
          //получаем список после рефреша заново для каждой итерации
          List<WebElement> newItems = driver.findElements(By.xpath(".//ul[@class='docs']/li"));
          //
//          String[] split = newItems.get(i).getText().split("\n");
//          String multiline = split[0];
          newItems.get(i).click();
          driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
          assertTrue(selenium().isElementPresent(By.xpath(".//h1")));
          i++;
        }
      }
      j++;
    }
  }
}
