package com.swaglabs.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CheckoutInfoPage extends BasePage {
    private SelenideElement firstNameInput = $("#first-name");
    private SelenideElement lastNameInput = $("#last-name");
    private SelenideElement zipInput = $("#postal-code");
    private SelenideElement continueButton = $("#continue");

    public void fillCheckoutInfo(String firstName, String lastName, String zip) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        zipInput.setValue(zip);
        continueButton.click();
        logger.info("Filled checkout info: {} {} {}", firstName, lastName, zip);
    }
}
