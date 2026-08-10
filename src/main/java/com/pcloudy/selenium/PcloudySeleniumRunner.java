package com.pcloudy.selenium;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PcloudySeleniumRunner {
    private static final String HUB_URL = "https://browser.device.pcloudy.com/seleniumcloud/wd/hub";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            Map<String, Object> pcloudyOptions = buildPcloudyOptions();

            System.out.println("========== pCloudy Options ==========");
            System.out.println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(pcloudyOptions));

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");
            capabilities.setCapability("pcloudy:options", pcloudyOptions);

            System.out.println();
            System.out.println("Connecting to pCloudy Selenium Grid...");
            System.out.println(HUB_URL);

            long start = System.nanoTime();
            driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;

            System.out.println();
            System.out.println("======================================");
            System.out.println("Session Created Successfully");
            System.out.println("Time Taken : " + Duration.ofMillis(elapsedMs));
            System.out.println("Session ID : " + ((RemoteWebDriver) driver).getSessionId());
            System.out.println("======================================");

            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            System.out.println("Opening Wikipedia...");
            driver.get("https://www.wikipedia.org/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            System.out.println("Page Title: " + driver.getTitle());

            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("search")));
            searchBox.clear();
            searchBox.sendKeys("Selenium");
            searchBox.sendKeys(Keys.ENTER);

            captureScreenshot(driver);
            System.out.println("Wikipedia Selenium test completed successfully.");
        } catch (Exception ex) {
            System.out.println();
            System.out.println("============= ERROR =============");
            ex.printStackTrace();
            System.out.println("=================================");
            throw new RuntimeException(ex);
        } finally {
            if (driver != null) {
                System.out.println("Closing browser...");
                driver.quit();
            }
        }
    }

    static Map<String, Object> buildPcloudyOptions() {
        Map<String, Object> pcloudyOptions = new LinkedHashMap<>();
        pcloudyOptions.put("userName", System.getenv().getOrDefault("PCLOUDY_USERNAME", "anjali.y@opkey.com"));
        pcloudyOptions.put("accessKey", System.getenv().getOrDefault("PCLOUDY_ACCESS_KEY", "fmf74dbfnprg2kqzz5kqrd98"));
        pcloudyOptions.put("os", "Mac");
        pcloudyOptions.put("osVersion", "Monterey");
        pcloudyOptions.put("browserVersion", "145");
        pcloudyOptions.put("seleniumVersion", "4.18.1");
        pcloudyOptions.put("project", "Projrct_1");
        pcloudyOptions.put("build", "staging_browser_run");
        pcloudyOptions.put("name", "Login");
        pcloudyOptions.put("tag", "launch");
        pcloudyOptions.put("local", false);
        pcloudyOptions.put("pCloudy_EnableVideo", true);
        pcloudyOptions.put("idleTimeout", 5);
        return pcloudyOptions;
    }

    private static void captureScreenshot(WebDriver driver) {
        try {
            Path folder = Path.of("target", "screenshots");
            Files.createDirectories(folder);

            String fileName = "Screenshot_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".png";
            Path path = folder.resolve(fileName);
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(path, screenshot);

            System.out.println("Screenshot saved: " + path.toAbsolutePath());
        } catch (Exception ex) {
            System.out.println("Unable to save screenshot: " + ex.getMessage());
        }
    }
}
