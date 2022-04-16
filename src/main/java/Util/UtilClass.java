package Util;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilClass {
    static WebDriver driver;
    static Logger log;

    public static ArrayList<Card> cardArrayList = new ArrayList<>();

    public UtilClass() {
    }

    public UtilClass(WebDriver driver, Logger log){
        this.driver = driver;
        this.log = log;
    }

    public void readCardDetailExcelSheet(ExtentTest extentTest){

        ExtentTest readExcelReport = extentTest.createNode("Read Card Details Excel Sheet");

        try {
            CardExcelReader cardExcelReader = new CardExcelReader("src/main/resources/Data/cardDetails.xlsx");
            cardArrayList = cardExcelReader.readCardDetailsFile();
            log.info("read");
            readExcelReport.log(Status.PASS,"Read Card Details Excel Sheet Successful");
        }catch (IOException exception){
            exception.printStackTrace();
            readExcelReport.log(Status.FAIL,"Read Card Details Excel Sheet unsuccessful");
            readExcelReport.log(Status.FAIL,exception.getMessage());
            log.error(exception.getMessage());
        }

    }

    public static ArrayList<Card> getCardArrayList() {
        return cardArrayList;
    }

    public void alertBoxOk() {
        Alert al = driver.switchTo().alert();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        al.accept();
        System.out.println("click ok in alert box");
    }

    public String takeScreenshot(String fileName, ExtentTest extentTest){

        fileName += new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+".png";

        String destination = "src/test/java/Reports/ScreenShots/";
        String filePath = destination + fileName;

        try {

            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File(filePath));

            log.error("Screenshot captured successfully");
            extentTest.log(Status.PASS,"Screenshot captured successfully");

        }catch (IOException | AWTException exception){
            log.error("Screenshot captured unsuccessfully");
            log.error(exception);
            extentTest.log(Status.FAIL,"Screenshot captured unsuccessfully");
            extentTest.log(Status.FAIL,exception.getMessage());

        }

        return fileName;
    }


    public void addCartNthItem(int index) {
        driver.findElement(By.xpath("(//button[contains(@class,'btn btn-primary')]) ["+index+"]")).click();
    }

    public void clickIButton(){
        driver.findElement(By.xpath("(//span[contains(@class,'octicon octicon-info')])")).click();
    }


    public int leastExpensiveItemInCategory(String category, ExtentTest extentTest) throws Exception {

        int minPriceItemIndex = 0;
        int minPrice = Integer.MAX_VALUE;
        String minPriceItem = "";

        List<WebElement> priceWebElementList = driver.findElements(By.xpath("//p[contains(text(),'Price:')] "));

        List<WebElement> productList = driver.findElements(By.xpath("//p[@class ='font-weight-bold top-space-10'] "));

        System.out.println("priceWebElementList size " + priceWebElementList.size());
        System.out.println("productList size " + productList.size());

        if(priceWebElementList.size() != productList.size()){
            extentTest.log(Status.FAIL,"something went wrong in item list");
            log.error("something went wrong in item list");
            throw new Exception("something went wrong in item list");
        }


        for (int i = 0 ; i < priceWebElementList.size(); i++){

            String productName = productList.get(i).getText().toLowerCase();

            String priceTag = priceWebElementList.get(i).getText();
            priceTag = priceTag.replace("Rs. ", "");
            priceTag = priceTag.replace("Price: ","");

            int price = Integer.parseInt(priceTag);

            if(price < minPrice && productName.contains(category.toLowerCase())){
                minPrice = price;
                minPriceItemIndex = i;
                minPriceItem = productName;
            }
        }

        extentTest.log(Status.PASS,"Least expensive item "+ minPriceItem+" Price :- "+ minPrice+" in " + category + " category.");

        return minPriceItemIndex+1;

    }






    public void closeWindow() {
        driver.close();
    }

}
