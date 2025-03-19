package org.example.reddit.tests;

import org.example.reddit.config.Configuration;
import org.example.reddit.pages.RedditHeaderUtils;
import org.example.reddit.pages.SubredditPage;
import org.example.reddit.pages.RedditLoginPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.time.Duration;

public class RedditVotingTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(RedditVotingTest.class);

    @Test
    public void testRedditVoting() {
        try {
            // 1. Login
            RedditLoginPage loginPage = new RedditLoginPage(driver);
            loginPage.login(Configuration.USERNAME, Configuration.PASSWORD);
            Assert.assertTrue(driver.getCurrentUrl().contains("reddit.com"), "Login failed: URL does not contain 'reddit.com'");

            // 2. Access the gaming section
            WebElement gamingSubreddit = RedditHeaderUtils.getHeaderElementByTitle(driver, Configuration.GAMING_SUBREDDIT); //replace Configuration.GAMING_SUBREDDIT with gaming?
            gamingSubreddit.click();

            // Explicit wait: wait up to 10 seconds for the URL to match
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlToBe("https://old.reddit.com/r/gaming/"));

            // Now retrieve the current URL
            String expectedUrl = "https://old.reddit.com/r/gaming/";
            String actualUrl = driver.getCurrentUrl();

            // TestNG assertion
            Assert.assertEquals(actualUrl, expectedUrl, "URL is not as expected!");


            // 3. Initialize the RedditGamingSubredditPage
            SubredditPage gamingPage = new SubredditPage(driver);

            // 4. Perform the voting action on the second post
            gamingPage.voteOnPostBasedOnKeyword(2, "Nintendo"); // Vote on the second post

            // 5. Logout
            RedditHeaderUtils headerUtils = new RedditHeaderUtils();
            headerUtils.logout(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement initialLoginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(RedditHeaderUtils.getInitialLoginButton()));
            Assert.assertTrue(initialLoginButton.isDisplayed(), "Login button should be visible again after logout.");


        } catch (Exception e) {
            logger.error("An error occurred during the test: ", e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }
}