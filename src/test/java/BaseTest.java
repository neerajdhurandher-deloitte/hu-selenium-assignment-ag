import Util.UtilClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.*;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;

public class BaseTest extends BasePage{

    UtilClass utilClass;
    TemperaturePage temperaturePage;
    SunsCreamPage sunsCreamPage;
    MoisturizersPage moisturizersPage;
    CartPage cartPage;
    PaymentStatusPage paymentStatusPage;



    WebDriver driver;

    @BeforeTest
    public void init() {

        webDriverSetUp();

        extentReportConfiguration();

        temperaturePage = new TemperaturePage();
        sunsCreamPage = new SunsCreamPage();
        moisturizersPage = new MoisturizersPage();
        cartPage = new CartPage();
        paymentStatusPage = new PaymentStatusPage();

    }



    @Test
    public void test1(){

        temperaturePage.getTemperature();

        temperaturePage.choiceForPurchase();

        cartPage.clickCartButton();

        cartPage.verifyCartItem();

        cartPage.payToProcess();

        cartPage.payAmount();

    }

    @AfterTest
    public  void closeBrowser(){
//        actionEvents.closeWindow();
        extentReports.flush();
    }


}
