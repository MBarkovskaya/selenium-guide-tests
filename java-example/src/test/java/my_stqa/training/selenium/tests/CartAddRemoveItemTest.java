package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

public class CartAddRemoveItemTest extends TestBase {

  @Test
  public void CartAddRemoveItem () {
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
// метод click()  в классе SeleniumHelper
      selenium().click(By.cssSelector("ul.items button[name=remove_cart_item]"), 10, 1000);
      wait.until(numberOfElementsToBe(By.cssSelector("tr td.item"), expectedItemsCount --));
    }
  }
  

  public String addItemToCard(WebDriverWait wait, String expectedText) {
    String text = driver.findElement(By.cssSelector("div#box-most-popular a.link")).getAttribute("title");
    driver.findElement(By.cssSelector("div#box-most-popular a.link")).click();
    driver.findElement(By.cssSelector("div.buy_now button[name=add_cart_product]")).click();
    wait.until(textToBePresentInElementLocated((By.cssSelector("div#cart span.quantity")), expectedText));
    driver.findElement(By.cssSelector("i.fa.fa-home")).click();
    return text;
  }

}
