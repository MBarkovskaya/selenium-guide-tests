package my_stqa.training.selenium.appmanager;

import my_stqa.training.selenium.model.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;

public class DataHelper {

  protected WebDriver driver;

  public DataHelper(WebDriver driver) {
    this.driver = driver;
  }

  public Customer generateRandomCustomer() {
    return new Customer().withTaxId(RandomStringUtils.randomNumeric(5)).withFirstname(RandomStringUtils.randomAlphabetic(5))
            .withLastname(RandomStringUtils.randomAlphabetic(5)).withAddress1(RandomStringUtils.randomAlphanumeric(4))
            .withPostcode(RandomStringUtils.randomNumeric(5)).withCity(RandomStringUtils.randomAlphabetic(5))
            .withPhone(RandomStringUtils.randomNumeric(9));
  }
}

