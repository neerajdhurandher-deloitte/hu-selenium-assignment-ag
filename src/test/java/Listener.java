import Util.UtilClass;
import org.apache.poi.ss.formula.functions.Slope;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.IOException;

public class Listener extends BaseTest implements ITestListener {

    UtilClass utilClass = new UtilClass();

    @Override
    public void onTestStart(ITestResult result) {
        super.init();
        ITestListener.super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.closeBrowser();
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {

        log.info("Test Failed");
        log.error("Test Failed");

        takeScreenshot("Failed Test case" + getCurrentDateTime());

        ITestListener.super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }
}
