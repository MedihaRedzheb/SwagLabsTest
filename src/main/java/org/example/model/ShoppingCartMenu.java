package org.example.model;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.example.PageObjectBase;
import org.example.utils.SeleniumUtils;
import org.example.utils.WaitUtils;

import java.util.List;

public class ShoppingCartMenu extends PageObjectBase {
    @FindBy(css = ".inventory_item_name")
    public List<WebElement> itemInShoppingCart;

    @FindBy(xpath = "//*[@id=\"shopping_cart_container\"]/a")
    public WebElement shoppingCartButton;

    @FindBy(id = "checkout")
    public WebElement checkoutButton;

    @FindBy(css = ".checkout_info")
    public WebElement checkoutInfo;

    @FindBy(id = "first-name")
    public WebElement firstName;

    @FindBy(id = "last-name")
    public WebElement lastName;

    @FindBy(id = "postal-code")
    public WebElement postalCode;

    @FindBy(id = "continue")
    public WebElement continueButton;

    @FindBy(id = "checkout_summary_container")
    public WebElement checkoutSummaryContainer;

    @FindBy(css = ".error-button")
    public WebElement errorButtonCheckout;

    @FindBy(id = "finish")
    public WebElement finishTheOrderButton;

    @FindBy(id = "checkout_complete_container")
    public WebElement checkoutCompleteContainer;

    public ShoppingCartMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isShoppingCartButtonVisible() {
        try {
            WaitUtils.waitForVisibleElement(shoppingCartButton, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isErrorButtonCheckoutVisible() {
        try {
            WaitUtils.waitForVisibleElement(errorButtonCheckout, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isCheckoutInfoIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(checkoutInfo, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isCheckoutSummaryContainerVisible() {
        try {
            WaitUtils.waitForVisibleElement(checkoutSummaryContainer, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isFinishTheOrderButtonVisible() {
        try {
            WaitUtils.waitForVisibleElement(finishTheOrderButton, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isCheckoutCompleteContainerVisible() {
        try {
            WaitUtils.waitForVisibleElement(checkoutCompleteContainer, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public void clickOnAddToCartButton(WebElement buttonName) {
        SeleniumUtils.clickElement(buttonName, driver);
    }

    public void clickOnRemoveFromCartButton(WebElement buttonName) {
        SeleniumUtils.clickElement(buttonName, driver);
    }

    public void clickOnShoppingCartButton() {
        SeleniumUtils.clickElement(shoppingCartButton, driver);
    }

    public void clickOnCheckoutButton() {
        SeleniumUtils.clickElement(checkoutButton, driver);
    }

    public void clickOnContinueButton() {
        SeleniumUtils.clickElement(continueButton, driver);
    }

    public void clickOnFinishTheOrderButton() {
        SeleniumUtils.clickElement(finishTheOrderButton, driver);
    }

}
