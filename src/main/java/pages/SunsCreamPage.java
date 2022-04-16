package pages;

import Util.UtilClass;
import com.aventstack.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class SunsCreamPage extends BasePage {

    private ArrayList<String> item_Category_List = new ArrayList<>();


    public void selectLeastItemInCategory(){

        ExtentTest sunscreensPurchaseET = extentReports.createTest("Select SunsCream");

        ExtentTest sunscreenNodeET = sunscreensPurchaseET.createNode("Purchased Sunscreens List");

        loadItems();

        for (String category : item_Category_List) {

            int index;

            try {
                index = super.leastExpensiveItemInCategory(category, sunscreenNodeET);
                super.addCartNthItem(index);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }

    }

    private void loadItems() {

        item_Category_List.add("SPF-30");
        item_Category_List.add("SPF-50");
    }
}
