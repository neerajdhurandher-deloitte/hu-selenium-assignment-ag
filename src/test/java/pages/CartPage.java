package pages;
import Util.Card;
import Util.UtilClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


import java.time.Duration;
import java.util.List;

public class CartPage extends UtilClass {
    WebDriver driver;
    Logger log;

    public CartPage(WebDriver driver, Logger log) {
        super(driver,log);
        this.driver = driver;
        this.log = log;
    }

    public void clickCartButton(){
        driver.findElement(By.xpath("(//button[contains(@class,'thin-text nav-link')])")).click();
    }

    public boolean verifyCartItem(int validItemCount, ExtentTest verifyCart){

        List<WebElement> rows_table = driver.findElements(By.tagName("tr"));

        System.out.println("rows :- " + rows_table.size());

        try{
            assertThat(rows_table.size()-1,is(equalTo(validItemCount)));
            verifyCart.log(Status.FAIL,"Cart items are verified.");
            log.info("Cart items are verified.");
            return true;
        }
        catch (AssertionError assertionError) {
            verifyCart.log(Status.FAIL,"Cart items are not matches with selected items.");
            verifyCart.log(Status.FAIL,assertionError.getMessage());
            log.error("Cart items are not matches with selected items");
            log.error(assertionError.getMessage());
            assertThat(rows_table.size()-1,is(equalTo(validItemCount)));

            return false;
        }
    }

    public void clickPay(){

        WebElement element = driver.findElement(By.xpath("//button[@class = 'stripe-button-el']"));

        // wait until button is not visible
        while (!element.isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        element.click();

    }

    public void fillCardDetails(int index){

        Card card = getCardArrayList().get(index);

        String cardNo = card.getCardNo();
        cardNo = cardNo.substring(1,cardNo.length()-1);

        for(int i = 0;i < cardNo.length();i+=4){

            String val = cardNo.substring(i,i+4);
            driver.findElement(By.xpath("//input[@id= 'card_number']")).sendKeys(val);
        }

//        driver.findElement(By.cssSelector("input[id*= 'card_number']")).sendKeys("4242424242424240");


        driver.findElement(By.cssSelector("input[id*='email']")).sendKeys(card.getEmail());
        log.info("email " + card.getEmail());
        driver.findElement(By.cssSelector("input[id*= 'cc-exp']")).sendKeys(card.getExpiryMonth());
        log.info("getExpiryMonth " + card.getExpiryMonth());

        driver.findElement(By.cssSelector("input[id*= 'cc-exp']")).sendKeys(card.getExpiryYear());
        log.info("getExpiryYear " + card.getExpiryYear());

        driver.findElement(By.cssSelector("input[id*= 'cc-csc']")).sendKeys(card.getCvc());
        log.info("getCvc " + card.getCvc());

        if(driver.findElement(By.cssSelector("input[id*= 'billing-zip']")).isDisplayed()) {
            driver.findElement(By.cssSelector("input[id*= 'billing-zip']")).sendKeys(card.getZipCode());
            log.info("getZipCode " + card.getZipCode());
        }


    }

    public void payAmount(){
        driver.findElement(By.xpath("//button[@id = 'submitButton']")).click();
    }
}
