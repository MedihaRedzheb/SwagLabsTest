package org.example;

import org.testng.ITestContext;
import org.testng.annotations.*;
import org.example.utils.PropertiesHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;
    public static String USER;
    public static String PASSWORD;

    public TestBase() throws Throwable {
        testSetup();
    }

    @AfterClass()
    public void afterClass(ITestContext context) {
        if (driver != null) {
            testTeardown();
        }
    }

    protected void testSetup() throws Throwable {
        PropertiesHandler.loadProperties("config/default-selenium.properties");

        USER = PropertiesHandler.getProperty("app.user");
        PASSWORD = PropertiesHandler.getProperty("app.user.password");

        driver = getDriverInstance(PropertiesHandler.getProperty("default.browser"));
        driver.manage().timeouts().implicitlyWait(Long.parseLong(PropertiesHandler.getProperty("selenium.timeout")), TimeUnit.SECONDS);
        driver.navigate().to(PropertiesHandler.getProperty("app.base.url"));
        driver.manage().window().maximize();
    }


    protected void testTeardown() {
        driver.manage().deleteAllCookies();
        driver.quit();
        driver = null;
    }

    public void deleteCookies() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    public WebDriver getDriverInstance(String browser) {

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
                if (driver == null) {
                    driver = new ChromeDriver();
                }
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "src/test/resources/drivers/msedgedriver.exe");

                if (driver == null) {
                    driver = new EdgeDriver();
                }
                break;
            default:
                if (driver == null) {
                    driver = new ChromeDriver();
                }
        }
        return driver;
    }
}
