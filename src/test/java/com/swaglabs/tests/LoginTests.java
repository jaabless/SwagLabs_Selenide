package com.swaglabs.tests;

import com.swaglabs.testData.TestData;
import com.swaglabs.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@Test(groups = {"smoke"})
public class LoginTests extends BaseTests {
    private LoginPage loginPage = new LoginPage();
    private ProductsPage productsPage = new ProductsPage();

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {TestData.VALID_USERNAME, TestData.PASSWORD, true, ""}, // Positive
                {TestData.PERFORMANCE_USERNAME, TestData.PASSWORD, true, ""}, // Positive
                {TestData.ERROR_USERNAME, TestData.PASSWORD, true, ""}, // Positive
                {TestData.VISUAL_USERNAME, TestData.PASSWORD, true, ""}, // Positive
                {TestData.LOCKED_USERNAME, TestData.PASSWORD, false, "Epic sadface: Sorry, this user has been locked out."}, // Negative: Locked
                {TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD, false, "Epic sadface: Username and password do not match any user in this service"}, // Negative: Invalid
                {TestData.EMPTY_FIELD, TestData.PASSWORD, false, "Epic sadface: Username is required"} ,// Edge: Empty username
                {TestData.VALID_USERNAME, TestData.EMPTY_FIELD, false, "Epic sadface: Password is required"} ,// Edge: Empty password
                {TestData.VALID_USERNAME, TestData.SPECIAL_CHAR_PASSWORD, false, "Epic sadface: Username and password do not match any user in this service"} // Edge: Special chars in password
        };
    }
    @BeforeMethod
    public void setup() {
        // Ensure starting from login page for each test
        loginPage.openLoginPage();
    }

    @Test
    @Story("Login Functionality")
    @Description("Verify login page displays form correctly")
    @Severity(SeverityLevel.MINOR)
    public void testLoginPageFormDisplay() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page form not displayed correctly");
    }

    @Test(dataProvider = "loginData", description = "Test login scenarios")
    @Story("Login Functionality")
    @Description("Verify login functionality with various credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogin(String username, String password, boolean shouldSucceed, String expectedError) {
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
