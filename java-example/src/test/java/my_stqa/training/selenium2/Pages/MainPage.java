package my_stqa.training.selenium2.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {
  public MainPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    driver.get("http://localhost/litecart/en/");
  }

  @FindBy(css = "div#box-most-popular a.link")
  public WebElement mostPopular;

  @FindBy(css = "div#cart a.link")
  public WebElement cart;
}
