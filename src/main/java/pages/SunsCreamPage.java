package pages;

import Util.UtilClass;
import com.aventstack.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SunsCreamPage extends UtilClass {

    WebDriver driver;
    Logger log;


    public SunsCreamPage(WebDriver driver, Logger log) {
        super(driver,log);
        this.driver = driver;
        this.log = log;
    }

    public void selectLeastItemInCategory(String category, ExtentTest extentTest){

        int index;

        try {
            index = super.leastExpensiveItemInCategory(category,extentTest);
            super.addCartNthItem(index);
        }catch (Exception e){
            log.info(e.getMessage());
        }

    }
}
