package pages;

import Util.UtilClass;
import com.aventstack.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class MoisturizersPage extends BasePage{


    private ArrayList<String> item_Category_List = new ArrayList<>();


    public void selectLeastItemInCategory(){

        ExtentTest moisturizerPurchaseET = extentReports.createTest("Select Moisturizer");

        ExtentTest moisturizerNodeET = moisturizerPurchaseET.createNode("Purchased Moisturizers List");

        loadItems();

        for (String category : item_Category_List) {

            int index;

            try {
                index = super.leastExpensiveItemInCategory(category, moisturizerNodeET);
                super.addCartNthItem(index);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }

    }

    private void loadItems() {

        item_Category_List.add("Aloe");
        item_Category_List.add("Almond");
    }
}
