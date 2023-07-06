package org.example.stepDefinitions;

import com.google.common.collect.Ordering;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;
import org.example.model.HomePage;
import org.example.model.Item;
import org.example.model.MyAccountPage;
import org.example.model.ShoppingCartMenu;
import org.example.utils.PropertiesHandler;
import org.example.utils.SeleniumUtils;
import org.example.utils.WaitUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class StepDefinitions {
    public static WebDriver driver;
    private HomePage homePage;
    private MyAccountPage myAccountPage;
    private ShoppingCartMenu shoppingCartMenu;
    public static String USER;
    public static String PASSWORD;

    protected void initialisePageObjects() {
        homePage = new HomePage(driver);
        myAccountPage = new MyAccountPage(driver);
        shoppingCartMenu = new ShoppingCartMenu(driver);

    }

    @Given("^User is on Home Page$")
    public void user_is_on_Home_Page() throws IOException {
        PropertiesHandler.loadProperties("config/default-selenium.properties");

        USER = PropertiesHandler.getProperty("app.user");
        PASSWORD = PropertiesHandler.getProperty("app.user.password");

        driver = getDriverInstance(PropertiesHandler.getProperty("default.browser"));
        driver.manage().timeouts().implicitlyWait(Long.parseLong(PropertiesHandler.getProperty("selenium.timeout")), TimeUnit.SECONDS);
        driver.navigate().to(PropertiesHandler.getProperty("app.base.url"));
        driver.manage().window().maximize();
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

    @And("user is log in")
    public void userIsLogIn() {
        initialisePageObjects();
        homePage.setUserName(USER);
        homePage.setPassword(PASSWORD);
        homePage.clickOnLoginButton();
    }

    @And("Inventory Container is visible")
    public void inventoryContainerIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(myAccountPage.inventoryContainer, driver);
        } catch (TimeoutException e) {
            System.out.println("Inventory container is not visible!");
        }
    }

    @Then("Number of items in inventory container are {int}")
    public void numberOfItemsInInventoryContainerAre(int expectedCount) {
        assertEquals(myAccountPage.items.size(), expectedCount, "Unexpected number of items in inventory container!");
    }

    @Then("User is logout")
    public void userIsLogout() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    @And("Product sort container is visible")
    public void productSortContainerIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(myAccountPage.productSortContainer, driver);
        } catch (TimeoutException e) {
            System.out.println("Product sort container is not visible!");
        }
    }

    @And("Select {string} from Product sort container")
    public void selectFromProductSortContainer(String sort) {
        Select drpCountry = new Select(myAccountPage.productSortContainer);
        drpCountry.selectByVisibleText(sort);
    }

    @Then("Product are sorted by name")
    public void productAreSortedByName() {
        List<String> allItemsName = new ArrayList<>();
        for (WebElement webElement : myAccountPage.items) {
            String inventory_item_name = ((RemoteWebElement) webElement).findElementByClassName("inventory_item_name").getText();
            allItemsName.add(getPrice(inventory_item_name));

            assertTrue(Ordering.natural().reverse().isOrdered(allItemsName));
        }
    }

    @Then("Product are sorted by price")
    public void productAreSortedByPrice() {
        List<BigDecimal> allItemsPrice = new ArrayList<>();
        for (WebElement webElement : myAccountPage.items) {
            String inventory_item_name = ((RemoteWebElement) webElement).findElementByClassName("inventory_item_price").getText();
            allItemsPrice.add(new BigDecimal(getPrice(inventory_item_name)));

            assertTrue(Ordering.natural().isOrdered(allItemsPrice));
        }
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

    @And("Fill User Name Button is visible")
    public void userNameButtonIsVisible() {
        initialisePageObjects();
        try {
            WaitUtils.waitForVisibleElement(homePage.myUserNameButton, driver);
        } catch (TimeoutException e) {
            System.out.println("User name button is not visible!");
        }
        SeleniumUtils.populateTextField(homePage.myUserNameButton, USER, driver);
    }

    @And("Fill Password Button is visible")
    public void passwordButtonIsVisible() {
        initialisePageObjects();
        try {
            WaitUtils.waitForVisibleElement(homePage.myPasswordButton, driver);
        } catch (TimeoutException e) {
            System.out.println("Password button is not visible!");
        }
        SeleniumUtils.populateTextField(homePage.myPasswordButton, PASSWORD, driver);
    }

    @And("Click on Log In button")
    public void clickOnLogInButton() {
        SeleniumUtils.clickElement(homePage.loginButton, driver);
    }

    @Then("User is on My Account Page")
    public void userIsOnMyAccountPage() {
        assertTrue(myAccountPage.isUserOnMyAccountPage(), "User is no on My Account page!");
    }

    @Then("Delete Cookies")
    public void deleteCookies() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @And("React Burger menu is visible")
    public void reactBurgerMenuIsVisible() {
        try {
            driver.findElement(By.id("react-burger-menu-btn")).click();
        } catch (TimeoutException e) {
            System.out.println("React Burger menu is not visible!");
        }
    }

    @And("Logout button is visible")
    public void logoutButtonIsVisible() {
        try {
            driver.findElement(By.id("logout_sidebar_link")).click();
        } catch (TimeoutException e) {
            System.out.println("Logout button is not visible!");
        }
    }

    @Then("User is logout successfully")
    public void userIsLogoutSuccessfully() {
        assertTrue(homePage.isUserOnHomePage());
    }

    @And("Shopping cart menu is visible")
    public void shoppingCartMenuIsVisible() {
        try {
            WaitUtils.waitForVisibleElement(shoppingCartMenu.shoppingCartButton, driver);
        } catch (TimeoutException e) {
            System.out.println("Shopping cart menu is not visible!");
        }
    }

    @And("Add one item in shopping cart")
    public void addOneItemInShoppingCart() {
        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(0).findElement(By.id("add-to-cart-sauce-labs-backpack")));
    }

    @Then("In Shopping Cart have one item")
    public void inShoppingCartHaveOneItem() {
        WebElement inventoryItemNameExpected = myAccountPage.items.get(0).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceExpected = myAccountPage.items.get(0).findElement(By.className("inventory_item_price"));
        Item expectedItem = new Item(inventoryItemNameExpected.getText(), inventoryItemPriceExpected.getText());
        shoppingCartMenu.clickOnShoppingCartButton();

        WebElement inventoryItemNameActual = shoppingCartMenu.itemInShoppingCart.get(0).findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        WebElement inventoryItemPriceActual = shoppingCartMenu.itemInShoppingCart.get(0).findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[2]/div"));

        Item actualItem = new Item(inventoryItemNameActual.getText(),inventoryItemPriceActual.getText());

        assertEquals(expectedItem, actualItem);

        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
    }

    @And("Add two item in shopping cart")
    public void addTwoItemInShoppingCart() {
        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(1).findElement(By.id("add-to-cart-sauce-labs-bike-light")));
        shoppingCartMenu.clickOnAddToCartButton(myAccountPage.items.get(2).findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")));
    }

    @Then("In Shopping Cart have two item")
    public void inShoppingCartHaveTwoItem() {
        WebElement inventoryItemNameExpected1 = myAccountPage.items.get(1).findElement(By.className("inventory_item_name"));
        WebElement inventoryItemPriceExpected1 = myAccountPage.items.get(1).findElement(By.className("inventory_item_price"));

        Item expectedItem1 = new Item(inventoryItemNameExpected1.getText(), inventoryItemPriceExpected1.getText());

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

        assertEquals(expectedItem1, actualItem1);
        assertEquals(expectedItem2, actualItem2);

        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")).click();
    }
}
