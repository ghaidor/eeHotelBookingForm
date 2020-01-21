package com.hotel.booking.pageobjects;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.List;

import static org.openqa.selenium.By.*;

public final class HotelBookingFormPage extends BasePage {

    @Getter
    private String selectedDateCI;
    @Getter
    private String selectedDateCO;

    private static final String URL = "http://hotel-test.equalexperts.io/";

    private final By FIRST_NAME = id("firstname");
    private final By LAST_NAME = id("lastname");
    private final By PRICE = id("totalprice");
    private final By DEPOSIT = id("depositpaid");
    private final By CHECK_IN = id("checkin");
    private final By CHECK_OUT = id("checkout");
    private final By NEXT_MONTH_CI = cssSelector(".ui-datepicker-next");
    private final By NEXT_MONTH_CO = cssSelector(".ui-icon-circle-triangle-e");
    private final By MONTH_NAME = xpath("//span[@class='ui-datepicker-month']");
    private final By YEAR = xpath("//span[@class='ui-datepicker-year']");
    private final By SAVE_BOOKING = cssSelector("input[value=' Save ']");

    private static final String GET_FIRST_NAME = "//div[@id='%s']/div[@class='col-md-2']";
    private static final String GET_LAST_NAME = "//div[@id='%s']/div[@class='col-md-2'][2]";
    private static final String GET_PRICE = "//div[@id='%s']/div[@class='col-md-1']";
    private static final String GET_DEPOSIT_PAID = "//div[@id='%s']/div[@class='col-md-2'][3]";
    private static final String GET_CHECKIN_DATE = "//div[@id='%s']/div[@class='col-md-2'][4]";
    private static final String GET_CHECKOUT_DATE = "//div[@id='%s']/div[@class='col-md-2'][5]";
    private static final String DELETE_BUTTON = "//div[@id='%s']/div[@class='col-md-1'][2]/input";

    private static final String TO_DELETE_BOOKING_ID = "//div[@id='%s']";
    private static final String DELETE_BUTTON_PATH = "//*[@id='%s']/div[7]/input";

    public void goTo() {
        goTo(URL);
        waitForPageToLoad();
    }

    public boolean isAt() {
        return driverTitle().equals("Hotel booking form");
    }

    public String getFirstName(String newId) {
        return ele(GET_FIRST_NAME, newId);
    }

    public String getLastName(String newId) {
        return ele(GET_LAST_NAME, newId);
    }

    public double getPrice(String newId) {
        return Double.parseDouble(ele(GET_PRICE, newId));
    }

    public String getDepositPaid(String newId) {
        return ele(GET_DEPOSIT_PAID, newId);
    }

    public String getCheckinDate(String newId) {
        return ele(GET_CHECKIN_DATE, newId);
    }

    public String getCheckoutDate(String newId) {
        return ele(GET_CHECKOUT_DATE, newId);
    }

    public String deleteButton(String newId) {
        WebElement element = getDriver().findElement(By.xpath(String.format(DELETE_BUTTON, newId)));
        Assert.assertTrue("Delete button is disabled for booking ID no: " + newId, element.isEnabled());
        return element.getAttribute("value");
    }

    public HotelBookingFormPage fillInFirstName(String value) {
        fillInTextField(FIRST_NAME, value);
        return this;
    }

    public HotelBookingFormPage fillInLastName(String value) {
        fillInTextField(LAST_NAME, value);
        return this;
    }

    public HotelBookingFormPage fillInPrice(double value) {
        fillInNumericField(PRICE, value);
        return this;
    }

    public HotelBookingFormPage depositPaid(String value) {
        Select selectDeposit = new Select(getDriver().findElement(DEPOSIT));
        selectDeposit.selectByVisibleText(value);
        return this;
    }

    public HotelBookingFormPage selectCheckInDate(String day) {
        clickElement(CHECK_IN);
        clickElement(NEXT_MONTH_CI);
        selectedDateCI = dateBuilder(MONTH_NAME, YEAR, day);
        clickElement(By.linkText(day));
        return this;
    }

    public HotelBookingFormPage selectCheckOutDate(String day) {
        clickElement(CHECK_OUT);
        clickElement(NEXT_MONTH_CO);
        selectedDateCO = dateBuilder(MONTH_NAME, YEAR, day);
        clickElement(By.linkText(day));
        return this;
    }

    public HotelBookingFormPage enterCheckinDate(String checkinDateEntered) {
        fillInDateField(CHECK_IN, checkinDateEntered);
        return this;
    }

    public void enterCheckoutDate(String checkoutDateEntered) {
        fillInDateField(CHECK_OUT, checkoutDateEntered);
    }

    public void saveBooking() {
        WebElement e = getDriver().findElement(SAVE_BOOKING);
        e.click();
    }

    public int deleteBooking(final int rowsCountAfter, String newId, int timeout) {
        WebElement deleteBooking = getDriver().findElement(By.xpath(String.format(TO_DELETE_BOOKING_ID, newId)));
        List<String> bookingDetails = Collections.singletonList(deleteBooking.getText());
        LOG.info("Booking ID " + newId + " will be DELETED and contains the following details: \n" + bookingDetails.toString().replaceAll("\\r\\n|\\r|\\n", " "));

        WebElement deleteButton = getDriver().findElement(By.xpath(String.format(DELETE_BUTTON_PATH, newId)));
        deleteButton.click();
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.invisibilityOf(deleteBooking));

        LOG.info("Count of hotel bookings AFTER deletion: " + rowsCountAfterAction());
        LOG.info("Booking ID " + newId + " has been DELETED");
        return rowsCountAfter;
    }

}
