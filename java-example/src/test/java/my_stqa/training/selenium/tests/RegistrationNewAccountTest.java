package my_stqa.training.selenium.tests;

import my_stqa.training.selenium.model.Customer;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class RegistrationNewAccountTest extends TestBase {


  @Test
  public void RegistrationNewAccount() {
    init();
    driver.get(getProperty("web.baseUrl") + "admin/");
    goTo().loginAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    captureOff();
    driver.get(getProperty("web.baseUrl") + "en/");
    long now = System.currentTimeMillis();
    //добавляем уникальное значение now к имени пользователя
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", now);
    selenium().click(By.cssSelector("div.content a[href='http://localhost/litecart/en/create_account']"), 10);
    Customer commondata = data().generateRandomCustomer();
    loginNewAccount(commondata, email, password);
    logout();
    loginCreatedAccount(email, password);
    logout();
  }

  public void loginCreatedAccount(String email, String password) {
    driver.findElement(By.name("email")).sendKeys(email + Keys.TAB);
    driver.findElement(By.name("password")).sendKeys(password + Keys.TAB);

    selenium().click(By.cssSelector("span.button-set button[name=login]"));
  }

  private void captureOff() {
    selenium().click(By.xpath(".//li[@id='app-']/a[span='Settings']"), 5);
    selenium().click(By.xpath(".//ul[@class='docs']//a[span='Security']"), 5);
    selenium().click(By.xpath(".//form[@name='settings_form']//tr[7]//a[@title='Edit']"), 5);
    selenium().click(By.xpath(".//td[2]//label[contains(.,'False')]"), 5);
    selenium().click(By.xpath(".//tr[7]//button[contains(.,'Save')]"), 5);
  }

  private void loginNewAccount(Customer commondata, String email, String password) {
    driver.findElement(By.name("tax_id")).sendKeys(commondata.getTaxId() + Keys.TAB + Keys.TAB);
    driver.findElement(By.name("firstname")).sendKeys(commondata.getFirstname() + Keys.TAB);
    driver.findElement(By.name("lastname")).sendKeys(commondata.getLastname() + Keys.TAB);
    driver.findElement(By.name("address1")).sendKeys(commondata.getAddress1() + Keys.TAB + Keys.TAB);
    driver.findElement(By.name("postcode")).sendKeys(commondata.getPostcode() + Keys.TAB);
    driver.findElement(By.name("city")).sendKeys(commondata.getCity() + Keys.TAB);
    WebElement country = driver.findElement(By.xpath(".//select[@name='country_code']"));
    Select select = new Select(country);
    select.selectByVisibleText("United States");
    WebElement state = selenium().findElement(By.xpath(".//select[@name='zone_code']"), 5);
    Select selectState = new Select(state);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    selectState.selectByVisibleText("Alaska");
    driver.findElement(By.name("email")).sendKeys(email + Keys.TAB);
    driver.findElement(By.name("phone")).sendKeys(Keys.HOME + commondata.getPhone() + Keys.TAB);
    driver.findElement(By.name("password")).sendKeys(password + Keys.TAB);
    driver.findElement(By.name("confirmed_password")).sendKeys(password + Keys.TAB);

    selenium().click(By.cssSelector("button[name='create_account']"));
  }

  private void logout() {
    selenium().click(By.cssSelector("aside#navigation a[href='http://localhost/litecart/en/logout']"));
  }
}
