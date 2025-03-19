package org.example.reddit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class RedditGamingSubredditPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(RedditGamingSubredditPage.class);

    // By selectors
    private final By POST_SELECTOR = By.cssSelector("div[data-type='link']:not(.stickied):not(.promotedlink)"); // Selects non-pinned, non-ad posts
    private final By TITLE_SELECTOR = By.cssSelector("a.title"); // Selects the title of a post
    private final By UPVOTE_BUTTON_SELECTOR = By.cssSelector("div.midcol div.arrow.up"); // Selects the upvote button
    private final By DOWNVOTE_BUTTON_SELECTOR = By.cssSelector("div.midcol div.arrow.down"); // Selects the downvote button
    private final By POST_VOTING_DIV = By.cssSelector("div.midcol");


    public RedditGamingSubredditPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait with a 10-second timeout
    }
    public void voteOnPostBasedOnKeyword(int postIndex, String keyword) {
        try {
            // Wait for posts to load
            wait.until(ExpectedConditions.presenceOfElementLocated(POST_SELECTOR));

            // Find all non-pinned, non-ad posts
            List<WebElement> posts = driver.findElements(POST_SELECTOR);
            if (posts.size() < postIndex) {
                logger.warn("Not enough posts found to perform the voting action. Requested index: {}", postIndex);
                return;
            }

            // Get the post at the specified index (adjust for 0-based index)
            WebElement post = posts.get(postIndex - 1);

            // Get the title of the post
            WebElement titleElement = post.findElement(TITLE_SELECTOR);
            String title = titleElement.getText();
            logger.info("Analyzing post title: {}", title);

            // Perform voting based on the title and keyword
            if (title.toLowerCase().contains(keyword.toLowerCase())) {
                handleUpvote(post);
            } else {
                handleDownvote(post);
            }

        } catch (Exception e) {
            logger.error("Error during post voting: ", e);
            throw new RuntimeException("Post voting failed: " + e.getMessage());
        }
    }

    private void handleUpvote(WebElement post) {
        try {

            if (isUpvoted(post)) {
                logger.info("Post is already upvoted. Keeping it as is");
            }
//            else if (isDownvoted(post)){
//                WebElement upvoteButton = post.findElement(UPVOTE_BUTTON_SELECTOR);
//                upvoteButton.click();
//                logger.info("Post is downvoted. Upvoted it");
//            }
            else {
                WebElement upvoteButton = post.findElement(UPVOTE_BUTTON_SELECTOR);
                upvoteButton.click();
                logger.info("Upvoted Post");
            }
        }
        catch (Exception e) {
            logger.error("Error handling upvote: ", e);
            throw new RuntimeException("Upvoting handling failed: " + e.getMessage());
        }
    }

    private void handleDownvote(WebElement post) {
        try {


            if (isDownvoted(post)) {
                logger.info("Post is already downvoted. Keeping it as is");
            }
//            else if (isUpvoted(post)){
//                WebElement downvoteButton = post.findElement(DOWNVOTE_BUTTON_SELECTOR);
//                downvoteButton.click();
//                logger.info("Post is Upvoted. Downvoted it");
//            }
            else {
                WebElement downvoteButton = post.findElement(DOWNVOTE_BUTTON_SELECTOR);
                downvoteButton.click();
                logger.info("Upvoted Post");
            }
        }
        catch (Exception e) {
            logger.error("Error handling upvote: ", e);
            throw new RuntimeException("Upvoting handling failed: " + e.getMessage());
        }
    }

    private boolean isUpvoted(WebElement post) {
        try {
            WebElement votingDiv = post.findElement(POST_VOTING_DIV);  // Find the div.midcol *inside* the post
            String classAttribute = votingDiv.getAttribute("class");

            if (classAttribute != null && classAttribute.contains("likes")) {
                return true;
            } else {
                return false;
            }

        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false; // If the div is not found, it's not upvoted.
        }
    }

    private boolean isDownvoted(WebElement post) {
        try {
            WebElement votingDiv = post.findElement(POST_VOTING_DIV);  // Find the div.midcol *inside* the post
            String classAttribute = votingDiv.getAttribute("class");

            if (classAttribute != null && classAttribute.contains("dislikes")) {
                return true;
            } else {
                return false;
            }

        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false; // If the div is not found, it's not downvoted.
        }
    }
}