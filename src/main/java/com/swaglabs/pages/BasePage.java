package com.swaglabs.pages;

import com.codeborne.selenide.SelenideElement;
import com.swaglabs.utils.LoggerUtil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public abstract class BasePage {
    protected Logger logger = LoggerUtil.getLogger(this.getClass());

    protected SelenideElement errorMessage = $(".error-message-container");

    public void navigateTo(String url) {
        open(url);
        logger.info("Navigated to: {}", url);
    }

    public void verifyUrl(String expectedUrl) {
        webdriver().shouldHave(url(expectedUrl));
        logger.info("Verified URL: {}", expectedUrl);
    }

    public String getErrorMessage() {
        return errorMessage.shouldBe(visible).getText();
    }
}
