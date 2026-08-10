# pCloudy Selenium Java Sample

This project is a Java-based Selenium sample adapted from the C# pCloudy example. It connects to the pCloudy Selenium grid, opens Wikipedia, searches for Selenium, and captures a screenshot.

## Prerequisites

- Java 11 or newer
- Maven 3.6+
- A pCloudy account with valid username and access key

## Setup

1. Clone the repository.
2. Update the credentials in [src/main/java/com/pcloudy/selenium/PcloudySeleniumRunner.java](src/main/java/com/pcloudy/selenium/PcloudySeleniumRunner.java).
3. Run the sample:

```bash
mvn clean compile exec:java -Dexec.mainClass=com.pcloudy.selenium.PcloudySeleniumRunner
```

## Run tests

```bash
mvn test
```

## Notes

- The pCloudy hub URL is configured in the runner class.
- Screenshots are saved under the target/screenshots folder.
- The sample uses the same capability structure as the C# reference project.

