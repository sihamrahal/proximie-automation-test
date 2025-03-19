////package org.example.reddit.pages;
////
////import org.openqa.selenium.By;
////import org.openqa.selenium.WebDriver;
////import org.openqa.selenium.WebElement;
////import org.openqa.selenium.support.ui.ExpectedConditions;
////import org.openqa.selenium.support.ui.WebDriverWait;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////
////import java.time.Duration;
////import java.util.List;
////import java.util.NoSuchElementException;
////
////public class SubredditPage {
////
////    private final WebDriver driver;
////    private final WebDriverWait wait;
////    private static final Logger logger = LoggerFactory.getLogger(SubredditPage.class);
////
////    // By selectors
////    private final String POST_XPATH_TEMPLATE = "(//div[@data-type='link' and not(contains(@class,'stickied')) and not(contains(@class,'promotedlink'))])[%d]";
////    private final By TITLE_SELECTOR = By.cssSelector("a.title"); // Selects the title of a post
////    private final By UPVOTE_BUTTON_SELECTOR = By.cssSelector("div.midcol div.arrow.up"); // Selects the upvote button
////    private final By DOWNVOTE_BUTTON_SELECTOR = By.cssSelector("div.midcol div.arrow.down"); // Selects the downvote button
////    private final By POST_VOTING_DIV = By.cssSelector("div.midcol");
////
////
////    public SubredditPage(WebDriver driver) {
////        this.driver = driver;
////        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait with a 10-second timeout
////    }
////    public void voteOnPostBasedOnKeyword(int postIndex, String keyword) {
////        try {
////            // Construct the XPath using the desired postIndex (1-based).
////            String postXPath = String.format(POST_XPATH_TEMPLATE, postIndex);
////
////            // Wait for the specific Nth element to appear before interacting.
////            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(postXPath)));
////
////            // Retrieve the Nth post directly using the constructed XPath.
////            WebElement post = driver.findElement(By.xpath(postXPath));
////
////            // From here, the logic is the same: find the title element, then upvote/downvote based on the keyword.
////            WebElement titleElement = post.findElement(TITLE_SELECTOR);
////            String title = titleElement.getText();
////
////            if (title.toLowerCase().contains(keyword.toLowerCase())) {
////                handleUpvote(post);
////            } else {
////                handleDownvote(post);
////            }
////        } catch (Exception e) {
////            logger.error("Error during post voting: ", e);
////            throw new RuntimeException("Post voting failed: " + e.getMessage());
////        }
////    }
////
////    public void handleUpvote(WebElement post) {
////        if (!isUpvoted(post)) {
////            post.findElement(UPVOTE_BUTTON_SELECTOR).click();
////            new WebDriverWait(driver, Duration.ofSeconds(5))
////                    .until(d -> isUpvoted(post));
////        }
////    }
////
////    public void handleDownvote(WebElement post) {
////        if (!isDownvoted(post)) {
////            post.findElement(DOWNVOTE_BUTTON_SELECTOR).click();
////            new WebDriverWait(driver, Duration.ofSeconds(5))
////                    .until(d -> isDownvoted(post));
////        }
////    }
////
////    private boolean isUpvoted(WebElement post) {
////        try {
////            WebElement votingDiv = post.findElement(POST_VOTING_DIV);
////            String classAttr = votingDiv.getAttribute("class");
////            return classAttr != null && classAttr.contains("likes");
////        } catch (NoSuchElementException e) {
////            return false;
////        }
////    }
////
////    private boolean isDownvoted(WebElement post) {
////        try {
////            WebElement votingDiv = post.findElement(POST_VOTING_DIV);
////            String classAttr = votingDiv.getAttribute("class");
////            return classAttr != null && classAttr.contains("dislikes");
////        } catch (NoSuchElementException e) {
////            return false;
////        }
////    }
////}
//
//package org.example.reddit.pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.time.Duration;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//public class SubredditPage {
//
//    private final WebDriver driver;
//    private final WebDriverWait wait;
//    private static final Logger logger = LoggerFactory.getLogger(SubredditPage.class);
//
//    // By selectors
//    private final By POST_SELECTOR = By.cssSelector("div[data-type='link']:not(.stickied):not(.promotedlink)"); // Selects non-pinned, non-ad posts
//    private final By TITLE_SELECTOR = By.cssSelector("a.title"); // Selects the title of a post
//    private final By UPVOTE_BUTTON_SELECTOR = By.cssSelector("div.midcol div.arrow.up"); // Selects the upvote button
//    private final By DOWNVOTE_BUTTON_SELECTOR = By.cssSelector("div.midcol div.arrow.down"); // Selects the downvote button
//    private final By POST_VOTING_DIV = By.cssSelector("div.midcol");
//
//
//    public SubredditPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait with a 10-second timeout
//    }
//    public void voteOnPostBasedOnKeyword(int postIndex, String keyword) {
//        try {
//            // Wait for posts to load
//            wait.until(ExpectedConditions.presenceOfElementLocated(POST_SELECTOR));
//
//            // Find all non-pinned, non-ad posts
//            List<WebElement> posts = driver.findElements(POST_SELECTOR);
//            if (posts.size() < postIndex) {
//                logger.warn("Not enough posts found to perform the voting action. Requested index: {}", postIndex);
//                return;
//            }
//
//            // Get the post at the specified index (adjust for 0-based index)
//            WebElement post = posts.get(postIndex - 1);
//
//            // Get the title of the post
//            WebElement titleElement = post.findElement(TITLE_SELECTOR);
//            String title = titleElement.getText();
//
//            // Perform voting based on the title and keyword
//            if (title.toLowerCase().contains(keyword.toLowerCase())) {
//                handleUpvote(post);
//            } else {
//                handleDownvote(post);
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Post voting failed: " + e.getMessage());
//        }
//    }
//
//    public void handleUpvote(WebElement post) {
//        if (!isUpvoted(post)) {
//            post.findElement(UPVOTE_BUTTON_SELECTOR).click();
//            new WebDriverWait(driver, Duration.ofSeconds(5))
//                    .until(d -> isUpvoted(post));
//        }
//    }
//
//    public void handleDownvote(WebElement post) {
//        if (!isDownvoted(post)) {
//            post.findElement(DOWNVOTE_BUTTON_SELECTOR).click();
//            new WebDriverWait(driver, Duration.ofSeconds(5))
//                    .until(d -> isDownvoted(post));
//        }
//    }
//
//    private boolean isUpvoted(WebElement post) {
//        try {
//            WebElement votingDiv = post.findElement(POST_VOTING_DIV);
//            String classAttr = votingDiv.getAttribute("class");
//            return classAttr != null && classAttr.contains("likes");
//        } catch (NoSuchElementException e) {
//            return false;
//        }
//    }
//
//    private boolean isDownvoted(WebElement post) {
//        try {
//            WebElement votingDiv = post.findElement(POST_VOTING_DIV);
//            String classAttr = votingDiv.getAttribute("class");
//            return classAttr != null && classAttr.contains("dislikes");
//        } catch (NoSuchElementException e) {
//            return false;
//        }
//    }
//}

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
     *
     * @param post The post element to upvote.
     */
    private void handleUpvote(WebElement post) {
        WebElement votingDiv = post.findElement(VOTING_DIV);
        String initialClass = votingDiv.getAttribute("class");

        // Skip if already upvoted
        if (initialClass.contains("likes")) {
            return;
        }

        // Perform the upvote
        post.findElement(UPVOTE_BUTTON).click();

        // Wait for the voting state to update
        wait.until(driver -> {
            String updatedClass = votingDiv.getAttribute("class");
            return updatedClass.contains("likes");
        });

        // Assert that the post is now upvoted
        String finalClass = votingDiv.getAttribute("class");
        Assert.assertTrue(finalClass.contains("likes"), "Post was not upvoted successfully.");
    }

    /**
     * Handles the downvote action on a post.
     *
     * @param post The post element to downvote.
     */
    private void handleDownvote(WebElement post) {
        WebElement votingDiv = post.findElement(VOTING_DIV);
        String initialClass = votingDiv.getAttribute("class");

        // Skip if already downvoted
        if (initialClass.contains("dislikes")) {
            return;
        }

        // Perform the downvote
        post.findElement(DOWNVOTE_BUTTON).click();

        // Wait for the voting state to update
        wait.until(driver -> {
            String updatedClass = votingDiv.getAttribute("class");
            return updatedClass.contains("dislikes");
        });

        // Assert that the post is now downvoted
        String finalClass = votingDiv.getAttribute("class");
        Assert.assertTrue(finalClass.contains("dislikes"), "Post was not downvoted successfully.");
    }
}