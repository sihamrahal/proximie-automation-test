package org.example.reddit.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration class to load and manage properties from the config.properties file.
 * This class provides static access to configuration values such as URLs, usernames, and passwords.
 */
public class Configuration {
    private static Properties prop = new Properties();

    static {
        try {
            // Load the properties file from the specified path
            prop.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a property value by key.
     *
     * @param key The key of the property to retrieve.
     * @return The value of the property, or null if the key is not found.
     */
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    // Static configuration values
    public static final String REDDIT_URL = getProperty("reddit.url");
    public static final String USERNAME = getProperty("reddit.username");
    public static final String PASSWORD = getProperty("reddit.password");
    public static final String GAMING_SUBREDDIT = getProperty("reddit.gamingsubreddit");
}