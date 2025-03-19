package org.example.reddit.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class RedditHeaderUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedditHeaderUtils.class);

    private final By LOGOUT_LINK_SELECTOR = By.xpath("//form[@class='logout hover']//a[normalize-space()='logout']");
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

    public void logout(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK_SELECTOR));
            logoutLink.click();
            logger.info("Clicked logout link.");
        } catch (Exception e) {
            logger.error("Error during logout: ", e);
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }


}