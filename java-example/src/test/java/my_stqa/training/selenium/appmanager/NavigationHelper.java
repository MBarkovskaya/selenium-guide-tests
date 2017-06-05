package my_stqa.training.selenium.appmanager;


import org.openqa.selenium.By;

public class NavigationHelper {
  private final SeleniumHelper selenium;

  public NavigationHelper(SeleniumHelper selenium) {
    this.selenium = selenium;
  }

  public void loginAdmin(String username, String password) {
    selenium.type(By.name("username"), username);
    selenium.type(By.name("password"), password);
    selenium.click(By.name("login"));
  }

  public void HomePage() {
    selenium.navigateTo("http://localhost/litecart/admin/");
  }

  public void Home() {
    selenium.navigateTo(  "http://localhost/litecart/en/");
  }

  public void CountriesPage() {
    if (selenium.isElementPresent(By.tagName("h1")) && selenium.driver.findElement(By.tagName("h1")).getText().equals("Countries")) {
      return;
    }
    selenium.click(By.xpath(".//li[@id='app-']/a[span='Countries']"), 5);
  }

  public void CountriesItemPage(String countryCode) {
    selenium.navigateTo(String.format("http://localhost/litecart/admin/?app=countries&doc=edit_country&country_code=%s", countryCode));
  }
}
