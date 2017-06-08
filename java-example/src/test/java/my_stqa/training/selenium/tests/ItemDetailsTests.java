package my_stqa.training.selenium.tests;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;

public class ItemDetailsTests extends TestBase {
  //создаем переменную типа Pattern, компилируем рег. выражение
  private Pattern rgba = Pattern.compile("rgba\\((\\d{1,3}), (\\d{1,3}), (\\d{1,3}),.*");

  @Test
  public void itemName() {
    init();
    goTo().Home();
    String mainPageData = driver.findElement(By.cssSelector("div#box-campaigns div.name")).getText();
    goTo().DetailsPage();
    WebElement element = selenium().findElement(By.cssSelector("div.content h1.title"), 15);
    Assert.assertNotNull("Can't find div.content h1.title", element);
    MatcherAssert.assertThat(mainPageData, equalTo(driver.findElement(By.cssSelector("div.content h1.title")).getText()));
  }

  @Test
  public void itemPrices() {
    init();
    goTo().Home();
    String mainPageRegular = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getText();
    String mainPageCampaigh = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getText();
    goTo().DetailsPage();
    String detailsPageRegular = driver.findElement(By.cssSelector("div.content s.regular-price")).getText();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    try {
      driver.findElement(By.cssSelector("div.content strong.campaign-price")).getText();
    } catch (TimeoutException ignore) {
    }
    MatcherAssert.assertThat(mainPageRegular, equalTo(detailsPageRegular));
    MatcherAssert.assertThat(mainPageCampaigh, equalTo(driver.findElement(By.cssSelector("div.content strong.campaign-price")).getText()));
  }

  @Test
  public void itemGreyPrices() {
    init();
    //проверки на главной странице
    goTo().Home();
    String mpRegularColor = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("color");
    RGB rgb = extractRGB(mpRegularColor);
    Assert.assertTrue(rgb.R == rgb.G && rgb.G == rgb.B);

    String mpRegularStyle =
            driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("text-decoration").substring(0, 12);
    MatcherAssert.assertThat(mpRegularStyle, equalTo("line-through"));
    //проверки на странице детализации
    goTo().DetailsPage();
    String dpRegularColor = driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("color");
    RGB rgb1 = extractRGB(dpRegularColor);
    Assert.assertTrue(rgb1.R == rgb1.G && rgb1.G == rgb1.B);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    try {
      driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("text-decoration").substring(0, 12);
    } catch (TimeoutException ignore) {
    }
    MatcherAssert.assertThat(driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("text-decoration").substring(0, 12),
            equalTo("line-through"));
  }

  @Test
  public void itemRedPrices() {
    init();
    //проверки на главной странице
    goTo().Home();
    String mpCampaignColor = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("color");
    RGB rgb = extractRGB(mpCampaignColor);
    Assert.assertTrue(rgb.G == rgb.B && rgb.G == 0);

    selenium().verifyFontWeight(driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("font-weight"));
    //проверки на странице детализации
    goTo().DetailsPage();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    String dpCampaignColor = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("color");
    RGB rgb1 = extractRGB(dpCampaignColor);
    Assert.assertTrue(rgb1.G == rgb1.B && rgb1.G == 0);
    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    selenium().verifyFontWeight(driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("font-weight"));
  }

  @Test
  public void itemSize() {
    init();
    goTo().Home();
    int mpRegularH = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getSize().getHeight();
    int mpRegularW = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getSize().getWidth();

    int mpCampaighH = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getSize().getHeight();
    int mpCampaighW = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getSize().getWidth();
    Assert.assertTrue(mpRegularH < mpCampaighH && mpRegularW < mpCampaighW);

    goTo().DetailsPage();
    int dpRegularH = driver.findElement(By.cssSelector("div.content s.regular-price")).getSize().getHeight();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    try{
    int dpRegularW = driver.findElement(By.cssSelector("div.content s.regular-price")).getSize().getWidth();
  } catch (TimeoutException ignore) {
  }
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    int dpCampaighH = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getSize().getHeight();
    int dpCampaighW = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getSize().getWidth();
    Assert.assertTrue(dpRegularH < dpCampaighH
            && driver.findElement(By.cssSelector("div.content s.regular-price")).getSize().getWidth() < dpCampaighW);
  }


  //метод достает значение RGB из строки заданного шаблона (скомпилированная строка), используя рег. выражение
  private RGB extractRGB(String rgbStr) {
    //matcher ищет соответствие между скомпилированным шаблоном и заданной строкой
    Matcher matcher = rgba.matcher(rgbStr);
    RGB result = null;
    // проверяем соответствует ли строка шаблону рег. выражения
    if (matcher.matches()) {
      result = new RGB();
      //в группах хранится результат распарсивания строки
      result.R = Integer.valueOf(matcher.group(1));
      result.G = Integer.valueOf(matcher.group(2));
      result.B = Integer.valueOf(matcher.group(3));
    }

    return result;
  }

  //создаем внутренний класс для хранения значений RGB
  private class RGB {
    int R;
    int G;
    int B;
  }
}

