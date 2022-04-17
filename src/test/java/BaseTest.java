import Util.UtilClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.*;

public class BaseTest extends BasePage{

    UtilClass utilClass;
    TemperaturePage temperaturePage;
    SunsCreamPage sunsCreamPage;
    MoisturizersPage moisturizersPage;
    CartPage cartPage;
    PaymentStatusPage paymentStatusPage;




    @BeforeTest
    public void init() {

        extentReportConfiguration();

        webDriverSetUp();

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

        try {
            Thread.sleep(3000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        paymentStatusPage.paymentStatus();
        paymentStatusPage.validateStatus();

    }

    @AfterTest
    public  void closeBrowser(){
//        actionEvents.closeWindow();
        extentReports.flush();
    }


}
