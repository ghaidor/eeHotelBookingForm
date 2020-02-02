package com.hotel.booking.pageobjects;

import com.github.underscore.lodash.U;
import com.hotel.booking.factory.FactoryDriver;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;


public class BasePage {

    protected static final Logger LOG = LogManager.getLogger(BasePage.class);

    @Getter
    private int rowsCountBefore;
    @Getter
    private int rowsCountAfter;
    @Getter
    private String rowIdsBefore;
    @Getter
    private String rowIdsAfter;

    private final By rowsAfter = xpath("//div[@class='row'][@id]");
    private final By rowsBefore = xpath("//div[@class='row'][@id]");

    protected WebDriver driver;

    public BasePage() {
        driver = FactoryDriver.getWebDriverContext();
    }

    public void goTo(String url) {
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void waitForPageToLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").toString().equals("complete"));
        } catch (Exception error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void tearDown() {
        driver.quit();
        driver = null;
    }

    public void fillInTextField(By element, String value) {
        getDriver().findElement(element).clear();
        getDriver().findElement(element).sendKeys(value);
    }

    public void fillInNumericField(By element, double value) {
        getDriver().findElement(element).clear();
        getDriver().findElement(element).sendKeys(String.valueOf(value));
    }

    public void fillInDateField(By element, String value) {
        getDriver().findElement(element).clear();
        getDriver().findElement(element).sendKeys(value);
    }

    public int rowsCountBeforeBooking() {
        waitForPageToLoad();
        WebElement baseTable = getDriver().findElement(By.id("bookings"));
        rowsCountBefore = baseTable.findElements(rowsBefore).size();
        if (rowsCountBefore == 0) {
            LOG.info("No hotel bookings recorded!");
        } else {
            LOG.info("Count of hotel bookings BEFORE action: {}", rowsCountBefore);
        }
        return rowsCountBefore;
    }

    public int rowsCountAfterAction() {
        WebElement baseTable = getDriver().findElement(By.id("bookings"));
        rowsCountAfter = baseTable.findElements(rowsAfter).size();
        return rowsCountAfter;
    }

    public String rowIdsBefore() {
        WebElement baseTable = getDriver().findElement(By.id("bookings"));
        List<WebElement> tableRows = baseTable.findElements(By.cssSelector(".row"));
        if (tableRows.size() == 0) {
            //do nothing if no bookings exist
        } else {
            for (int i = 1; i < tableRows.size(); ++i) {
                rowIdsBefore = tableRows.get(i).getAttribute("id");
                LOG.info("Before action - Booking ID for row {}: {}", i, rowIdsBefore);
            }
        }
        return rowIdsBefore;
    }

    public String rowIdsAfter() {
        waitForPageToLoad();
        WebElement baseTable = getDriver().findElement(By.id("bookings"));
        List<WebElement> tableRows = baseTable.findElements(By.cssSelector(".row"));
        for (int i = 1; i < tableRows.size(); ++i) {
            rowIdsAfter = tableRows.get(i).getAttribute("id");
            LOG.info("After booking/deletion - Booking ID for row {}: {}", i, rowIdsAfter);
        }
        return rowIdsAfter;
    }

    public int waitForNewBookingDetails(int rowsCountBefore, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(driver -> rowsCountAfterAction() > rowsCountBefore);
        LOG.info("Count of hotel bookings AFTER action: {}", rowsCountAfter);
        return rowsCountAfter;
    }

    public String rowIdComparison(String rowIdsBeforeSet, String rowIdsAfterSet) {
        List<String> beforeIds = Collections.singletonList(rowIdsBeforeSet);
        List<String> afterIds = Collections.singletonList(rowIdsAfterSet);
        String newId = U.difference(afterIds, beforeIds).toString().replaceAll("[\\[\\]]", "");
        LOG.info("New booking ID number: {}", newId);
        return newId;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected String driverTitle() {
        return driver.getTitle();
    }

    protected String ele(String getElement, String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(getElement, newId)));
        return element.getText();
    }

    protected void clickElement(By ele) {
        WebElement element = getDriver().findElement(ele);
        element.click();
    }

    protected String dateBuilder(By month, By year, String day) {
        String selectedMonth = getDriver().findElement(month).getText();
        String selectedYear = getDriver().findElement(year).getText();
        LocalDate date = LocalDate.of(Integer.parseInt(selectedYear), Month.valueOf(selectedMonth.toUpperCase()), Integer.parseInt(day));
        return date.toString();
    }

}


