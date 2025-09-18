package com.swaglabs.tests;

import com.swaglabs.testData.TestData;
import com.swaglabs.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;

public class ProductsTests extends BaseTests {
    private LoginPage loginPage = new LoginPage();
    private ProductsPage productsPage = new ProductsPage();

    @Test(description = "Verify products page after login")
    public void testProductsPage() {
        loginPage.openLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.PASSWORD);
        int count = productsPage.getProductCount();
        Assert.assertEquals(count, 6, "Expected 6 products"); // Hard assertion
        productsPage.addProductToCart(TestData.PRODUCT_NAME); // Positive
//        productsPage.cartButon.shouldBe(visible); // Soft
        Assert.assertTrue(productsPage.isCartButtonVisible());
        logger.info("Products page verified, added item");
    }

    @Test(description = "Edge: No products visible (simulated invalid state)")
    public void testEmptyProductsEdge() {
        // Assuming problem_user for glitchy behavior
        loginPage.openLoginPage();
        loginPage.login(TestData.PROBLEM_USERNAME, TestData.PASSWORD);
        int count = productsPage.getProductCount();
        Assert.assertEquals(count, 6, "Expected 6 products"); // Hard assertion
//        productsPage.productItems.shouldHaveSize(6); // Wait and soft assert size
        // Negative: Try adding non-existent product (would fail naturally in real, but log)
        try {
            productsPage.addProductToCart("NonExistent");
            Assert.fail("Should not add non-existent product");
        } catch (Exception e) {
            logger.info("Edge case: Non-existent product handled");
        }
    }
}