package my_stqa.training.selenium.tests;

import my_stqa.training.selenium.appmanager.ApplicationManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {
  public WebDriver driver;
  public WebDriverWait wait;

  public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

  protected final ApplicationManager app;

  public TestBase() {
    app = new ApplicationManager();
  }

  @Before
  public void setUp() throws IOException {

    if (tlDriver.get() != null) {
      driver = tlDriver.get();
    }  else {
      //передаем браузер, в котором будем запускать тесты
      driver = app.driverChoice(System.getProperty("browser", BrowserType.FIREFOX));
      //эта строчка кода включает настройку НЕЯВНОЕ ОЖИДАНИЕ
//      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      tlDriver.set(driver);
    }

    wait = new WebDriverWait(driver, 8);

    app.init();
  }

  @After
  public void tearDown() {
    app.stop();
  }


  public boolean isElementPresent(By locator) {
    //если элемент есть, то он найдется, если нет - будет выброшено исключение
    //так как Selenium не может знать есть элемент на странице или нет, поэтому нужно обернуть попытку поиска элементов в блок try
    try {
//      явное ожидание
            wait.until((WebDriver d) -> d.findElement(locator));
//            неявное ожидание
//      driver.findElement(locator);
      return true;
          } catch (TimeoutException ex) {
            return false;
//    } catch (NoSuchElementException ex) {
//      return false;
    }
  }

}
