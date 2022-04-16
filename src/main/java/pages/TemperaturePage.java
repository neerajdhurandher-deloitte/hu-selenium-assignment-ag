package pages;

import Util.UtilClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class TemperaturePage extends BasePage{

    private WebDriver driver;
    private Logger log;

    private WebElement webElement;

    String tempTextXpath = "//span[@id = 'temperature']";
    String sunScreenBtnXpath = "//button[text() = 'Buy sunscreens']";
    String moisturizerBtnXpath = "//button[text() = 'Buy moisturizers']";





    public TemperaturePage(WebDriver driver, Logger log) {
        this.driver = driver;
        this.log = log;

    }

    public int getTemperature(){

        webElement = driver.findElement(By.xpath(tempTextXpath));
        String currentTemp = webElement.getText();
        String[] arrOfStr = currentTemp.split(" ");

        return Integer.parseInt(arrOfStr[0]);
    }

    public void choiceForPurchase() {

        ExtentTest tempTest = extentReports.createTest("Temperature Page Test");

        int currentTemp = getTemperature();

        log.info("Current temperature is :- " + currentTemp);
        tempTest.log(Status.PASS, "Current Temperature :- " + currentTemp);

        String filePath = utilClass.takeScreenshot("Current_Temperature", tempTest);

        tempTest.addScreenCaptureFromPath("ScreenShots/" + filePath, "Current Temperature Screenshot");


        if (currentTemp < 19) {
            tempTest.log(Status.PASS, "Purchase Moisturizers");
            log.info("Purchase Moisturizers");
            goForMoisturizers();

        } else if (currentTemp > 34) {
            tempTest.log(Status.PASS, "Purchase Sunscreens");
            log.info("Purchase Sunscreens");
            goForSunscreens();

        } else {
            tempTest.log(Status.FAIL, "Invalid Temperature. That is between 20 to 33.");
            driver.navigate().refresh();
        }
    }

    private void goForMoisturizers() {

        ExtentTest extentTest = extentReports.createTest("Select Moisturizer");

        clickMoisturizersBtn();

        log.info("clickMoisturizerBtn");

        clickIButton();

        selectLeastItemInCategory("Aloe",extentTest);
        selectLeastItemInCategory("Almond",extentTest);

        clickCartButton();

        ExtentTest verifyCart = extentTest.createNode("verifying cart items");

        if(cartPage.verifyCartItem(2,verifyCart)){

            payToProcess();
        }
    }

    private void goForSunscreens() {

        ExtentTest sunscreensPurchaseET = extentReports.createTest("Select SunsCream");

        temperaturePage.clickSunscreensBtn();

        sunsCreamPage.clickIButton();
        sunscreensPurchaseET.log(Status.PASS,"Get the info");

        ExtentTest purchaseSunscreensET = sunscreensPurchaseET.createNode("Purchased Sunscreens List");

        sunsCreamPage.selectLeastItemInCategory("SPF-50",purchaseSunscreensET);
        sunsCreamPage.selectLeastItemInCategory("SPF-30",purchaseSunscreensET);

        cartPage.clickCartButton();

        ExtentTest verifyCart = extentTest.createNode("verifying cart items");

        if(cartPage.verifyCartItem(2,verifyCart)){
            payToProcess();
        }




    }

    public void clickSunscreensBtn(){
        driver.findElement(By.xpath(sunScreenBtnXpath)).click();
    }

    public void clickMoisturizersBtn(){
        driver.findElement(By.xpath(moisturizerBtnXpath)).click();
    }
}
