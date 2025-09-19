package com.swaglabs.testData;

public class TestData {
    public static final String BASE_URL = "https://www.saucedemo.com/";
    public static final String VALID_USERNAME = "standard_user";
    public static final String LOCKED_USERNAME = "locked_out_user";
    public static final String PROBLEM_USERNAME = "problem_user";
    public static final String PERFORMANCE_USERNAME = "performance_glitch_user";
    public static final String ERROR_USERNAME = "error_user"; // Hypothetical for error simulation
    public static final String VISUAL_USERNAME = "visual_user"; // Hypothetical for visual testing
    public static final String INVALID_USERNAME = "invalid_user";
    public static final String PASSWORD = "secret_sauce";
    public static final String INVALID_PASSWORD = "wrong_pass";
    public static final String SPECIAL_CHAR_PASSWORD = "!@#$%^&*()";

    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String ZIP_CODE = "12345";
    public static final String EMPTY_FIELD = "";

    public static final String PRODUCT_NAME = "Sauce Labs Backpack"; // For add to cart
    public static final String EDGE_PRODUCT = "Sauce Labs Onesie"; // For edge cases like low stock simulation if needed

    // DataProvider methods can be here if needed, but placed in tests for simplicity
}