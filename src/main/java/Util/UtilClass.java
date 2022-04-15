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
    WebDriver driver;
    Logger log;

    public static ArrayList<Card> cardArrayList = new ArrayList<>();

    public UtilClass(WebDriver driver, Logger log){
        this.driver = driver;
        this.log = log;
    }

    public void readCardDetailExcelSheet(){

        try {
            CardExcelReader cardExcelReader = new CardExcelReader("src/main/resources/Data/cardDetails.xlsx");
            cardArrayList = cardExcelReader.readCardDetailsFile();
            log.info("read");
        }catch (IOException exception){
            exception.printStackTrace();
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

    public void takeScreenshot(String path ,String fileName) throws IOException, AWTException {
        fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+fileName;

        String destination = System.getProperty("user.dir").toLowerCase(Locale.ROOT);

        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File(path+fileName+".png"));
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
            throw new Exception("something went wrong in item list");
        }


        for (int i = 0 ; i < priceWebElementList.size(); i++){

            String productName = productList.get(i).getText();

            String priceTag = priceWebElementList.get(i).getText();
            priceTag = priceTag.replace("Rs. ", "");
            priceTag = priceTag.replace("Price: ","");

            int price = Integer.parseInt(priceTag);

            if(price < minPrice && productName.contains(category)){
                minPrice = price;
                minPriceItemIndex = i;
                minPriceItem = productName;
            }

            System.out.println(price);
        }

        extentTest.log(Status.PASS,"Least expensive item "+ minPriceItem+" Price :- "+ minPrice);

        extentTest.log(Status.PASS,"Least expensive item "+ minPriceItem+" Price :- "+ minPrice);

        return minPriceItemIndex+1;

    }






    public void closeWindow() {
        driver.close();
    }

}
