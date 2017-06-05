package my_stqa.training.selenium.tests;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.equalTo;

public class ItemDetailsTests extends TestBase{

  @Test
  public void itemName() {
    app.goTo().Home();
    String mainPageData = driver.findElement(By.cssSelector("div#box-campaigns div.name")).getText();
    app.goTo().DetailsPage();
    String detailsPageData = driver.findElement(By.cssSelector("div.content h1.title")).getText();
    MatcherAssert.assertThat(mainPageData, equalTo(detailsPageData));
  }

  @Test
  public void itemPrices() {
    app.goTo().Home();
    String mainPageRegular = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getText();
    String mainPageCampaigh = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getText();
    app.goTo().DetailsPage();
    String detailsPageRegular = driver.findElement(By.cssSelector("div.content s.regular-price")).getText();
    String detailsPageCampaigh = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getText();
    MatcherAssert.assertThat(mainPageRegular, equalTo(detailsPageRegular));
    MatcherAssert.assertThat(mainPageCampaigh, equalTo(detailsPageCampaigh));
  }

  @Test
  public void itemGreyPrices() {
    //проверки на главной странице
    app.goTo().Home();
    String mpRegularColor = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("color");
    String firstnum = (mpRegularColor.substring(5,8));
    String secondtnum = (mpRegularColor.substring(10,13));
    String thirdtnum = (mpRegularColor.substring(15,18));
    Assert.assertTrue(firstnum.equals(secondtnum) && secondtnum.equals(thirdtnum));

    String mpRegularStyle = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("text-decoration");
    MatcherAssert.assertThat(mpRegularStyle, equalTo("line-through"));
    //проверки на странице детализации
    app.goTo().DetailsPage();
    String dpRegularColor = driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("color");
    String fnum = (mpRegularColor.substring(5,8));
    String snum = (mpRegularColor.substring(10,13));
    String thnum = (mpRegularColor.substring(15,18));
    Assert.assertTrue(fnum.equals(snum) && snum.equals(thnum));

    String dpRegularStyle = driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("text-decoration");
    MatcherAssert.assertThat(dpRegularStyle, equalTo("line-through"));
  }

  @Test
  public void itemRedPrices() {
    //проверки на главной странице
    app.goTo().Home();
    String mpCampaignColor = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("color");
    String firstnum = (mpCampaignColor.substring(5,8));
    String secondnum = (mpCampaignColor.substring(10,11));
    String thirdnum = (mpCampaignColor.substring(13,14));
    Assert.assertTrue(secondnum.equals(thirdnum) && secondnum.equals("0"));

    String mpRegularStyle = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("font-weight");
    MatcherAssert.assertThat(mpRegularStyle, equalTo("bold"));
    //проверки на странице детализации
    app.goTo().DetailsPage();
    String dpCampaignColor = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("color");
    String fnum = (dpCampaignColor.substring(5,8));
    String snum = (dpCampaignColor.substring(10,11));
    String thnum = (dpCampaignColor.substring(13,14));
    Assert.assertTrue(snum.equals(thnum) && snum.equals("0"));

    String dpRegularStyle = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("font-weight");
    MatcherAssert.assertThat(dpRegularStyle, equalTo("bold"));
  }
}

