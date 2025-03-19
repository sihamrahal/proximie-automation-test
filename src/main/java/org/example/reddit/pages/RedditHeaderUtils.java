package org.example.reddit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class RedditHeaderUtils {
    // The "initial login" button shown before you're logged in
    private static final By INITIAL_LOGIN_BUTTON = By.cssSelector("a.login-required.login-link");

    // The logout link in the header
    private static final By LOGOUT_LINK_SELECTOR = By.xpath("//form[@class='logout hover']//a[normalize-space()='logout']");

    /**
     * Returns a clickable header element by its visible text.
     * Example usage: getHeaderElementByTitle(driver, "gaming")
     */
    public static WebElement getHeaderElementByTitle(WebDriver driver, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@class='choice' and text()='" + text + "']")
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to locate header element with text: " + text, e);
        }
    }

    /**
     * Logs the user out by clicking the logout link in the header.
     */
    public void logout(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK_SELECTOR));
            logoutLink.click();
        } catch (Exception e) {
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }

    // Getter method for INITIAL_LOGIN_BUTTON
    public static By getInitialLoginButton() {
        return INITIAL_LOGIN_BUTTON;
    }
}