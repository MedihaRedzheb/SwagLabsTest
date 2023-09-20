package org.example.model;

import org.openqa.selenium.support.PageFactory;
import org.example.PageObjectBase;
import org.example.utils.SeleniumUtils;
import org.example.utils.WaitUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObjectBase {

    @FindBy(id = "user-name")
    public WebElement myUserNameButton;

    @FindBy(id = "password")
    public WebElement myPasswordButton;
    @FindBy(id = "login-button")
    public WebElement loginButton;
    @FindBy(id = "login_button_container")
    public WebElement homeIntroSection;

    @FindBy(className = "error-button")
    public WebElement errorButtonForIncorrectUser;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isUserOnHomePage() {
        try {
            WaitUtils.waitForVisibleElement(homeIntroSection, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isMyUserNameButtonIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(myUserNameButton, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isMyPasswordButtonIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(myPasswordButton, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isErrorButtonForIncorrectUserVisible() {
        try {
            WaitUtils.waitForVisibleElement(errorButtonForIncorrectUser, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public void setUserName(String userName) {
        SeleniumUtils.populateTextField(myUserNameButton, userName, driver);
    }

    public void setPassword(String password) {
        SeleniumUtils.populateTextField(myPasswordButton, password, driver);
    }

    public void clickOnLoginButton() {
        SeleniumUtils.clickElement(loginButton, driver);
    }
}
