package my_stqa.training.selenium2.appmanager;

import my_stqa.training.selenium.tests.TestBase;
import my_stqa.training.selenium2.Pages.CartsPage;
import my_stqa.training.selenium2.Pages.ItemPage;
import my_stqa.training.selenium2.Pages.MainPage;
import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

import static java.util.logging.Level.ALL;
import static org.openqa.selenium.remote.BrowserType.*;

public class ApplicationManager {
  public EventFiringWebDriver driver;

  protected final Properties properties;
  private MainPage mainPage;
  private ItemPage itemPage;
  private CartsPage cartsPage;


  public ApplicationManager() {
    properties = new Properties();
    init();
  }

  public MainPage mainPage() {
    return mainPage;
  }

  public ItemPage itemPage() {
    return itemPage;
  }

  public CartsPage cartsPage() {
    return cartsPage;
  }

  public WebDriverWait createWait(int timeoutInSeconds) {
    return new WebDriverWait(driver, timeoutInSeconds);
  }

  public static class MyListener extends AbstractWebDriverEventListener {
    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
      System.out.println(element);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
      driver.manage().logs().get("browser").getAll().stream().filter(l -> l.getLevel() == ALL).forEach(System.out::println);
    }
  }

  public void init() {
    String browser = System.getProperty("browser", BrowserType.CHROME);
    String target = System.getProperty("target", "local");
    try {
      properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    } catch (IOException e) {
      Assert.fail("Unable to read properties: " + e);
    }

    if ("".equals(properties.getProperty("selenium.server"))) {
      switch (browser) {
        case FIREFOX:
          FirefoxOptions firefoxOptions = new FirefoxOptions();
          firefoxOptions.setLegacy(true);
          DesiredCapabilities firefoxCaps = DesiredCapabilities.firefox();
          firefoxCaps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
          driver = new EventFiringWebDriver(WebDriverPool.DEFAULT.getDriver(firefoxCaps));
          driver.register(new TestBase.MyListener());
          break;
        case CHROME:

          ChromeOptions options = new ChromeOptions();
          options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
          options.addArguments("start-maximized");
          DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
          chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
          LoggingPreferences logPrefs = new LoggingPreferences();
          logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
          chromeCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
          driver = new EventFiringWebDriver(WebDriverPool.DEFAULT.getDriver(chromeCapabilities));
          driver.register(new TestBase.MyListener());
          break;
        case IE:
          DesiredCapabilities capabilities = new DesiredCapabilities(DesiredCapabilities.internetExplorer());
          capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
          driver = new EventFiringWebDriver(WebDriverPool.DEFAULT.getDriver(capabilities));
          driver.register(new TestBase.MyListener());
          break;
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win8")));
      try {
        driver =
                new EventFiringWebDriver(WebDriverPool.DEFAULT.getDriver(new URL(properties.getProperty("selenium.server")), capabilities));
        driver.register(new TestBase.MyListener());
      } catch (MalformedURLException e) {
        Assert.fail("Unable to read property for selenium.server: " + e);
      }
    }
    mainPage = new MainPage(driver);
    itemPage = new ItemPage(driver);
    cartsPage = new CartsPage(driver);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

}


