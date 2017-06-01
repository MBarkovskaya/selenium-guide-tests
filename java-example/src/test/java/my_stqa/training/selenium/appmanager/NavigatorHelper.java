package my_stqa.training.selenium.appmanager;


import org.openqa.selenium.By;

public class NavigatorHelper {
  private final SeleniumHelper selenium;

  public NavigatorHelper(SeleniumHelper selenium) {
    this.selenium = selenium;
  }

  public void loginAdmin(String username, String password) {
    selenium.type(By.name("username"), username);
    selenium.type(By.name("password"), password);
    selenium.click(By.name("login"));
  }

  public void AppearancePage() {
    selenium.click(By.xpath(".//li[@id='app-']/a[span='Appearence']"));
  }

  public void HomePage() {
    selenium.navigateTo("http://localhost/litecart/admin/");
  }

  public void TemplatePage() {
    selenium.click(By.xpath(".//li[@id='doc-template']/a[span='Template']"), 5);
  }

  public void LogotypePage() {
    selenium.click(By.xpath(".//li[@id='doc-logotype']/a[span='Logotype']"));
  }

  public void CatalogPage() {
    selenium.click(By.xpath(".//li[@id='app-']/a[span='Catalog']"), 5);
  }

  public void CatalogItemPage() {
    selenium.click(By.xpath(".//li[@id='doc-catalog']/a[span='Catalog']"), 5);
  }

  public void ProductGroupsPage() {
    selenium.click(By.xpath(".//li[@id='doc-product_groups']/a[span='Product Groups']"), 5);
  }

  public void CSVPage() {
    selenium.click(By.xpath(".//li[@id='doc-csv']/a[span='CSV Import/Export']"), 5);
  }

  public void CountriesPage() {
    selenium.click(By.xpath(".//li[@id='app-']/a[span='Countries']"), 5);
  }

  public void CustomersPage() {
    selenium.click(By.xpath(".//li[@id='app-']/a[span='Customers']"), 5);
  }

  public void CSVCustomersPage() {
    selenium.click(By.xpath(".//ul[@class='docs']//a[span='CSV Import/Export']"), 5);
  }

  public void CustomersItemPage() {
    selenium.click(By.xpath(".//li[@id='doc-customers']/a[span='Customers']"), 5);
  }

  public void NewsletterPage() {
    selenium.click(By.xpath(".//li[@id='doc-newsletter']/a[span='Newsletter']"), 5);
  }
}
