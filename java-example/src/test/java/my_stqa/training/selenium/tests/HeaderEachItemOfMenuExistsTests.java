package my_stqa.training.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

public class HeaderEachItemOfMenuExistsTests extends TestBase {

  @Test
  public void headerItemTest() throws InterruptedException {
    app.selenium().HomePage();
    app.selenium().AppearancePage();
    app.selenium().TemplatePage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Template')]")));
    app.selenium().LogotypePage();
    assertTrue(isElementPresent(By.xpath(".//h1[contains(.,'Logotype')]")));
  }


}