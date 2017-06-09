package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
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
    String Name = "Funny Duck";
    fillGeneralForm(Name);
    String newName = driver.findElement(By.xpath(".//table[@class='dataTable']//td[a='Funny Duck']")).getAttribute("textContent");
    if(newName.equals(Name)) {
      new Actions(driver).click(driver.findElement(By.xpath(".//table[@class='dataTable']//td[a='Funny Duck']")));
    }
    fillInformationForm();


    selenium().click(By.linkText("Prices"), 10);
    driver.findElement(By.cssSelector("input[name=purchace_price")).clear();
    driver.findElement(By.cssSelector("input[name=purchace_price")).sendKeys("22");
    WebElement elprice = driver.findElement(By.cssSelector("selector[name='purchase_price_currency_code']"));
    Select price = new Select(elprice);
    price.selectByVisibleText("US Dollars");
    new Actions(driver).click(driver.findElement(By.cssSelector("selector[name='tax_class_id']"))).click();
    driver.findElement(By.cssSelector("input[name=prices[USD]")).sendKeys("22");
    driver.findElement(By.cssSelector("input[name=prices[EUR]")).sendKeys("22");
    selenium().click(By.cssSelector("button[name=save]"), 5);
  }

  public void fillInformationForm() {
    selenium().click(By.cssSelector("ul.index a[href='#tab-information']"));
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(attributeContains(driver.findElement(By.cssSelector("li.active a[href='#tab-information']")),"li", "active"));
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

  public void fillGeneralForm(String Name) {
    selenium().click(By.cssSelector("div#tab-general label"));
    driver.findElement(By.xpath(".//div[@id='tab-general']//tr[2]//span[@class='input-wrapper']/input[@name='name[en]']"))
            .sendKeys(Name + Keys.TAB);
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
    //    selenium().click(By.cssSelector("input[name=new_images[]]"));
    //    File photo = new File(".png”);
    //    photo.getAbsolutePath());
    selenium().click(By.cssSelector("button[name=save]"), 5);
  }
}



