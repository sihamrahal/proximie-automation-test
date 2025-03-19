package org.example.reddit.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setup() {
        try {
            // Automatically set up the ChromeDriver using WebDriverManager
            WebDriverManager.chromedriver().setup();

            // Initialize the Chrome driver
            driver = new ChromeDriver();

            // Maximize the browser window for better visibility
            driver.manage().window().maximize();

            logger.info("WebDriver initialized successfully.");

            // Open the Reddit website
            driver.get(org.example.reddit.config.Configuration.REDDIT_URL);
            logger.info("Opened Reddit URL: {}", org.example.reddit.config.Configuration.REDDIT_URL);

        } catch (Exception e) {
            logger.error("Error during setup: ", e);
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage()); //Re-throw to fail the test
        }
    }

    @AfterMethod
    public void teardown() {
        try {
            if (driver != null) {
                driver.quit();
                logger.info("WebDriver quit successfully.");
            }
        } catch (Exception e) {
            logger.error("Error during teardown: ", e);
        }
    }
}