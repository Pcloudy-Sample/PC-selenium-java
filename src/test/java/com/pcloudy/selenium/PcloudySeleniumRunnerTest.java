package com.pcloudy.selenium;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PcloudySeleniumRunnerTest {

    @Test
    void buildPcloudyOptions_containsRequiredCapabilities() {
        Map<String, Object> options = PcloudySeleniumRunner.buildPcloudyOptions();

        String expectedUsername = System.getenv().getOrDefault("PCLOUDY_USERNAME", "YOUR_PLOUDY_USERNAME");
        String expectedAccessKey = System.getenv().getOrDefault("PCLOUDY_ACCESS_KEY", "YOUR_PLOUDY_ACCESS_KEY");

        assertEquals(expectedUsername, options.get("userName"));
        assertEquals(expectedAccessKey, options.get("accessKey"));
        assertEquals("Mac", options.get("os"));
        assertEquals("Ventura", options.get("osVersion"));
        assertEquals("134", options.get("browserVersion"));
        assertEquals(false, options.get("local"));
        assertTrue((Boolean) options.get("pCloudy_EnableVideo"));
    }
}
