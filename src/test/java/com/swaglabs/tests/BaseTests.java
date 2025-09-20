package com.swaglabs.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.swaglabs.utils.LoggerUtil;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTests {
    protected Logger logger = LoggerUtil.getLogger(this.getClass());

    @BeforeSuite(alwaysRun = true)
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

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)        // ✅ attach screenshots
                        .savePageSource(true)); // ✅ attach page source

        // Open AFTER everything is configured
        open("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
        logger.info("Closed browser");
    }
}