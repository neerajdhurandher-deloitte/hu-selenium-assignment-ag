package pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class TemperaturePage extends BasePage{

    SunsCreamPage sunsCreamPage = new SunsCreamPage();
    MoisturizersPage moisturizersPage = new MoisturizersPage();

    private WebElement webElement;

    String tempTextXpath = "//span[@id = 'temperature']";
    String sunScreenBtnXpath = "//button[text() = 'Buy sunscreens']";
    String moisturizerBtnXpath = "//button[text() = 'Buy moisturizers']";


    private int currentTemp;

    public void getTemperature(){

        webElement = driver.findElement(By.xpath(tempTextXpath));
        String currentTemp = webElement.getText();
        String[] arrOfStr = currentTemp.split(" ");

        this.currentTemp =  Integer.parseInt(arrOfStr[0]);
    }

    public void choiceForPurchase() {

        ExtentTest tempTest = extentReports.createTest("Temperature Page Test");


        log.info("Current temperature is :- " + currentTemp);
        tempTest.log(Status.PASS, "Current Temperature :- " + currentTemp);

        String filePath = takeScreenshot("Current_Temperature", tempTest);

        tempTest.addScreenCaptureFromPath("ScreenShots/" + filePath, "Current Temperature Screenshot");

        clickIButton();


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

        clickMoisturizersBtn();

        moisturizersPage.selectLeastItemInCategory();

    }

    private void goForSunscreens() {

        clickSunscreensBtn();

        sunsCreamPage.selectLeastItemInCategory();
    }

    public void clickSunscreensBtn(){
        driver.findElement(By.xpath(sunScreenBtnXpath)).click();
    }

    public void clickMoisturizersBtn(){
        driver.findElement(By.xpath(moisturizerBtnXpath)).click();
    }
}
