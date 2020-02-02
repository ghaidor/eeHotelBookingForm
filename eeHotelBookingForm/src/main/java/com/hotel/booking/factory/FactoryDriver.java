package com.hotel.booking.factory;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;
import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class FactoryDriver {

    public static WebDriver getWebDriverContext() {
        final String systemEnvDriver = System.getenv("browser");
        if("firefox".equalsIgnoreCase(systemEnvDriver)) {
            WebDriverManager.getInstance(FIREFOX).setup();
            return new FirefoxDriver();

        } else if ("chrome".equalsIgnoreCase(systemEnvDriver)) {
            WebDriverManager.getInstance(CHROME).setup();
            return new ChromeDriver();
        }
        throw new UnsupportedOperationException();
    }
}
