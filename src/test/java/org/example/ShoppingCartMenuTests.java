package org.example;

import org.example.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import org.example.model.HomePage;
import org.example.model.Item;
import org.example.model.MyAccountPage;
import org.example.model.ShoppingCartMenu;

public class ShoppingCartMenuTests extends TestBase {

    private ShoppingCartMenu shoppingCartMenu;
    private MyAccountPage myAccountPage;
    private HomePage homePage;

    public ShoppingCartMenuTests() throws Throwable {
        super();
        initialisePageObjects();
    }

    protected void initialisePageObjects() {
        shoppingCartMenu = new ShoppingCartMenu(driver);
        myAccountPage = new MyAccountPage(driver);
        homePage = new HomePage(driver);
    }

    public void login() {
        homePage.setUserName(USER);
        homePage.setPassword(PASSWORD);
        homePage.clickOnLoginButton();
    }

    public void logout() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    @Test(testName = "Add one item to shopping cart")
    public void addOneItemToShoppingCart() {
        login();
        Assert.assertTrue(shoppingCartMenu.isShoppingCartButtonVisible(), "Shopping cart menu is not visible!");

        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(0).findElement(By.id("add-to-cart-sauce-labs-backpack")));

        WebElement inventoryItemNameExpected = myAccountPage.items.get(0).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceExpected = myAccountPage.items.get(0).findElement(By.className("inventory_item_price"));
        Item expectedItem = new Item(inventoryItemNameExpected.getText(), inventoryItemPriceExpected.getText());

        shoppingCartMenu.clickOnShoppingCartButton();

        WebElement inventoryItemNameActual = shoppingCartMenu.itemInShoppingCart.get(0).findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        WebElement inventoryItemPriceActual = shoppingCartMenu.itemInShoppingCart.get(0).findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[2]/div"));

        Item actualItem = new Item(inventoryItemNameActual.getText(), inventoryItemPriceActual.getText());

        Assert.assertEquals(expectedItem, actualItem);

        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        deleteCookies();

    }

    @Test(testName = "Add two item to shopping cart")
    public void addTwoItemToShoppingCart() {
        login();
        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(1).findElement(By.id("add-to-cart-sauce-labs-bike-light")));

        WebElement inventoryItemNameExpected1 = myAccountPage.items.get(1).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceExpected1 = myAccountPage.items.get(1).findElement(By.className("inventory_item_price"));

        Item expectedItem1 = new Item(inventoryItemNameExpected1.getText(), inventoryItemPriceExpected1.getText());

        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(2).findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")));
        WebElement inventoryItemNameExpected2 = myAccountPage.items.get(2).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceExpected2 = myAccountPage.items.get(2).findElement(By.className("inventory_item_price"));

        Item expectedItem2 = new Item(inventoryItemNameExpected2.getText(), inventoryItemPriceExpected2.getText());

        shoppingCartMenu.clickOnShoppingCartButton();

        WebElement inventoryItemNameActual1 = shoppingCartMenu.itemInShoppingCart.get(0).findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div"));
        WebElement inventoryItemPriceActual1 = shoppingCartMenu.itemInShoppingCart.get(0).findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[2]/div"));

        Item actualItem1 = new Item(inventoryItemNameActual1.getText(), inventoryItemPriceActual1.getText());

        WebElement inventoryItemNameActual2 = shoppingCartMenu.itemInShoppingCart.get(1).findElement(By.xpath("//*[@id=\"item_1_title_link\"]/div"));
        WebElement inventoryItemPriceActual2 = shoppingCartMenu.itemInShoppingCart.get(1).findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[4]/div[2]/div[2]/div"));

        Item actualItem2 = new Item(inventoryItemNameActual2.getText(), inventoryItemPriceActual2.getText());

        Assert.assertEquals(expectedItem1, actualItem1);
        Assert.assertEquals(expectedItem2, actualItem2);

        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")).click();
        logout();
    }

    @Test(testName = "Removing item from shopping cart")
    public void removingItemFromShoppingCart(){
        login();
        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(4).findElement(By.id("add-to-cart-sauce-labs-onesie")));
        shoppingCartMenu.clickOnShoppingCartButton();
        int sizeBeforeRemoving = myAccountPage.itemsInShoppingCart.size();
        shoppingCartMenu.clickOnRemoveFromCartButton(myAccountPage.itemsInShoppingCart.get(1).findElement(By.id("remove-sauce-labs-onesie")));
        Assert.assertEquals(myAccountPage.itemsInShoppingCart.size(), sizeBeforeRemoving -1 );
        logout();
    }

    @Test(testName = "Finalization of order")
    public void finalizationOfOrder() {
        login();
        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(1).findElement(By.id("add-to-cart-sauce-labs-bike-light")));
        WebElement inventoryItemNameExpected = myAccountPage.items.get(1).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceExpected = myAccountPage.items.get(1).findElement(By.className("inventory_item_price"));

        Item expectedItem = new Item(inventoryItemNameExpected.getText(), inventoryItemPriceExpected.getText());

        shoppingCartMenu.clickOnShoppingCartButton();
        shoppingCartMenu.clickOnCheckoutButton();
        Assert.assertTrue(shoppingCartMenu.isCheckoutInfoIsVisible(), "Checkout info is not visible!");

        SeleniumUtils.populateTextField(shoppingCartMenu.firstName, "Mediha", driver);
        SeleniumUtils.populateTextField(shoppingCartMenu.lastName, "Redzheb", driver);
        SeleniumUtils.populateTextField(shoppingCartMenu.postalCode, "7200", driver);
        shoppingCartMenu.clickOnContinueButton();
        Assert.assertTrue(shoppingCartMenu.isCheckoutSummaryContainerVisible(), "Checkout summary container is not visible!");


        WebElement inventoryItemNameActual = myAccountPage.itemsInShoppingCart.get(0).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceActual = myAccountPage.itemsInShoppingCart.get(0).findElement(By.className("inventory_item_price"));

        Item actualItem = new Item(inventoryItemNameActual.getText(), inventoryItemPriceActual.getText());

        Assert.assertEquals(expectedItem, actualItem, "Something has gone wrong!");
        Assert.assertTrue(shoppingCartMenu.isFinishTheOrderButtonVisible(), "Finish order button is not visible");
        shoppingCartMenu.clickOnFinishTheOrderButton();
        Assert.assertTrue(shoppingCartMenu.isCheckoutCompleteContainerVisible(), "Failed order!");
        deleteCookies();

    }

    @Test(testName = "Incorrect filling a Name field")
    public void incorrectFillingANameFieldThrowErrorMessage() {
        login();
        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(2).findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")));
        shoppingCartMenu.clickOnShoppingCartButton();
        shoppingCartMenu.clickOnCheckoutButton();
        Assert.assertTrue(shoppingCartMenu.isCheckoutInfoIsVisible(), "Checkout info is not visible!");
        SeleniumUtils.populateTextField(shoppingCartMenu.firstName, "", driver);
        SeleniumUtils.populateTextField(shoppingCartMenu.lastName, "Redzheb", driver);
        SeleniumUtils.populateTextField(shoppingCartMenu.postalCode, "7200", driver);

        shoppingCartMenu.clickOnContinueButton();

        Assert.assertTrue(shoppingCartMenu.isErrorButtonCheckoutVisible());
        logout();
    }
}
