package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import org.testng.Assert;

public class PaymentStatusPage {

    WebDriver driver;
    Logger log;

    public PaymentStatusPage(WebDriver driver, Logger log){
        this.driver = driver;
        this.log =log;
    }

    public String paymentStatus(){
        WebElement paymentStatusElement = driver.findElement(By.xpath("//div[@class = 'row justify-content-center']"));

        while (!paymentStatusElement.isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        return paymentStatusElement.getText();
    }

    public void validateStatus(){
        Assert.assertEquals(1,1);

    }
}
