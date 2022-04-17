package pages;

import Util.UtilClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasePage extends UtilClass {

    public static WebDriver driver;
    public static  ExtentSparkReporter extentSparkReporter;
    public static ExtentReports extentReports = new ExtentReports();
    public static Logger log = Logger.getLogger(BasePage.class);



    String chromeDriverPath = "src/main/resources/chrome-driver/chromedriver.exe";
    String webUrl = "https://weathershopper.pythonanywhere.com";

    protected void webDriverSetUp(){
        // set web driver
        System.setProperty("webdriver.chrome.driver",chromeDriverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        log.info("Web browser driver setup");

        // url hit
        driver.get(webUrl);
        log.info("Website Url hit");

        setDriverAndLog(driver,log);

        ExtentTest readExcelET = extentReports.createTest("Read card details Excel file test");
        // read card details excl file
        readCardDetailExcelSheet(readExcelET);

    }

    // extent report configuration setup
    protected void extentReportConfiguration() {

        extentSparkReporter = new ExtentSparkReporter("src/test/java//Reports/Test_Report.html");

        extentSparkReporter.config().setEncoding("utf-8");
        extentSparkReporter.config().setDocumentTitle("Selenium Assignment Report");
        extentSparkReporter.config().setReportName("Test Reports");
        extentSparkReporter.config().setTheme(Theme.DARK);


        extentReports.setSystemInfo("Organization", "Hashedin By Deloitte");
        extentReports.setSystemInfo("Created by ", "Neeraj Dhurandher");
        extentReports.attachReporter(extentSparkReporter);

        ExtentTest extentTest = extentReports.createTest("Base Test Class");

        ExtentTest extentReportNode = extentTest.createNode("Extent Report Setup");
        extentReportNode.log(Status.PASS,"extent report configuration setup");
        extentReportNode.log(Status.PASS,"Set Extent report Title");
        extentReportNode.log(Status.PASS,"Set Extent report system Info");
        log.info("Base test initialized");
        log.info("Extent Report Setup");
    }






}
