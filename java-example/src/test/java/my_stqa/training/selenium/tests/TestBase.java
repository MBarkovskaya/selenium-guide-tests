package my_stqa.training.selenium.tests;

import my_stqa.training.selenium.appmanager.DataHelper;
import my_stqa.training.selenium.appmanager.NavigationHelper;
import my_stqa.training.selenium.appmanager.SeleniumHelper;
import org.junit.AfterClass;
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
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static java.util.logging.Level.ALL;
import static org.openqa.selenium.remote.BrowserType.*;

public class TestBase {
  public EventFiringWebDriver driver;

  protected final Properties properties;
  private SeleniumHelper seleniumHelper;
  private NavigationHelper navigationHelper;
  private DataHelper dataHelper;

  public TestBase() {
    properties = new Properties();
  }

  @AfterClass
  public static void stopAllBrowsers() {
    WebDriverPool.DEFAULT.dismissAll();
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
          driver.register(new MyListener());
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
          driver.register(new MyListener());
          break;
        case IE:
          DesiredCapabilities capabilities = new DesiredCapabilities(DesiredCapabilities.internetExplorer());
          capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
          driver = new EventFiringWebDriver(WebDriverPool.DEFAULT.getDriver(capabilities));
          driver.register(new MyListener());
          break;
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win8")));
      try {
        driver = new EventFiringWebDriver(WebDriverPool.DEFAULT.getDriver(new URL(properties.getProperty("selenium.server")), capabilities));
        driver.register(new MyListener());
      } catch (MalformedURLException e) {
        Assert.fail("Unable to read property for selenium.server: " + e);
      }
    }

    seleniumHelper = new SeleniumHelper(driver);
    navigationHelper = new NavigationHelper(seleniumHelper);
    dataHelper = new DataHelper(driver);

  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public SeleniumHelper selenium() {
    return seleniumHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public DataHelper data() {
    return dataHelper;
  }


  public void assertAlphabetOrder(List<String> listNames) {
    for (int i = 0; i < listNames.size() - 1; i++) {
      String previous = listNames.get(i);
      String next = listNames.get(i + 1);
      Assert.assertTrue(previous.compareTo(next) < 0);
    }
  }


}
