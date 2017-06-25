package my_stqa.training.selenium2.Pages;

import my_stqa.training.selenium2.appmanager.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected SeleniumHelper selenium;

  public Page(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
    selenium = new SeleniumHelper(driver);
    PageFactory.initElements(driver, this);
  }
}
