package org.example.model;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.example.PageObjectBase;
import org.example.utils.WaitUtils;

import java.util.List;

public class MyAccountPage extends PageObjectBase {

    @FindBy(className = "inventory_list")
    public WebElement authenticationPanel;
    @FindBy(css = ".inventory_item")
    public List<WebElement> items;

    @FindBy(css = ".cart_item")
    public List<WebElement> itemsInShoppingCart;

    @FindBy(css = ".product_sort_container")
    public WebElement productSortContainer;

    @FindBy(id = "inventory_container")
    public WebElement inventoryContainer;

    @FindBy(id = "inventory_details_container")
    public WebElement inventoryItemContainer;

    @FindBy(className = "inventory_details_desc_container")
    public WebElement inventoryDetailsContainer;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isUserOnMyAccountPage() {
        try {
            WaitUtils.waitForVisibleElement(authenticationPanel, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isProductSortContainerIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(productSortContainer, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isInventoryContainerIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(inventoryContainer, driver);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
}
