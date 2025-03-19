package org.example.reddit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class RedditLoginPage {

    private final WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(RedditLoginPage.class);

    // Locator for the initial "Login" button
    private final By initialLoginButton =  By.cssSelector("a.login-required.login-link");
    private final By finalLoginButton = By.className("login");

    // Locators for Shadow DOM and elements within
    private final By shadowHostLocatorUsername = By.id("login-username");  // Replace with actual selector
    private final By shadowHostLocatorPassword = By.id("login-password");   // Replace with actual selector
    private final By shadowHostLocatorLogin = By.id("login");  // Replace with actual selector
    private final By usernameFieldInShadow = By.name("username"); //Locators inside the shadow DOM. Double Check these.
    private final By passwordFieldInShadow = By.name("password");
    private final By loginButtonInShadow = By.cssSelector("button.login");


    public RedditLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //  method to find elements within a Shadow DOM
    private WebElement findElementInShadowDOM(By shadowHostLocator, By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(shadowHostLocator));

        SearchContext shadowRoot = (SearchContext) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].shadowRoot", shadowHost);

        return shadowRoot.findElement(elementLocator);
    }

    public void login(String username, String password) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 1. Click the initial "Login" button
            WebElement initialLoginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(initialLoginButton));
            initialLoginButtonElement.click();
            logger.info("Clicked initial login button.");

            // Use the findElementInShadowDOM method to locate elements
            WebElement usernameElement = findElementInShadowDOM(shadowHostLocatorUsername,usernameFieldInShadow);
            usernameElement.sendKeys(username);
            logger.info("Entered username: {}", username);

            WebElement passwordElement = findElementInShadowDOM(shadowHostLocatorPassword,passwordFieldInShadow);
            passwordElement.sendKeys(password);
            logger.info("Entered password.");

            WebElement loginButtonElement = driver.findElement(finalLoginButton);
            loginButtonElement.click();
            logger.info("Clicked login button.");

        } catch (Exception e) {
            logger.error("Error during login: ", e);
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}