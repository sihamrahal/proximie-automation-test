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

    // Locator for the final "Login" button within the login form
    private final By finalLoginButton = By.className("login");

    // Locators for the Shadow DOM hosts
    private final By shadowHostLocatorUsername = By.id("login-username");  // Verify this is correct
    private final By shadowHostLocatorPassword = By.id("login-password");  // Verify this is correct

    // Fields inside the Shadow DOM
    private final By usernameFieldInShadow = By.name("username");
    private final By passwordFieldInShadow = By.name("password");

    public RedditLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Utility method for finding elements within a Shadow DOM.
     */
    private WebElement findElementInShadowDOM(By shadowHostLocator, By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(shadowHostLocator));

        SearchContext shadowRoot = (SearchContext) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].shadowRoot", shadowHost);

        return shadowRoot.findElement(elementLocator);
    }

    /**
     * Enters the provided username and password into the Shadow DOM fields,
     * then clicks the final login button.
     */
    public void login(String username, String password) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Click the initial login button using the getter from RedditHeaderUtils
            WebElement initialLoginButton = wait.until(ExpectedConditions.elementToBeClickable(RedditHeaderUtils.getInitialLoginButton()));
            initialLoginButton.click();

            // Locate and fill the shadow DOM fields
            WebElement usernameElement = findElementInShadowDOM(shadowHostLocatorUsername, usernameFieldInShadow);
            WebElement passwordElement = findElementInShadowDOM(shadowHostLocatorPassword, passwordFieldInShadow);

            usernameElement.sendKeys(username);
            passwordElement.sendKeys(password);

            // Click the final login button
            WebElement loginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(finalLoginButton));
            loginButtonElement.click();
        } catch (Exception e) {
            logger.error("Error during login: ", e);
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}