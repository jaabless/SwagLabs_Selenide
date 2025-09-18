package com.swaglabs.tests;

import com.codeborne.selenide.Configuration;
import com.swaglabs.utils.LoggerUtil;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTests {
    protected Logger logger = LoggerUtil.getLogger(this.getClass());

//    @BeforeMethod
//    public void setUp() {
//        Configuration.browser = "chrome";
//        Configuration.headless = true;
//        Configuration.browserSize = "1920x1080";
//        Configuration.timeout = 5000;
////        Configuration.baseUrl = "https://www.saucedemo.com";
//        open("https://www.saucedemo.com/");
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--remote-allow-origins=*");
//
//        Configuration.browserCapabilities = options;
//
////        Configuration.browser = System.getProperty("selenide.browser", "chrome");
////        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true")); // Default headless
////        Configuration.timeout = 10000; // 10s wait
//        Configuration.reportsFolder = "target/selenide-reports"; // For screenshots
////        Configuration.baseUrl = "https://www.saucedemo.com";
//        logger.info("Starting test in {} browser, headless: {}", Configuration.browser, Configuration.headless);
//    }

    @BeforeMethod
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000;
        Configuration.reportsFolder = "target/selenide-reports";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new"); // <-- more stable in Docker

        Configuration.browserCapabilities = options;

        logger.info("Starting test in {} browser, headless: {}", Configuration.browser, Configuration.headless);

        // Open AFTER everything is configured
        open("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
        logger.info("Closed browser");
    }
}