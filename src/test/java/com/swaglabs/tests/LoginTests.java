package com.swaglabs.tests;

import com.swaglabs.testData.TestData;
import com.swaglabs.testData.TestData;
import com.swaglabs.pages.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class LoginTests extends BaseTests {
    private LoginPage loginPage = new LoginPage();
    private ProductsPage productsPage = new ProductsPage();

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {TestData.VALID_USERNAME, TestData.PASSWORD, true, ""}, // Positive
                {TestData.LOCKED_USERNAME, TestData.PASSWORD, false, "Epic sadface: Sorry, this user has been locked out."}, // Negative: Locked
                {TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD, false, "Epic sadface: Username and password do not match any user in this service"}, // Negative: Invalid
                {TestData.EMPTY_FIELD, TestData.PASSWORD, false, "Epic sadface: Username is required"} ,// Edge: Empty username
                {TestData.VALID_USERNAME, TestData.EMPTY_FIELD, false, "Epic sadface: Password is required"} ,// Edge: Empty password
                {TestData.VALID_USERNAME, TestData.SPECIAL_CHAR_PASSWORD, false, "Epic sadface: Username and password do not match any user in this service"} // Edge: Special chars in password
        };
    }

    @Test(dataProvider = "loginData", description = "Test login scenarios")
    public void testLogin(String username, String password, boolean shouldSucceed, String expectedError) {
//        loginPage.openLoginPage();
        loginPage.login(username, password);

        if (shouldSucceed) {
            productsPage.verifyUrl(TestData.BASE_URL + "inventory.html");
//            productsPage.cartButton.shouldBe(visible); // Soft assertion with wait
            Assert.assertTrue(productsPage.isCartButtonVisible());
            logger.info("Login successful");
        } else {
            String error = loginPage.getErrorMessage();
            Assert.assertEquals(error,expectedError, "Expected error message"); // Hard assertion
            logger.warn("Login failed as expected: {}", error);
        }
    }
}
