package pages;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import org.testng.Assert;

public class PaymentStatusPage extends BasePage{

    String confirmationPageEndPoint = "confirmation";
    String paymentStatusElement = "//div[@class = 'row justify-content-center']";
    String validPaymentStatus = "PAYMENT SUCCESS";

    static String paymentStatusStr;

    ExtentTest paymentStatusTest = extentReports.createTest("Payment Status Test");


    public void paymentStatus(){

        ExtentTest paymentStatusETNode = paymentStatusTest.createNode("Read Payment status");

        while (!driver.getCurrentUrl().contains(confirmationPageEndPoint)){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            System.out.println("End point " +driver.getCurrentUrl() );
        }

        while (!driver.findElement(By.xpath(paymentStatusElement)).isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        paymentStatusStr =  driver.findElement(By.xpath(paymentStatusElement)).getText();

        System.out.println(paymentStatusStr);
    }

    public void validateStatus(){
        Assert.assertEquals(paymentStatusStr,validPaymentStatus);

    }
}
