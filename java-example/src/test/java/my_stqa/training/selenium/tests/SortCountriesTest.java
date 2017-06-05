package my_stqa.training.selenium.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SortCountriesTest extends TestBase {

  @Test
  public void sortCountries() {
    app.goTo().CountriesPage();
    List<String> cNames = new ArrayList<>();
    List<String> cCodes = new ArrayList<>();
    List<WebElement> listElements = driver.findElements(By.cssSelector("[name=countries_form] tr.row"));
    for (WebElement element : listElements) {
      String country = element.findElement(By.xpath("./td[5]")).getAttribute("textContent");
      String code = element.findElement(By.xpath("./td[4]")).getAttribute("textContent");
      cNames.add(country);
      cCodes.add(code);
    }
    assertAlphabetOrder(cNames);
    for (int j = 0; j < cCodes.size() - 1; j++) {
      app.goTo().CountriesItemPage(cCodes.get(j));
      List<WebElement> rows = driver.findElements(By.cssSelector("table.dataTable tr:not(.header)"));
      int numZone = rows.size()-1;
      if (numZone > 2) {
        List<String> czones = new ArrayList<>();
        for (WebElement row : rows) {
          WebElement cell = row.findElement(By.xpath("./td[3]"));
          String czone = cell.getAttribute("textContent");
          if(!czone.equals("")) {
            czones.add(czone);
          }
        }
        assertAlphabetOrder(czones);
      }
    }
  }

  public void assertAlphabetOrder(List<String> listNames) {
    for (int i = 0; i < listNames.size() - 1; i++) {
      String previous = listNames.get(i);
      String next = listNames.get(i + 1);
      Assert.assertTrue(previous.compareTo(next) < 0);
    }
  }
}
