package com.swaglabs.pages;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPage extends BasePage {
    private ElementsCollection cartItems = $$(".cart_item");
    private SelenideElement checkoutButton = $("#checkout");

    public void verifyProductInCart(String productName) {
        cartItems.findBy(text(productName)).shouldBe(visible);
        logger.info("Verified product in cart: {}", productName);
    }

    public void proceedToCheckout() {
        checkoutButton.click();
        logger.info("Proceeded to checkout");
    }

    public void removeProduct(String productName) {
        cartItems.findBy(text(productName)).$(".btn_secondary").click();
        logger.info("Removed product: {}", productName);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public boolean isCheckoutButtonEnabled() {
        return checkoutButton.isEnabled();
    }
}