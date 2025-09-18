package com.swaglabs.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage extends BasePage {
    private ElementsCollection productItems = $$(".inventory_item");
    private SelenideElement cartButton = $(".shopping_cart_link");

    public void addProductToCart(String productName) {
        productItems.findBy(text(productName)).$(".btn_inventory").click();
        logger.info("Added product to cart: {}", productName);
    }

    public void goToCart() {
        cartButton.click();
        logger.info("Navigated to cart");
    }

    public int getProductCount() {
        return productItems.size();
    }

    public boolean isCartButtonVisible(){
        return cartButton.isDisplayed();
    }


}
