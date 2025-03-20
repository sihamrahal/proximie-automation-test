package org.example.reddit.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.example.reddit.config.Configuration;
import org.example.reddit.pages.RedditHeaderUtils;
import org.example.reddit.pages.SubredditPage;
import org.example.reddit.pages.RedditLoginPage;
import org.example.reddit.utils.ExtentReportManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class RedditVotingTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(RedditVotingTest.class);
    private ExtentTest test;

    @BeforeMethod
    public void setUp() {
        // Initialize the test in the report
        test = ExtentReportManager.createTest("Reddit Voting Test");
    }

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

            // 4. Perform the voting action on the second post based on the keyword "Nintendo"
            gamingPage.voteOnPostBasedOnKeyword(2, "Nintendo");

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


    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            String screenshotPath = captureScreenshot(result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
            test.log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test passed");
        } else {
            test.log(Status.SKIP, "Test skipped");
        }

        // Flush the report
        ExtentReportManager.flushReport();
    }

    /**
     * Captures a screenshot and saves it to the specified directory.
     *
     * @param testName The name of the test.
     * @return The path to the screenshot.
     */
    private String captureScreenshot(String testName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "test-output/screenshots/" + testName + ".png";
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            return screenshotPath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot: " + e.getMessage());
        }
    }
}