package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;

public class CartAddRemoveItemTest extends TestBase {

  @Test
  public void CartAddRemoveItem () {
    init();
    driver.get(getProperty("web.baseUrl") + "en/");
    driver.findElement(By.cssSelector("div#box-most-popular a.link")).click();
    driver.findElement(By.cssSelector("div.buy_now button[name=add_cart_product]")).click();
    driver.findElement(By.cssSelector("i.fa.fa-home")).click();
    driver.findElement(By.cssSelector("div#box-most-popular a.link")).click();
    driver.findElement(By.cssSelector("div.buy_now button[name=add_cart_product]")).click();
    driver.findElement(By.cssSelector("i.fa.fa-home")).click();
    driver.findElement(By.cssSelector("div#box-most-popular a.link")).click();
    driver.findElement(By.cssSelector("div.buy_now button[name=add_cart_product]")).click();
    driver.findElement(By.cssSelector("i.fa.fa-home")).click();

    driver.findElement(By.cssSelector("div#cart a.link")).click();
    
  }
}
