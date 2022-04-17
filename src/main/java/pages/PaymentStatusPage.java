package pages;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import org.testng.Assert;

public class PaymentStatusPage extends BasePage{

    String confirmationPageEndPoint = "confirmation";
    String paymentStatusElement = "//div[@class = 'row justify-content-center']";
    String validPaymentStatus = "PAYMENT SUCCESS";

    static String paymentStatusStr;

    static ExtentTest paymentStatusTest;
    static ExtentTest paymentStatusETNode;

    public void paymentStatus(){

        paymentStatusTest = extentReports.createTest("Payment Status Test");
        paymentStatusETNode = paymentStatusTest.createNode("Read Payment status");

        while (!driver.getCurrentUrl().contains(confirmationPageEndPoint)){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            System.out.println("End point " +driver.getCurrentUrl() );
        }

        while (!driver.findElement(By.xpath(paymentStatusElement)).isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        paymentStatusStr =  driver.findElement(By.xpath(paymentStatusElement)).getText();
        System.out.println(paymentStatusStr);

        log.info("Payment Status :- " + paymentStatusStr);
        paymentStatusETNode.log(Status.PASS,"Payment Status :- " + paymentStatusStr);

        String payment_Status_ss_path =  takeScreenshot("Payment Status", paymentStatusETNode);

        paymentStatusETNode.addScreenCaptureFromPath("ScreenShots/" + payment_Status_ss_path, "Payment Status Screenshot");


    }

    public void validateStatus(){

        try {
            Assert.assertEquals(paymentStatusStr, validPaymentStatus);
            paymentStatusETNode.log(Status.PASS, "Payment Status Validated");
            log.info( "Payment Status Validated");
        }catch (AssertionError assertionError){
            paymentStatusETNode.log(Status.FAIL, "Payment status not validate");
            log.error("Payment status not validate");
            Assert.assertEquals(paymentStatusStr, validPaymentStatus);

        }

    }
}
