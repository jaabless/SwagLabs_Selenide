package com.swaglabs.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CheckoutOverviewPage extends BasePage {
    private SelenideElement finishButton = $("#finish");

    public void finishCheckout() {
        finishButton.click();
        logger.info("Finished checkout");
    }

    public String getTotal() {
        return $(".summary_total_label").getText();
    }
}
