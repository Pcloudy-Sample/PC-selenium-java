package com.pcloudy.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class PcloudySeleniumRunner {
    private static final String HUB_URL = "https://browser.device.pcloudy.com/seleniumcloud/wd/hub";
    private static final String USER_NAME = "Your-Username";
    private static final String ACCESS_KEY = "Your-Access-Key";

    private WebDriver driver;

    public static void main(String[] args) throws Exception {
        PcloudySeleniumRunner runner = new PcloudySeleniumRunner();
        runner.setUp();
        try {
            runner.wikipediaTest();
        } finally {
            runner.tearDown();
        }
    }

    @BeforeMethod
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("pcloudy:options", buildOptions());

        driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void wikipediaTest() throws Exception {
        driver.get("https://www.wikipedia.org/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("search")));
        searchBox.clear();
        searchBox.sendKeys("Selenium");
        searchBox.sendKeys(Keys.ENTER);

        captureScreenshot(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

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

    private static void captureScreenshot(WebDriver driver) {
        try {
            Path folder = Path.of("target", "screenshots");
            Files.createDirectories(folder);
            String fileName = "Screenshot_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".png";
            Path path = folder.resolve(fileName);
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(path, screenshot);
        } catch (Exception ignored) {
        }
    }
}
