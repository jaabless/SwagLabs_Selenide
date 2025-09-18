package com.swaglabs.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class ConfirmationPage extends BasePage {
    private static final String CONFIRMATION_TEXT = "Thank you for your order!";

    public void verifyConfirmation() {
        $(".complete-header").shouldHave(text(CONFIRMATION_TEXT));
        logger.info("Verified order confirmation");
    }
}