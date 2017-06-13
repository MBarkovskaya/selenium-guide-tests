package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CartAddRemoveItemTest extends TestBase {

  @Test
  public void CartAddRemoveItem() {
    init();
    driver.get(getProperty("web.baseUrl") + "en/");

    Set<String> itemNames = new HashSet<>();
    WebDriverWait wait = new WebDriverWait(driver, 10);

    itemNames.add(addItemToCard(wait, "1"));
    itemNames.add(addItemToCard(wait, "2"));
    itemNames.add(addItemToCard(wait, "3"));
    driver.findElement(By.cssSelector("div#cart a.link")).click();
    int expectedItemsCount = itemNames.size();
    while (expectedItemsCount > 0) {
      selenium().click(By.cssSelector("ul.items button[name=remove_cart_item]"), 10, 1000);
      wait.until(numberOfElementsToBe(By.cssSelector("tr td.item"), --expectedItemsCount));
    }
  }


  public String addItemToCard(WebDriverWait wait, String expectedText) {
    wait.until(presenceOfElementLocated(By.cssSelector("div#box-most-popular a.link")));
    String text = driver.findElement(By.cssSelector("div#box-most-popular a.link")).getAttribute("title");
    driver.findElement(By.cssSelector("div#box-most-popular a.link")).click();
    if (selenium().isElementPresent(By.cssSelector("div.buy_now td.options"), 1)) {
      WebElement el = driver.findElement(By.cssSelector("select[name='options[Size]']"));
      Select select = new Select(el);
      select.selectByVisibleText("Small");
    }
    selenium().click(By.cssSelector("div.buy_now button[name=add_cart_product]"), 10, 1000);
    wait.until(textToBePresentInElementLocated((By.cssSelector("div#cart span.quantity")), expectedText));
    driver.findElement(By.cssSelector("i.fa.fa-home")).click();
    return text;
  }

}
