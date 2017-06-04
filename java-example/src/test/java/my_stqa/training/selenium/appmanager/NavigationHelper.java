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
}
