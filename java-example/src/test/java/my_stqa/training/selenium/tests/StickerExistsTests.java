package my_stqa.training.selenium.tests;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.List;

public class StickerExistsTests extends TestBase {

  @Test
  public void stickerTest() {
    init();
    driver.get(getProperty("web.baseUrl") + "en/");
    List<WebElement> elementList = driver.findElements(By.cssSelector("div.middle li.product.column.shadow.hover-light"));
    for (WebElement element : elementList) {
      int num = element.findElements(By.xpath(".//div[@class='image-wrapper']/div[starts-with(@class,'sticker')]")).size();
      Assert.assertTrue(num == 1);
    }
    driver.manage().logs().get("browser").getAll().forEach(System.out::println);
  }

}


