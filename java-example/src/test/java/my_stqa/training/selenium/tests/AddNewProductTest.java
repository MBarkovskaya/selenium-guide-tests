package my_stqa.training.selenium.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class AddNewProductTest extends TestBase {

  @Test
  public void addNewProduct() {
    init();
    driver.get(getProperty("web.baseUrl") + "admin/");
    goTo().loginAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    goTo().CatalogPage();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(urlContains("http://localhost/litecart/admin/?app=catalog&doc=catalog"));
    selenium().click(By.linkText("Add New Product"));
    wait.until(urlContains("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product"));
    long now = System.currentTimeMillis();
    String name = String.format("Funny Duck%s", now);
    fillGeneralForm(name);
    editForm(name);
    fillInformationForm();
    editForm(name);
    fillPricesForm();
    //возвращаемся на траницу Home, чтобы убедиться, что в каталоге появился новый товар
    goTo().HomePage();
    goTo().CatalogPage();
    String newName = driver.findElement(By.xpath(String.format(".//table[@class='dataTable']//td[a='%s']", name))).getAttribute("textContent");
    Assert.assertTrue(newName.trim().equals(name));
  }


  public void fillPricesForm() {
    selenium().click(By.linkText("Prices"), 10);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.findElement(By.xpath(".//div[@id='tab-prices']//table//input[@type='number']")).clear();
    driver.findElement(By.xpath(".//div[@id='tab-prices']//table//input[@type='number']")).sendKeys("22");
    WebElement elprice = driver.findElement(By.cssSelector("select[name='purchase_price_currency_code']"));
    Select price = new Select(elprice);
    price.selectByVisibleText("US Dollars");
    new Actions(driver).click(driver.findElement(By.cssSelector("select[name='tax_class_id']"))).click();
    driver.findElement(By.xpath(".//div[@id='tab-prices']//tr[2]//input[@type='text']")).clear();
    driver.findElement(By.xpath(".//div[@id='tab-prices']//tr[2]//input[@type='text']")).sendKeys("22");
    driver.findElement(By.xpath(".//div[@id='tab-prices']//tr[3]//input[@type='text']")).clear();
    driver.findElement(By.xpath(".//div[@id='tab-prices']//tr[3]//input[@type='text']")).sendKeys("22");
    selenium().click(By.cssSelector("button[name=save]"), 5);
  }

  public void editForm(String name) {
    String newName = selenium().findElement(By.xpath(String.format(".//table[@class='dataTable']//td[a='%s']", name)), 10).getAttribute("textContent");
    if (newName.trim().equals(name)) {
      selenium().click(By.xpath(".//a[contains(.,'Funny Duck')]"), 10);
    }
  }

  public void fillInformationForm() {
    selenium().click(By.cssSelector("ul.index a[href='#tab-information']"));
    WebElement element = driver.findElement(By.cssSelector("select[name=manufacturer_id]"));
    Select manufacturer = new Select(element);
    manufacturer.selectByVisibleText("ACME Corp.");
    new Actions(driver).click(driver.findElement(By.cssSelector("select[name=supplier_id]"))).click();
    driver.findElement(By.cssSelector("input[name=keywords")).sendKeys("Duck");
    driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("Duck");
    driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Duck");
    driver.findElement(By.cssSelector("input[name='head_title[en]']")).sendKeys("Funny duck");
    driver.findElement(By.cssSelector("input[name='meta_description[en]']")).sendKeys("FD");
    selenium().click(By.cssSelector("button[name=save]"), 5);
  }

  public void fillGeneralForm(String name) {
    selenium().click(By.cssSelector("div#tab-general label"));
    driver.findElement(By.xpath(".//div[@id='tab-general']//tr[2]//span[@class='input-wrapper']/input[@name='name[en]']"))
            .sendKeys(name + Keys.TAB);
    driver.findElement(By.xpath(".//div[@id='tab-general']//tr[3]//input[@name='code']")).sendKeys(Keys.HOME + "12345" + Keys.TAB);
    selenium().click(By.xpath(".//div[@id='tab-general']//tr[4]//input[@value='1']"));
    new Actions(driver).moveToElement(driver.findElement(By.cssSelector("select[name=default_category_id]"))).click().click().perform();
    selenium().click(By.cssSelector("div#tab-general input[value='1-3']"));
    selenium().click(By.cssSelector("div#tab-general input[name=quantity]"));
    driver.findElement(By.cssSelector("div#tab-general input[name=quantity]")).clear();
    driver.findElement(By.cssSelector("div#tab-general input[name=quantity]")).sendKeys("5" + Keys.TAB);
    new Actions(driver).moveToElement(driver.findElement(By.cssSelector("div#tab-general select[name=quantity_unit_id]"))).click().click()
            .perform();
    new Actions(driver).moveToElement(driver.findElement(By.cssSelector("div#tab-general select[name=delivery_status_id]"))).click().click()
            .perform();
    WebElement el = driver.findElement(By.cssSelector("div#tab-general select[name=sold_out_status_id]"));
    Select select = new Select(el);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    select.selectByVisibleText("Temporary sold out");
    String paint = new File("src/test/resources/images/duck.png").getAbsolutePath();
    driver.findElement(By.cssSelector("input[type=file]")).sendKeys(paint);
    selenium().click(By.cssSelector("button[name=save]"), 5);
  }

}




