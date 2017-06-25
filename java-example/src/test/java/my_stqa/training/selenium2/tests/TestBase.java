package my_stqa.training.selenium2.tests;

import my_stqa.training.selenium2.appmanager.ApplicationManager;
import org.junit.AfterClass;
import ru.stqa.selenium.factory.WebDriverPool;

public class TestBase {

  private ApplicationManager applicationManager;

  public TestBase() {
    this.applicationManager = new ApplicationManager();
  }

  public ApplicationManager app() {
    return applicationManager;
  }

  @AfterClass
  public static void stopAllBrowsers() {
    WebDriverPool.DEFAULT.dismissAll();
  }




}
