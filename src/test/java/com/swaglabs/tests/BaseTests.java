package com.swaglabs.tests;

import com.codeborne.selenide.Configuration;
import com.swaglabs.utils.LoggerUtil;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTests {
    protected Logger logger = LoggerUtil.getLogger(this.getClass());

    @BeforeMethod
    public void setUp() {
        Configuration.browser = System.getProperty("selenide.browser", "chrome");
//        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true")); // Default headless
        Configuration.timeout = 10000; // 10s wait
        Configuration.reportsFolder = "target/selenide-reports"; // For screenshots
        logger.info("Starting test in {} browser, headless: {}", Configuration.browser, Configuration.headless);
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
        logger.info("Closed browser");
    }
}