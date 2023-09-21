package org.example;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.example.model.*;

public class HomePageTests extends TestBase {

    private HomePage homePage;
    private MyAccountPage myAccountPage;

    public HomePageTests() throws Throwable {
        super();
        initialisePageObjects();
    }

    protected void initialisePageObjects() {
        homePage = new HomePage(driver);
        myAccountPage = new MyAccountPage(driver);
    }

    @BeforeMethod
    public void login() {
        homePage.setUserName(USER);
        homePage.setPassword(PASSWORD);
        homePage.clickOnLoginButton();
    }
    @AfterMethod
    public void logout() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    @Test(testName = "Login from My Home Page")
    public void accessMyAccount() {
//        Assert.assertTrue(homePage.isUserOnHomePage(), "User is not on Home page!");
//        Assert.assertTrue(homePage.isMyUserNameButtonIsVisible(), "User name button is not visible!");
//        Assert.assertTrue(homePage.isMyPasswordButtonIsVisible(), "Password button is not visible!");
//
//        homePage.setUserName(USER);
//        homePage.setPassword(PASSWORD);
//        homePage.clickOnLoginButton();
        Assert.assertTrue(myAccountPage.isUserOnMyAccountPage(), "User is no on My Account page!");
        deleteCookies();
    }

//    @Test(testName = "Login from My Home Page with incorrect password")
//    public void accessMyAccountWithIncorrectPassword() {
//        Assert.assertTrue(homePage.isUserOnHomePage(), "User is not on Home page!");
//        Assert.assertTrue(homePage.isMyUserNameButtonIsVisible(), "User name button is not visible!");
//        Assert.assertTrue(homePage.isMyPasswordButtonIsVisible(), "Password button is not visible!");
//
//        homePage.setUserName(USER);
//        homePage.setPassword("secret");
//        homePage.clickOnLoginButton();
//        Assert.assertTrue(homePage.isErrorButtonForIncorrectUserVisible(), "Incorrect password!");
//        deleteCookies();
//    }

    @Test(testName = "Logout from My Account Page")
    public void logoutFromMyAccountPage() {
        login();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();
        Assert.assertTrue(homePage.isUserOnHomePage());
    }
}
