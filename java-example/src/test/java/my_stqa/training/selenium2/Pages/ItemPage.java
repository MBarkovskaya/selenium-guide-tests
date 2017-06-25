package my_stqa.training.selenium2.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

public class ItemPage extends Page {
  public ItemPage(WebDriver driver) {
    super(driver);
  }

//  @FindBy(css = "button[name=add_cart_product]")
  @FindBy(name = "add_cart_product")
  public WebElement addButton;

  @FindBy(css = "i.fa.fa-home")
  public WebElement home;

  public boolean itemSizeable() {
    return selenium.isElementPresent(By.cssSelector("div.buy_now td.options"), 1);
  }

  public void chooseSize() {
    WebElement el = driver.findElement(By.cssSelector("select[name='options[Size]']"));
    Select select = new Select(el);
    select.selectByVisibleText("Small");
  }

  public void expectAmount(int expectedAmount) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(textToBePresentInElementLocated((By.cssSelector("div#cart span.quantity")), String.valueOf(expectedAmount)));
  }
}
