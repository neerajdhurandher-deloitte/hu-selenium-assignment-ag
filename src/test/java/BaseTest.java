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

public class BaseTest {
    TemperaturePage temperaturePage;
    SunsCreamPage sunsCreamPage;
    MoisturizersPage moisturizersPage;
    CartPage cartPage;
    PaymentStatusPage paymentStatusPage;

    public ExtentSparkReporter extentSparkReporter;
    public static ExtentReports extentReports = new ExtentReports();
    Logger log = Logger.getLogger(BaseTest.class);

    WebDriver driver;

    @BeforeTest
    public void init() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chrome-driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://weathershopper.pythonanywhere.com");

        temperaturePage = new TemperaturePage(driver,log);
        sunsCreamPage = new SunsCreamPage(driver,log);
        moisturizersPage = new MoisturizersPage(driver,log);
        cartPage = new CartPage(driver,log);
        paymentStatusPage = new PaymentStatusPage(driver,log);

        // extent report configuration setup
        extentReportConfiguration();

        // read card details excels file
        cartPage.readCardDetailExcelSheet();




        ExtentTest extentTest = extentReports.createTest("Base Test");
        extentTest.log(Status.PASS,"Test setup");
        log.info("Base test initialized");

    }

    private void extentReportConfiguration() {

        // extent report configuration setup


        extentSparkReporter = new ExtentSparkReporter("src/test/java//Reports/Test_Report.html");

        extentSparkReporter.config().setEncoding("utf-8");
        extentSparkReporter.config().setDocumentTitle("Selenium Assignment Report");
        extentSparkReporter.config().setReportName("Test Reports");
        extentSparkReporter.config().setTheme(Theme.DARK);


        extentReports.setSystemInfo("Organization", "Hashedin By Deloitte");
        extentReports.setSystemInfo("Created by ", "Neeraj Dhurandher");
        extentReports.attachReporter(extentSparkReporter);
    }


    @Test
    public void test1(){

        ExtentTest extentTest = extentReports.createTest("Test 1");

        ExtentTest e = extentTest.createNode("Title","des");
        e.log(Status.PASS,"1");
        e.log(Status.PASS,"2");
        extentTest.addScreenCaptureFromPath("ScreenShots/ShoppingRecipient.png","Ss title path");

        int  currentTemp = temperaturePage.getTemperature();
        log.info("Current temperature is :- " + currentTemp);

        if(currentTemp < 19){

            goForMoisturizers();

        }else if(currentTemp > 34){

            goForSunscreens();

        }else{

            driver.navigate().refresh();
        }



    }

    private void goForMoisturizers() {

        ExtentTest extentTest = extentReports.createTest("Select Moisturizer");

        temperaturePage.clickMoisturizersBtn();


        log.info("clickMoisturizerBtn");

        moisturizersPage.clickIButton();

        moisturizersPage.selectLeastItemInCategory("Aloe",extentTest);
        moisturizersPage.selectLeastItemInCategory("Almond",extentTest);

        cartPage.clickCartButton();

        ExtentTest verifyCart = extentTest.createNode("verifying cart items");

        if(cartPage.verifyCartItem(2,verifyCart)){

            payToProcess();
        }
    }

    private void goForSunscreens() {

        ExtentTest extentTest = extentReports.createTest("Select SunsCream");

        temperaturePage.clickSunscreensBtn();


        log.info("clickSunscreensBtn");

        sunsCreamPage.clickIButton();

        sunsCreamPage.selectLeastItemInCategory("SPF-50",extentTest);
        sunsCreamPage.selectLeastItemInCategory("SPF-30",extentTest);

        cartPage.clickCartButton();

        ExtentTest verifyCart = extentTest.createNode("verifying cart items");

        if(cartPage.verifyCartItem(2,verifyCart)){

            payToProcess();
        }




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
