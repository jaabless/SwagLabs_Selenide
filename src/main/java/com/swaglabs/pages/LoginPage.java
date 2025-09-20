package com.swaglabs.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    private SelenideElement usernameInput = $("#user-name");
    private SelenideElement passwordInput = $("#password");
    public SelenideElement loginButton = $("#login-button");

    public void login(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        logger.info("Attempted login with username: {}", username);
    }

    public void openLoginPage() {
        navigateTo("https://www.saucedemo.com/");
    }

    public boolean isLoginPageDisplayed() {
        return loginButton.isDisplayed() && usernameInput.isDisplayed() && passwordInput.isDisplayed();
    }
}
