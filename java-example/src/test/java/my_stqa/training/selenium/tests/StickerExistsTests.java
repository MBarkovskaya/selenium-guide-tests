package my_stqa.training.selenium.tests;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class StickerExistsTests extends TestBase {

  @Test
  public void stickerTest() {
    app.navigationTo().Home();
    List<WebElement> elementList = driver.findElements(By.cssSelector("div.middle li.product.column.shadow.hover-light"));
    for (WebElement element : elementList) {
      String sticker = element.getText().split("\n")[0];
      MatcherAssert.assertThat("The only one sticker have to exist", sticker.equals("NEW") ^ sticker.equals("SALE"));
    }
  }

}

