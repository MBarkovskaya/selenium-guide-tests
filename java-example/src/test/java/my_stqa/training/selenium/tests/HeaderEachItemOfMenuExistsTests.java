package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

public class HeaderEachItemOfMenuExistsTests extends TestBase {

  @Test
  public void appearanceItemTest() throws InterruptedException {
    app.navigationTo().HomePage();
    app.navigationTo().AppearancePage();
    app.navigationTo().TemplatePage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Template')]")));
    app.navigationTo().LogotypePage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Logotype')]")));
  }

  @Test
  public void catalogItemTest() throws InterruptedException {
    app.navigationTo().HomePage();
    app.navigationTo().CatalogPage();
    app.navigationTo().CatalogItemPage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Catalog')]")));
    app.navigationTo().ProductGroupsPage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Product Groups')]")));
    app.navigationTo().CSVPage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'CSV Import/Export')]")));
  }

  @Test
  public void countriesItemTest() throws InterruptedException {
    app.navigationTo().CountriesPage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Countries')]")));
  }

  @Test
  public void customersItemTest() throws InterruptedException {
    app.navigationTo().CustomersPage();
    app.navigationTo().CustomersItemPage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Customers')]")));
    app.navigationTo().CSVCustomersPage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'CSV Import/Export')]")));
    app.navigationTo().NewsletterPage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Newsletter')]")));
  }

  
}