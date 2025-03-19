package org.example.reddit.tests;

import org.example.reddit.config.Configuration;
import org.example.reddit.pages.RedditHeaderUtils;
import org.example.reddit.pages.RedditGamingSubredditPage;
import org.example.reddit.pages.RedditLoginPage;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.Assert;

public class RedditVotingTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(RedditVotingTest.class);

    @Test
    public void testRedditVoting() {
        try {
            // 1. Login
            RedditLoginPage loginPage = new RedditLoginPage(driver);
            loginPage.login(Configuration.USERNAME, Configuration.PASSWORD);
            logger.info("Login successful.");
            Assert.assertTrue(driver.getCurrentUrl().contains("reddit.com"), "Login failed: URL does not contain 'reddit.com'");

            // 2. Access the gaming section
            WebElement gamingSubreddit = RedditHeaderUtils.getHeaderElementByTitle(driver, Configuration.GAMING_SUBREDDIT); //replace Configuration.GAMING_SUBREDDIT with gaming?
            gamingSubreddit.click();
            logger.info("Navigated to the gaming subreddit.");

            // 3. Initialize the RedditGamingSubredditPage
            RedditGamingSubredditPage gamingPage = new RedditGamingSubredditPage(driver);

            // 4. Perform the voting action on the second post
            gamingPage.voteOnPostBasedOnKeyword(2, "test"); // Vote on the second post
            logger.info("Voting action completed on the second post.");
            Thread.sleep(3000);

            // 5. Logout
            RedditHeaderUtils redditHeaderUtils = new RedditHeaderUtils();
            redditHeaderUtils.logout(driver);
            logger.info("Logged out successfully.");


        } catch (Exception e) {
            logger.error("An error occurred during the test: ", e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }
}