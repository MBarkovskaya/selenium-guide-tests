package my_stqa.training.selenium.appmanager;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static org.openqa.selenium.remote.BrowserType.*;

public class ApplicationManager {
  private final Properties properties;
  private WebDriver driver;

  private SeleniumHelper seleniumHelper;
  private NavigationHelper navigationHelper;

  public ApplicationManager() {
    properties = new Properties();
  }

  public WebDriver driverChoice() throws IOException {
    String browser = System.getProperty("browser", BrowserType.FIREFOX);
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    if ("".equals(properties.getProperty("selenium.server"))) {
      if (browser.equals(FIREFOX)) {
//        FirefoxOptions options =
//                new FirefoxOptions().setBinary(new FirefoxBinary(new File("c:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe")))
//                        .setProfile(new FirefoxProfile()).setLegacy(false);
//        driver = new FirefoxDriver(options);
            FirefoxOptions options = new FirefoxOptions().setLegacy(true);
            driver = new FirefoxDriver(options);

        //    FirefoxOptions options = new FirefoxOptions().setBinary(new FirefoxBinary(new File("c:\\Program Files\\Nightly\\firefox.exe"))).setProfile(new FirefoxProfile());
        //    driver = new FirefoxDriver(options);

      } else if (browser.equals(CHROME)) {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
      } else if (browser.equals(IE)) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
        driver = new InternetExplorerDriver(caps);
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win8")));
      driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
    }
    return driver;
  }


  public void init() {
    driver.get(properties.getProperty("web.baseUrl"));
    seleniumHelper = new SeleniumHelper(driver);
    navigationHelper = new NavigationHelper(seleniumHelper);
    navigationHelper.loginAdmin(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
  }

  public SeleniumHelper selenium() {
    return seleniumHelper;
  }

  public NavigationHelper navigationTo() {
    return navigationHelper;
  }

  public void stop() {
    driver.quit();
    driver = null;
  }
}




