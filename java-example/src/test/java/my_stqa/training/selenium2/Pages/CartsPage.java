package my_stqa.training.selenium2.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;

public class CartsPage extends Page {
  public CartsPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(css = "ul.items button[name=remove_cart_item]")
  public WebElement removeItem;

  public void expectAmount(int expectedAmout) {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(numberOfElementsToBe(By.cssSelector("tr td.item"), expectedAmout));
  }
}
