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
    BasePage basePage;
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

        basePage = new BasePage();
        utilClass = new UtilClass(driver,log);
        temperaturePage = new TemperaturePage(driver,log);
        sunsCreamPage = new SunsCreamPage(driver,log);
        moisturizersPage = new MoisturizersPage(driver,log);
        cartPage = new CartPage(driver,log);
        paymentStatusPage = new PaymentStatusPage(driver,log);



        // read card details excels file
        cartPage.readCardDetailExcelSheet(extentTest);






    }



    @Test
    public void test1(){





    }



    private void payToProcess() {
        ExtentTest extentTest = extentReports.createTest("Payment Test");

        cartPage.clickPay();

        WebElement frame = driver.findElement(By.xpath("//iframe[@class = 'stripe_checkout_app']"));

        while (!frame.isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        driver.switchTo().frame(frame);

        cartPage.fillCardDetails(0);

        cartPage.payAmount();

        driver.switchTo().defaultContent();

        System.out.println("payment :- "+paymentStatusPage.paymentStatus());


    }

    @AfterTest
    public  void closeBrowser(){
        log.info("Web driver closed");
//        actionEvents.closeWindow();
        extentReports.flush();
    }


}
