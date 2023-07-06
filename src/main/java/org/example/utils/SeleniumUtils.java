package org.example.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;

public final class SeleniumUtils {

    public static void populateTextField(WebElement element, String text, WebDriver driver){
        WaitUtils.waitForVisibleElement(element,driver);
        element.clear();
        element.sendKeys(text);
    }

    public static void clickElement(WebElement element, WebDriver driver){
        WaitUtils.waitForClickableElement(element,driver);
        element.click();
    }

    /**
     * Takes a screenshot of the current page
     * @param fileName   The filename to save the screenshot as
     */
    public static void takeScreenShotAs(String fileName, WebDriver driver) {
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            String path = System.getProperty("user.dir") + "/target/screenshots/";
            FileUtils.copyFile(srcFile, new File(path+fileName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

