package org.example;

import com.google.common.collect.Ordering;
import org.example.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import org.example.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAccountPageTests extends TestBase {

    private HomePage homePage;
    private MyAccountPage myAccountPage;
    private ShoppingCartMenu shoppingCartMenu;

    public MyAccountPageTests() throws Throwable {
        super();
        initialisePageObjects();
    }

    protected void initialisePageObjects() {
        homePage = new HomePage(driver);
        myAccountPage = new MyAccountPage(driver);
        shoppingCartMenu = new ShoppingCartMenu(driver);

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

    @Test(testName = "Is the inventory container is visible")
    public void countOfItemsInInventoryContainer() {
//        login();
        Assert.assertTrue(myAccountPage.isInventoryContainerIsVisible(), "Inventory container is not visible!");
        List<WebElement> elements = driver.findElements(By.className("inventory_item_name"));
        Assert.assertEquals(elements.size(), 6, "Unexpected number of items in inventory container!");
//        logout();
    }

    @Test(testName = "Filtering menu by name work correct")
    public void isFilteringMenuWorkCorrect() {
//        login();
        Assert.assertTrue(myAccountPage.isProductSortContainerIsVisible(), "Product sort container is not visible!");
        Select drpCountry = new Select(myAccountPage.productSortContainer);
        drpCountry.selectByVisibleText("Name (Z to A)");

        List<String> allItemsName = new ArrayList<>();

        for (WebElement webElement : myAccountPage.items) {
            String inventory_item_name = ((RemoteWebElement) webElement).findElementByClassName("inventory_item_name").getText();
            allItemsName.add(inventory_item_name);
        }

        Assert.assertTrue(Ordering.natural().reverse().isOrdered(allItemsName), "Filtering menu by name does not work correct!");
//        logout();
    }

    @Test(testName = "Filtering menu by price work correct")
    public void isFilteringMenuByPriceWorkCorrect() {
//        login();
        Assert.assertTrue(myAccountPage.isProductSortContainerIsVisible(), "Product sort container is not visible!");
        Select drpCountry = new Select(myAccountPage.productSortContainer);
        drpCountry.selectByVisibleText("Price (low to high)");

        List<BigDecimal> allItemsPrice = new ArrayList<>();

        for (WebElement webElement : myAccountPage.items) {
            String inventory_item_name = ((RemoteWebElement) webElement).findElementByClassName("inventory_item_price").getText();
            allItemsPrice.add(new BigDecimal(getPrice(inventory_item_name)));
        }
        Assert.assertTrue(Ordering.natural().isOrdered(allItemsPrice), "Filtering menu by price does not work correct!");
//        logout();
    }

    @Test(testName = "Does a single product view match the main one")
    public void IsSingleProductViewMatchTheMainOne() {
//        login();
        Assert.assertTrue(shoppingCartMenu.isShoppingCartButtonVisible(), "Shopping cart menu is not visible!");

        WebElement inventoryItemNameExpected = myAccountPage.items.get(0).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceExpected = myAccountPage.items.get(0).findElement(By.className("inventory_item_price"));
        WebElement inventoryItemDescExpected = myAccountPage.items.get(0).findElement(By.className("inventory_item_desc"));
        String inventoryItemPictureExpected = myAccountPage.items.get(0).findElement(By.tagName("img")).getAttribute("src");

        Item expected = new Item(inventoryItemNameExpected.getText(), inventoryItemPriceExpected.getText(), inventoryItemDescExpected.getText(), inventoryItemPictureExpected);

        SeleniumUtils.clickElement(myAccountPage.items.get(0).findElement(By.className("inventory_item_name")), driver);

//        WebElement inventoryItemNameActual = myAccountPage.inventoryItemContainer.findElement(By.tagName("div"));
//        WebElement inventoryItemPriceActual = myAccountPage.inventoryDetailsContainer.findElement(By.className("inventory_details_price"));
//        WebElement inventoryItemDescActual = myAccountPage.inventoryDetailsContainer.findElement(By.className("inventory_details_desc large_size"));
//        String inventoryItemPictureActual = myAccountPage.inventoryDetailsContainer.findElement(By.className("inventory_details_price")).getAttribute("src");

//        Item actual = new Item(inventoryItemNameActual.getText(), inventoryItemPriceActual.getText(), inventoryItemDescActual.getText(), inventoryItemPictureActual);
//        logout();
//        Assert.assertTrue(expected.equalsForItem(actual), "Single product view does not match with main one!");

    }

    private static String getPrice(String input) {
        String output = "";

        Pattern pattern = Pattern.compile("\\d{1,3}[,\\.]?(\\d{1,2})?");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            output = matcher.group(0);
        }
        return output;
    }

}
