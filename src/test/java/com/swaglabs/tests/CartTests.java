package com.swaglabs.tests;

import com.swaglabs.testData.TestData;
import com.swaglabs.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseTests {
    private LoginPage loginPage = new LoginPage();
    private ProductsPage productsPage = new ProductsPage();
    private CartPage cartPage = new CartPage();

    @Test()
    @Description("Verify adding an item to the cart updates the cart count")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddToCart() {
        loginPage.openLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.PASSWORD);
        productsPage.addProductToCart(TestData.PRODUCT_NAME);
        productsPage.goToCart();
        cartPage.verifyProductInCart(TestData.PRODUCT_NAME); // Soft with wait
        logger.info("Cart verified");
    }

    @Test
    @Description("Verify checkout with empty cart does not proceed")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.MINOR)
    public void testEmptyCartNegative() {
        loginPage.openLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.PASSWORD);
        productsPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty"); // Hard
        // Attempt checkout (should stay on cart, no error but log)
        cartPage.proceedToCheckout();
        cartPage.verifyUrl(TestData.BASE_URL + "cart.html"); // Should not proceed
        logger.warn("Negative: Attempted checkout with empty cart");
    }

    @Test(description = "Edge: Remove item from cart")
    @Description("Verify removing an item from the cart updates the cart count")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveFromCartEdge() {
        loginPage.openLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.PASSWORD);
        productsPage.addProductToCart(TestData.EDGE_PRODUCT);
        productsPage.goToCart();
        cartPage.removeProduct(TestData.EDGE_PRODUCT);
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty after remove");
        logger.info("Edge: Removed item successfully");
    }
}
