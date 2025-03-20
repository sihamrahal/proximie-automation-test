# Reddit Automation Testing

This project automates actions on Reddit using Selenium WebDriver with TestNG. It performs tasks like logging in, voting on posts based on keywords, and logging out. The framework is designed to be extensible for additional functionalities.

## Prerequisites

- **Java 11 or higher**: Ensure you have Java installed.
- **Maven**: For managing dependencies and building the project.
- **Selenium WebDriver**: For interacting with the browser.
- **TestNG**: For test execution and reporting.
- **WebDriverManager**: For automatically managing browser drivers.
- **ExtentReports**: For test reporting.

## Setup

1. Clone the repository.
2. Ensure you have **Java** and **Maven** installed.
3. Run the following command to install dependencies:
    ```bash
    mvn install
    ```

## Configuration

You can configure the following properties in `src/main/resources/config.properties`:

- `reddit.url`: The Reddit URL (default: `https://old.reddit.com`).
- `reddit.username`: The Reddit username for login.
- `reddit.password`: The Reddit password for login.


Example:
```properties
reddit.url=https://old.reddit.com
reddit.username=Siham-Proximie
reddit.password=testuser1
```

## Running the Tests

1. The tests are implemented using **TestNG**. To run the tests, you can execute the following command:

    ```bash
    mvn test
    ```

2. Test execution will be logged, and the results will be generated in the `test-output/ExtentReport.html` file.

## Test Flow

- Login: Logs into Reddit using the provided credentials.

- Navigate to Subreddit: Navigates to the gaming subreddit.

- Vote on Post: Votes on the second post if the title contains the keyword "Nintendo".

- Logout: Logs out of the Reddit account.

## Test Reporting

- **ExtentReports** is used for generating detailed test reports. After each test execution, a report will be available in the `test-output/ExtentReport.html` file.
- Screenshots are taken for failed tests and included in the report.

## Screenshots

Screenshots will be saved in the `test-output/screenshots/` folder.

---
**Contact:**

For questions or feedback, please contact:

Siham Rahal: siham.rahal@outlook.com

GitHub: sihamrahal