<div align="center">

# Run Selenium Tests with Java on pCloudy

[![pCloudy](https://img.shields.io/badge/Made%20by-pCloudy-1a73e8?style=for-the-badge)](https://www.pcloudy.com)
[![Selenium](https://img.shields.io/badge/Selenium-Java-blue?style=for-the-badge)](https://www.selenium.dev/)
[![Java](https://img.shields.io/badge/Java-11%2B-orange?style=for-the-badge)](https://www.java.com/)

</div>

## Overview

This repository contains a simple Selenium Java sample that runs a browser session on the pCloudy cloud Selenium grid.

The sample:

- Connects to the pCloudy remote Selenium hub
- Launches a Chrome browser session
- Opens Wikipedia
- Searches for `Selenium`
- Saves a screenshot under `target/screenshots`

## Project Details

- Language: Java
- Build tool: Maven
- Selenium version: `4.25.0`
- Test framework: TestNG
- Java version: `11`

## Prerequisites

- Java 11 or later
- Maven 3.8+ recommended
- A valid pCloudy account
- Access to the pCloudy Selenium endpoint

## Project Structure

```
pc-selenium-java/
├── pom.xml
├── README.md
└── src
    └── main
        └── java
            └── com
                └── pcloudy
                    └── selenium
                        └── PcloudySeleniumRunner.java
```

## Configuration

Open `src/main/java/com/pcloudy/selenium/PcloudySeleniumRunner.java` and update the following values:

- `HUB_URL` for the pCloudy Selenium hub
- `USER_NAME` with your pCloudy email
- `ACCESS_KEY` with your pCloudy access key
- The `buildOptions()` values for your desired OS, browser version, project name, and build name

Example capability block:

```java
static Map<String, Object> buildOptions() {
    Map<String, Object> options = new LinkedHashMap<>();
    options.put("userName", USER_NAME);
    options.put("accessKey", ACCESS_KEY);
    options.put("os", "Mac");
    options.put("osVersion", "Ventura");
    options.put("browserVersion", "134");
    options.put("seleniumVersion", "4.18.1");
    options.put("project", "Project_1");
    options.put("build", "staging_browser_run");
    options.put("name", "Wikipedia search");
    options.put("tag", "launch");
    options.put("local", false);
    options.put("pCloudy_EnableVideo", true);
    options.put("idleTimeout", 5);
    return options;
}
```

## Run the Sample

Run the sample through Maven exec:

```powershell
mvn exec:java -Dexec.mainClass=com.pcloudy.selenium.PcloudySeleniumRunner
```

## Getting Started

Clone the project and install dependencies:

```bash
git clone https://github.com/Pcloudy-Sample/PC-selenium-java
cd PC-selenium-java
mvn clean install
```

## What the Sample Does

The `PcloudySeleniumRunner` class:

- Creates a `RemoteWebDriver` session using `pcloudy:options`
- Navigates to `https://www.wikipedia.org/`
- Waits for the search box to become clickable
- Searches for `Selenium`
- Captures a screenshot in `target/screenshots`
- Closes the browser session

## Important Notes

- The project currently hardcodes the pCloudy username and access key in the runner. For real usage, move these values to environment variables or a secure secret store.
- `browserName` is set to `chrome` in the runner.
- Screenshots are written to `target/screenshots/` and are not committed to source control.
- The sample uses TestNG annotations, but the `main` method also allows it to run as a simple Java entry point.

## Troubleshooting

- If the session does not start, verify the hub URL, username, and access key.
- If the browser fails to launch, confirm the selected `os`, `osVersion`, and `browserVersion` are supported by your pCloudy plan.
- If Maven cannot resolve dependencies, make sure you have internet access and a working Maven installation.
- If screenshots are not created, check that the test reached the `captureScreenshot` step and that the process has write access to `target/screenshots`.



## Dependencies

The project uses these Maven dependencies:

- `org.seleniumhq.selenium:selenium-java`
- `org.testng:testng`


## Learn More

- [pCloudy Website](https://www.pcloudy.com)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
