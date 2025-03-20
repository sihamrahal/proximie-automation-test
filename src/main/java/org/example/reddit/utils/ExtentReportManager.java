package org.example.reddit.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    // Initialize ExtentReports
    public static ExtentReports getInstance() {
        if (extent == null) {
            // Set up the ExtentSparkReporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Reddit Automation Report");
            sparkReporter.config().setReportName("Reddit Test Report");

            // Initialize ExtentReports and attach the reporter
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    // Create a test in the report
    public static ExtentTest createTest(String testName) {
        // Ensure ExtentReports is initialized
        getInstance();
        test = extent.createTest(testName);
        return test;
    }

    // Flush the report (write to file)
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}