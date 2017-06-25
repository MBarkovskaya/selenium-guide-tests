package my_stqa.training.selenium2.tests;

import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.Set;

public class CartAddRemoveItemTest extends TestBase {

  @Test
  public void CartAddRemoveItem() {
    app().mainPage().open();

    Set<String> itemNames = new HashSet<>();
    WebDriverWait wait = app().createWait(10);

    for (int i = 1; i <= 3; i++) {
      itemNames.add(addItemToCard(i));
    }

    app().mainPage().cart.click();
    int expectedItemsCount = itemNames.size();
    while (expectedItemsCount > 0) {
      app().cartsPage().removeItem.click();
      app().cartsPage().expectAmount(--expectedItemsCount);
    }
  }


  public String addItemToCard(int expectedAmount) {
    String text = app().mainPage().mostPopular.getAttribute("title");
    app().mainPage().mostPopular.click();

    if (app().itemPage().itemSizeable()) {
      app().itemPage().chooseSize();
    }
    app().itemPage().addButton.click();

    app().itemPage().expectAmount(expectedAmount);
    app().itemPage().home.click();
    return text;
  }

}
