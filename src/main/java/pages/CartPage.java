package pages;
import Util.Card;
import Util.UtilClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;


import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    String cartBtn = "(//button[contains(@class,'thin-text nav-link')])";
    String payBtn = "//button[@class = 'stripe-button-el']";
    String confirmPatBtn = "//button[@id = 'submitButton']";
    String cardDetailsFormFrame = "//iframe[@class = 'stripe_checkout_app']";

    String cardNoFieldXpath = "//input[@id= 'card_number']";
    String emailFieldXpath = "input[id*='email']";
    String expMonthFieldXpath = "input[id*= 'cc-exp']";
    String expYearFieldXpath = "input[id*= 'cc-exp']";
    String cvcFieldXpath = "input[id*= 'cc-csc']";
    String zipCodeFieldXpath = "input[id*= 'billing-zip']";

    int validItemCount = 2;

    public void clickCartButton(){
        driver.findElement(By.xpath(cartBtn)).click();
    }

    public void verifyCartItem(){

        ExtentTest verifyCart = extentReports.createTest("Verify Cart Item");

        verifyCart = verifyCart.createNode("Verifying items");

        List<WebElement> rows_table = driver.findElements(By.tagName("tr"));

        System.out.println("rows :- " + rows_table.size());

        String filePath = takeScreenshot("verifyCartItems",verifyCart);

        try{
            Assert.assertEquals(validItemCount,rows_table.size()-1);
            verifyCart.log(Status.PASS,"Cart items are verified.");
            verifyCart.addScreenCaptureFromPath("ScreenShots/" + filePath, "verify Cart Items screenshot");
            log.info("Cart items are verified.");
        }
        catch (AssertionError assertionError) {
            verifyCart.log(Status.FAIL,"Cart items are not matches with selected items.");
            verifyCart.log(Status.FAIL,assertionError.getMessage());
            verifyCart.addScreenCaptureFromPath("ScreenShots/" + filePath, "verify Cart Items screenshot");

            log.error("Cart items are not matches with selected items");
            log.error(assertionError.getMessage());
            Assert.assertEquals(validItemCount,rows_table.size()-1);

        }
    }



    public void payToProcess() {


        WebElement element = driver.findElement(By.xpath(payBtn));

        // wait until button is not visible
        while (!element.isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        element.click();

        WebElement frame = driver.findElement(By.xpath(cardDetailsFormFrame));

        while (!frame.isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        driver.switchTo().frame(frame);

        fillCardDetails(0);

    }


    public void fillCardDetails(int index){

        Card card = getCardArrayList().get(index);

        String cardNo = card.getCardNo();
        cardNo = cardNo.substring(1,cardNo.length()-1);

        for(int i = 0;i < cardNo.length();i+=4){

            String val = cardNo.substring(i,i+4);
            driver.findElement(By.xpath(cardNoFieldXpath)).sendKeys(val);
        }

//        driver.findElement(By.cssSelector("input[id*= 'card_number']")).sendKeys("4242424242424240");


        driver.findElement(By.cssSelector(emailFieldXpath)).sendKeys(card.getEmail());
        log.info("email " + card.getEmail());
        driver.findElement(By.cssSelector(expMonthFieldXpath)).sendKeys(card.getExpiryMonth());
        log.info("getExpiryMonth " + card.getExpiryMonth());

        driver.findElement(By.cssSelector(expYearFieldXpath)).sendKeys(card.getExpiryYear());
        log.info("getExpiryYear " + card.getExpiryYear());

        driver.findElement(By.cssSelector(cvcFieldXpath)).sendKeys(card.getCvc());
        log.info("getCvc " + card.getCvc());

        if(driver.findElement(By.cssSelector(zipCodeFieldXpath)).isDisplayed()) {
            driver.findElement(By.cssSelector(zipCodeFieldXpath)).sendKeys(card.getZipCode());
            log.info("getZipCode " + card.getZipCode());
        }


    }



    public void payAmount(){

        driver.findElement(By.xpath(confirmPatBtn)).click();

        driver.switchTo().defaultContent();

    }
}
