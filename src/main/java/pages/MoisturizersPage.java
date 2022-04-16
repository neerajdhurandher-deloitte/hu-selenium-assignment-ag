package pages;

import Util.UtilClass;
import com.aventstack.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MoisturizersPage extends BasePage{
    private WebDriver driver;
    private Logger log;

    private WebElement webElement;

    public MoisturizersPage(WebDriver driver, Logger log) {
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
