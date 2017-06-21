package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LogItemsCatalogTest extends TestBase {

  @Test
  public void logItemsCatalog() {
    init();
    driver.get(getProperty("web.baseUrl") + "/admin/");
    goTo().loginAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));

    goTo().CatalogPage();
    goTo().RubberDucksPage();
    int i = 0;
    while (i < driver.findElements(By.xpath(".//tr[@class='row']//img")).size()) {
      List<WebElement> items = driver.findElements(By.xpath(".//tr[@class='row']//img"));
      items.get(i).findElement(By.xpath("../a")).click();
      driver.manage().logs().get("browser").getAll().forEach(System.out::println);
      selenium().click(By.xpath("//button[@name='cancel']"), 5);
      i++;
    }
  }
}



