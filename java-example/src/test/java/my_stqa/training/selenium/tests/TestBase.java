package my_stqa.training.selenium.tests;

import my_stqa.training.selenium.appmanager.NavigationHelper;
import my_stqa.training.selenium.appmanager.SeleniumHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import static org.openqa.selenium.remote.BrowserType.*;

public class TestBase {
  public WebDriver driver;

  protected final Properties properties;
  private SeleniumHelper seleniumHelper;
  private NavigationHelper navigationHelper;

  public TestBase() {
    properties = new Properties();
  }

  @AfterClass
  public static void stopAllBrowsers() {
    WebDriverPool.DEFAULT.dismissAll();
  }

  public void init() {
    String browser = System.getProperty("browser", BrowserType.IE);
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
          driver = WebDriverPool.DEFAULT.getDriver(firefoxCaps);
          break;
        case CHROME:

          ChromeOptions options = new ChromeOptions();
          options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
          options.addArguments("start-maximized");
          DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
          chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
          driver = WebDriverPool.DEFAULT.getDriver(chromeCapabilities);
          break;
        case IE:
          InternetExplorerOptions ieOptions = new InternetExplorerOptions();
          DesiredCapabilities capabilities = new DesiredCapabilities(ieOptions.merge(DesiredCapabilities.internetExplorer()));
          capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
          driver = WebDriverPool.DEFAULT.getDriver(capabilities);
          break;
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win8")));
      try {
        driver = WebDriverPool.DEFAULT.getDriver(new URL(properties.getProperty("selenium.server")), capabilities);
      } catch (MalformedURLException e) {
        Assert.fail("Unable to read property for selenium.server: " + e);
      }
    }

    seleniumHelper = new SeleniumHelper(driver);
    navigationHelper = new NavigationHelper(seleniumHelper);

  }

  public SeleniumHelper selenium() {
    return seleniumHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }


  public void assertAlphabetOrder(List<String> listNames) {
    for (int i = 0; i < listNames.size() - 1; i++) {
      String previous = listNames.get(i);
      String next = listNames.get(i + 1);
      Assert.assertTrue(previous.compareTo(next) < 0);
    }
  }


}
