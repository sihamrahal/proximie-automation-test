package org.example.reddit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SubredditPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By POST_SELECTOR = By.cssSelector("div[data-type='link']:not(.stickied):not(.promotedlink)");
    private final By TITLE_SELECTOR = By.cssSelector("a.title");
    private final By UPVOTE_BUTTON = By.cssSelector("div.midcol div.arrow.up");
    private final By DOWNVOTE_BUTTON = By.cssSelector("div.midcol div.arrow.down");
    private final By VOTING_DIV = By.cssSelector("div.midcol");

    public SubredditPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Votes on a post based on whether its title contains a specific keyword.
     *
     * @param postIndex The index of the post to vote on (1-based).
     * @param keyword   The keyword to check in the post title.
     */
    public void voteOnPostBasedOnKeyword(int postIndex, String keyword) {
        try {
            // Wait for posts to load
            wait.until(ExpectedConditions.presenceOfElementLocated(POST_SELECTOR));

            // Find all non-pinned, non-ad posts
            List<WebElement> posts = driver.findElements(POST_SELECTOR);
            if (posts.size() < postIndex) {
                throw new RuntimeException("Not enough posts found to perform the voting action. Requested index: " + postIndex);
            }

            // Get the post at the specified index (adjust for 0-based index)
            WebElement post = posts.get(postIndex - 1);

            // Get the title of the post
            WebElement titleElement = post.findElement(TITLE_SELECTOR);
            String title = titleElement.getText();

            // Perform voting based on the title and keyword
            if (title.toLowerCase().contains(keyword.toLowerCase())) {
                handleUpvote(post);
            } else {
                handleDownvote(post);
            }

        } catch (Exception e) {
            throw new RuntimeException("Post voting failed: " + e.getMessage());
        }
    }

    /**
     * Handles the upvote action on a post.
     * If the post is already upvoted, it skips the action.
     * If the post is downvoted or not voted, it votes up.
     *
     * @param post The post element to upvote.
     */
    private void handleUpvote(WebElement post) {
        WebElement votingDiv = post.findElement(VOTING_DIV);
        String initialClass = votingDiv.getAttribute("class");

        // Skip if already upvoted
        if (initialClass.contains("likes")) {
            System.out.println("Post is already upvoted. Skipping upvote.");
            return;
        }
        else {         post.findElement(UPVOTE_BUTTON).click();
}

        // Verify the post is now upvoted
        String finalClass = votingDiv.getAttribute("class");
        Assert.assertTrue(finalClass.contains("likes"), "Upvote failed: Post was not upvoted successfully.");
    }

    /**
     * Handles the downvote action on a post.
     * If the post is already downvoted, it skips the action.
     * If the post is upvoted or not voted, it votes down.
     *
     * @param post The post element to downvote.
     */
    private void handleDownvote(WebElement post) {
        WebElement votingDiv = post.findElement(VOTING_DIV);
        String initialClass = votingDiv.getAttribute("class");

        // Skip if already downvoted
        if (initialClass.contains("dislikes")) {
            System.out.println("Post is already downvoted. Skipping downvote.");
            return;
        }
        else {
            post.findElement(DOWNVOTE_BUTTON).click();

        }

        // Verify the post is now downvoted
        String finalClass = votingDiv.getAttribute("class");
        Assert.assertTrue(finalClass.contains("dislikes"), "Downvote failed: Post was not downvoted successfully.");
    }
}