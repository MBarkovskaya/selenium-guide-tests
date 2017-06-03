package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class HeaderEachItemOfMenuExistsTests extends TestBase {

  @Test
  public void HeaderItemExists() throws InterruptedException {
    int j = 0;
    int numBut = driver.findElements(By.cssSelector("li#app-")).size();
    while (j < numBut) {
      app.navigationTo().HomePage();
      List<WebElement> elements = driver.findElements(By.cssSelector("li#app-"));
      elements.get(j).click();
      assertTrue(app.selenium().isElementPresent(By.xpath(".//h1")));
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
          assertTrue(app.selenium().isElementPresent(By.xpath(".//h1")));
          i++;
        }
      }
      j++;
    }
  }
}
