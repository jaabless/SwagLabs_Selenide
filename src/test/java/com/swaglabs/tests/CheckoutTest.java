package com.swaglabs.tests;

import com.swaglabs.testData.TestData;
import com.swaglabs.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Test(groups = {"regression"})
public class CheckoutTest extends BaseTests {
    private LoginPage loginPage = new LoginPage();
    private ProductsPage productsPage = new ProductsPage();
    private CartPage cartPage = new CartPage();
    private CheckoutInfoPage infoPage = new CheckoutInfoPage();
    private CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
    private ConfirmationPage confirmationPage = new ConfirmationPage();

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        return new Object[][]{
                {TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE, true}, // Positive
                {TestData.EMPTY_FIELD, TestData.LAST_NAME, TestData.ZIP_CODE, false}, // Negative: Empty first name
                {TestData.FIRST_NAME, TestData.EMPTY_FIELD, TestData.EMPTY_FIELD, false} // Edge: Multiple empty
        };
    }

    @BeforeMethod
    public void setup() {
        // Ensure starting from login page for each test
        loginPage.openLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.PASSWORD);
    }

    @Test(dataProvider = "checkoutData", description = "Test checkout scenarios")
    @Description("Verify checkout process with various form inputs")
    @Story("Checkout Functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckout(String first, String last, String zip, boolean shouldSucceed) {
        productsPage.addProductToCart(TestData.PRODUCT_NAME);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        infoPage.fillCheckoutInfo(first, last, zip);

        if (shouldSucceed) {
            overviewPage.finishCheckout();
            confirmationPage.verifyConfirmation(); // Soft with wait
            logger.info("Checkout successful");
        } else {
            String error = infoPage.getErrorMessage();
            assertTrue(error.contains("Error"), "Expected checkout error"); // Hard assertion
            logger.warn("Checkout failed as expected: {}", error);
        }
    }

    @Test
    @Description("Verify checkout overview displays correct item and total")
    @Story("Checkout Functionality")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutOverview() {
        productsPage.addProductToCart(TestData.PRODUCT_NAME);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        infoPage.fillCheckoutInfo(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.ZIP_CODE);
        String itemName = overviewPage.getItemName();
        String itemTotal = overviewPage.getItemTotal();
        assertTrue(itemName.contains(TestData.PRODUCT_NAME), "Item name mismatch");
        assertTrue(itemTotal.contains("Total"), "Item total missing");
        logger.info("Checkout overview verified");
    }
}