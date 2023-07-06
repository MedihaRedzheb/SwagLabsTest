package org.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Arrays;
import java.util.List;


public class WaitUtils {

    public static final int DEFAULT_WAIT_TIME_SECONDS = 5; // in seconds
    private static final int POLL_FREQUENCY_INTERVAL = 100; // in milli-seconds

    private static final List<Class<? extends Throwable>> IGNORE_EXCEPTIONS = Arrays.asList(NotFoundException.class,
            NoSuchElementException.class,
            StaleElementReferenceException.class);


    /**
     * Waits for the WebElement to become visible
     * @return  The located WebElement instance
     */
    public static WebElement waitForVisibleElement(WebElement element, WebDriver driver) {
        return waitForVisibleElement(element, driver, DEFAULT_WAIT_TIME_SECONDS, POLL_FREQUENCY_INTERVAL);
    }


    /**
     * Waits for the WebElement to become visible
     * @return  The located WebElement instance
     */
    public static WebElement waitForVisibleElement(WebElement element, WebDriver driver, long timeOutInSeconds,
                                                   long pollIntervalInMs) {
        WebDriverWait wait = createWaitInstance(driver, timeOutInSeconds, pollIntervalInMs);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    /**
     * Waits for an element to become clickable
     * @return The located WebElement instance
     */
    public static WebElement waitForClickableElement(WebElement element, WebDriver driver) {

        WebDriverWait wait = createWaitInstance(driver);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Creates an instance of the WebDriverWait class
     */
    private static WebDriverWait createWaitInstance(WebDriver driver) {
        return createWaitInstance(driver, DEFAULT_WAIT_TIME_SECONDS, POLL_FREQUENCY_INTERVAL);
    }

    /**
     * Creates an instance of the WebDriverWait class
     */
    private static WebDriverWait createWaitInstance(WebDriver driver, long timeOutInSeconds, long pollIntervalInMs) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds, pollIntervalInMs);
        wait.ignoreAll(IGNORE_EXCEPTIONS);
        wait.withMessage("The specified element cannot be located, timed out after " + timeOutInSeconds + " seconds");
        return wait;
    }

}
