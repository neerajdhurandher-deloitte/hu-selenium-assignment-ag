package pages;

import Util.UtilClass;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class TemperaturePage extends UtilClass{

    private WebDriver driver;
    private Logger log;

    private WebElement webElement;

    public TemperaturePage(WebDriver driver, Logger log) {
        super(driver,log);
        this.driver = driver;

    }

    public int getTemperature(){

        webElement = driver.findElement(By.xpath("//span[@id = 'temperature']"));
        String currentTemp = webElement.getText();
        String[] arrOfStr = currentTemp.split(" ");

        return Integer.parseInt(arrOfStr[0]);
    }

    public void clickSunscreensBtn(){
        WebElement element = driver.findElement(By.xpath("//button[text() = 'Buy sunscreens']"));

        // wait until button is not visible
        while (!element.isDisplayed()){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        element.click();
    }

    public void clickMoisturizersBtn(){
        driver.findElement(By.xpath("//button[text() = 'Buy moisturizers']")).click();
    }
}
