package my_stqa.training.selenium.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.ArrayList;
import java.util.List;

public class SortGeoZonesTest extends TestBase {

  @Test
  public void sortGeoZones() {
    init();
    goTo().HomePage();
    goTo().loginAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));

    goTo().GeoZonesPage();
    List<WebElement> listCountries = driver.findElements(By.cssSelector("form[name=geo_zones_form] tr.row"));
    for (int i = 0; i < listCountries.size(); i++) {
      goTo().GeoZonesPage();
      List<WebElement> list = driver.findElements(By.cssSelector("form[name=geo_zones_form] tr.row"));
      int id = Integer.parseInt(list.get(i).findElement(By.xpath(".//td[2]")).getText());
      goTo().GeoZonesItemPage(id);
      List<WebElement> rows = driver.findElements(By.cssSelector("table.dataTable tr:not(.header)"));
      List<String> zones = new ArrayList<>();
      for (int j = 0; j < rows.size() - 1; j++) {
        String cellzone = rows.get(j).findElement(By.xpath(".//td[3]//option[@selected='selected']")).getText();
        if (!cellzone.equals("")) {
          zones.add(cellzone);
        }
      }
      assertAlphabetOrder(zones);
    }
  }


}
