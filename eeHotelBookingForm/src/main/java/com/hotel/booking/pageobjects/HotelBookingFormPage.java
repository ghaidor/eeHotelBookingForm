package com.hotel.booking.pageobjects;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.Month;

import static org.openqa.selenium.By.*;

public final class HotelBookingFormPage extends BasePage {

    @Getter
    private String selectedDateCI;
    @Getter
    private String selectedDateCO;

    private static final String URL = "http://hotel-test.equalexperts.io/";

    private final By firstName = id("firstname");
    private final By lastName = id("lastname");
    private final By price = id("totalprice");
    private final By deposit = id("depositpaid");
    private final By checkInField = id("checkin");
    private final By checkOutField = id("checkout");
    private final By nextMonthCI = cssSelector(".ui-datepicker-next");
    private final By nextMonthCO = cssSelector(".ui-icon-circle-triangle-e");
    private final By monthName = xpath("//span[@class='ui-datepicker-month']");
    private final By yearValue = xpath("//span[@class='ui-datepicker-year']");
    private final By saveBooking = cssSelector("input[value=' Save ']");

    private static final String getFirstName = "//div[@id='%s']/div[@class='col-md-2']";
    private static final String getLastName = "//div[@id='%s']/div[@class='col-md-2'][2]";
    private static final String getPrice = "//div[@id='%s']/div[@class='col-md-1']";
    private static final String getDepositPaid = "//div[@id='%s']/div[@class='col-md-2'][3]";
    private static final String getCheckinDate = "//div[@id='%s']/div[@class='col-md-2'][4]";
    private static final String getCheckoutDate = "//div[@id='%s']/div[@class='col-md-2'][5]";
    private static final String deleteButton = "//div[@id='%s']/div[@class='col-md-1'][2]/input";

    public void goToBookingForm() {
        goTo(URL);
        waitForPageToLoad();
    }

    public boolean isAt() {
        return driverTitle().equals("Hotel booking form");
    }

    public String getFirstName(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(getFirstName, newId)));
        return element.getText();
    }

    public String getLastName(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(getLastName, newId)));
        return element.getText();
    }

    public double getPrice(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(getPrice, newId)));
        return Double.parseDouble(element.getText());
    }

    public String getDepositPaid(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(getDepositPaid, newId)));
        return element.getText();
    }

    public String getCheckinDate(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(getCheckinDate, newId)));
        return element.getText();
    }

    public String getCheckoutDate(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(getCheckoutDate, newId)));
        return element.getText();
    }

    public String deleteButton(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(deleteButton, newId)));
        Assert.assertTrue("Delete button is disabled for booking ID no: " + newId, element.isEnabled());
        return element.getAttribute("value");
    }

    public HotelBookingFormPage fillInFirstName(String value) {
        fillInTextField(firstName, value);
        return this;
    }

    public HotelBookingFormPage fillInLastName(String value) {
        fillInTextField(lastName, value);
        return this;
    }

    public HotelBookingFormPage fillInPrice(double value) {
        fillInNumericField(price, value);
        return this;
    }

    public HotelBookingFormPage depositPaid(String value) {
        Select selectDeposit = new Select(getDriver().findElement(deposit));
        selectDeposit.selectByVisibleText(value);
        return this;
    }

    public HotelBookingFormPage selectCheckInDate(String day) {
        WebElement e = getDriver().findElement(checkInField);
        e.click();
        WebElement month = getDriver().findElement(nextMonthCI);
        month.click();
        String selectedMonth = getDriver().findElement(monthName).getText();
        String selectedYear = getDriver().findElement(yearValue).getText();
        LocalDate date = LocalDate.of(Integer.parseInt(selectedYear), Month.valueOf(selectedMonth.toUpperCase()), Integer.parseInt(day));
        selectedDateCI = date.toString();

        getDriver().findElement(By.linkText(day)).click();
        return this;
    }

    public HotelBookingFormPage selectCheckOutDate(String day) {
        WebElement e = getDriver().findElement(checkOutField);
        e.click();
        WebElement month = getDriver().findElement(nextMonthCO);
        month.click();
        String selectedMonth = getDriver().findElement(monthName).getText();
        String selectedYear = getDriver().findElement(yearValue).getText();
        LocalDate date = LocalDate.of(Integer.parseInt(selectedYear), Month.valueOf(selectedMonth.toUpperCase()), Integer.parseInt(day));
        selectedDateCO = date.toString();

        getDriver().findElement(By.linkText(day)).click();
        return this;
    }

    public HotelBookingFormPage enterCheckinDate(String checkinDateEntered) {
        fillInDateField(checkInField, checkinDateEntered);
        return this;
    }

    public void enterCheckoutDate(String checkoutDateEntered) {
        fillInDateField(checkOutField, checkoutDateEntered);
    }

    public void saveBooking() {
        WebElement e = getDriver().findElement(saveBooking);
        e.click();
    }

}
