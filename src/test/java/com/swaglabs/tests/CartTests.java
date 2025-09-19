package com.swaglabs.tests;

import com.swaglabs.testData.TestData;
import com.swaglabs.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTests extends BaseTests {
    private LoginPage loginPage = new LoginPage();
    private ProductsPage productsPage = new ProductsPage();
    private CartPage cartPage = new CartPage();

    @BeforeMethod
    public void setup() {
        // Ensure starting from login page for each test
        loginPage.openLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.PASSWORD);
    }

    @Test()
    @Description("Verify adding an item to the cart show in cart")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddToCart() {
        productsPage.addProductToCart(TestData.PRODUCT_NAME);
        productsPage.goToCart();
        cartPage.verifyProductInCart(TestData.PRODUCT_NAME); // Soft with wait
        logger.info("Product in Cart");
    }

    @Test
    @Description("Verify checkout with empty cart does not proceed")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.MINOR)
    public void testEmptyCartNegative() {
        productsPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty"); // Hard
        // Attempt checkout (should stay on cart, no error but log)
        cartPage.proceedToCheckout();
        cartPage.verifyUrl(TestData.BASE_URL + "cart.html"); // Should not proceed
        logger.warn("Negative: Attempted checkout with empty cart");
    }

    @Test
    @Description("Verify adding an item to the cart updates the cart count")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddToCartEdge() {
        productsPage.addProductToCart(TestData.PRODUCT_NAME);
        productsPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart count should be 1 after add"); // Hard
        logger.info("Edge: Added item successfully");
    }

    @Test(description = "Edge: Remove item from cart")
    @Description("Verify removing an item from the cart updates the cart count")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveFromCartEdge() {
        productsPage.addProductToCart(TestData.EDGE_PRODUCT);
        productsPage.goToCart();
        cartPage.removeProduct(TestData.EDGE_PRODUCT);
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty after remove");
        logger.info("Edge: Removed item successfully");
    }

    @Test
    @Description("Verify proceeding to checkout from the cart navigates to the checkout information page")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    public void testProceedToCheckout() {
        productsPage.addProductToCart(TestData.PRODUCT_NAME);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        cartPage.verifyUrl(TestData.BASE_URL + "checkout-step-one.html"); // Soft with wait
        logger.info("Proceeded to Checkout Information Page");
    }

    @Test
    @Description("Verify removing an item from the cart updates the cart count")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    public void testAddAndRemoveFromCart() {
        productsPage.addProductToCart(TestData.PRODUCT_NAME);
        productsPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart count should be 1 after add"); // Hard
        // Remove item
        cartPage.removeProduct(TestData.PRODUCT_NAME);
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty after remove");
        logger.info("Added and Removed item successfully");
        // Negative: Try remove when empty (no action, but verify no error)
    }

    @Test
    @Description("Verify checkout button is disabled with empty cart")
    @Story("Cart Functionality")
    @Severity(SeverityLevel.MINOR)
    public void testCheckoutButtonDisabledWithEmptyCart() {
        productsPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty"); // Hard
        // Verify that checkout button is disabled
        Assert.assertFalse(cartPage.isCheckoutButtonEnabled(), "Checkout button should be disabled with empty cart");
        logger.info("Verified checkout button is disabled with empty cart");
    }

}
