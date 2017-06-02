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
      driver = app.driverChoice();
      //эта строчка кода включает настройку НЕЯВНОЕ ОЖИДАНИЕ
//      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      tlDriver.set(driver);
    }
    wait = new WebDriverWait(driver, 15);
    app.init();
  }

  @After
  public void tearDown() {
    app.stop();
  }




}
