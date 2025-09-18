package com.swaglabs.tests;

import com.swaglabs.testData.TestData;
import com.swaglabs.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;

public class ProductsTests extends BaseTests {
    private LoginPage loginPage = new LoginPage();
    private ProductsPage productsPage = new ProductsPage();

    @Test(description = "Verify products page after login")
    @Story("Products Page Functionality")
    @Description("Verify products page displays correct number of items and allows adding to cart")
    @Severity(SeverityLevel.MINOR)
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
    @Story("Products Page Functionality")
    @Description("Simulate edge case where no products are visible after login")
    @Severity(SeverityLevel.NORMAL)
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