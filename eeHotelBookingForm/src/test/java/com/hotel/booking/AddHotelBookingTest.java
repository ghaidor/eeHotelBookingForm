package com.hotel.booking;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.hotel.booking.pageobjects.HotelBookingFormPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddHotelBookingTest  {

    private static HotelBookingFormPage booking = new HotelBookingFormPage();
    private String newId;

    @Before
    public void goToHotelBookingPage() {
        booking.goTo();
        Assert.assertTrue(booking.isAt());
    }

    @Test
    public void addHotelBooking() {

        String firstName = "Hotel";
        String surname = "Governor";
        String depositPaid = "true";
        String checkinDay = "5";
        String checkoutDay = "17";
        double price = 305.30;
        int timeout = 20;

        //Enter new booking details and save
        booking
                .fillInFirstName(firstName)
                .fillInLastName(surname)
                .fillInPrice(price)
                .depositPaid(depositPaid)
                .selectCheckInDate(checkinDay)
                .selectCheckOutDate(checkoutDay);

        //Record current bookings count & Ids
        booking.rowsCountBeforeBooking();
        booking.rowIdsBefore();

        //Save booking
        booking.saveBooking();

        //Wait & confirm new booking has been processed
        booking.waitForNewBookingDetails(booking.getRowsCountBefore(), timeout);
        Assert.assertEquals(booking.getRowsCountBefore()+ 1, booking.getRowsCountAfter());

        //Compare between previous & new set of row Ids
        booking.rowIdsAfter();
        newId = booking.rowIdComparison(booking.getRowIdsBefore(), booking.getRowIdsAfter());

        Assert.assertEquals(firstName, booking.getFirstName(newId));
        Assert.assertEquals(surname, booking.getLastName(newId));
        Assert.assertEquals(price, booking.getPrice(newId), 0.00);
        Assert.assertEquals(depositPaid, booking.getDepositPaid(newId));
        Assert.assertEquals(booking.getSelectedDateCI(), booking.getCheckinDate(newId));
        Assert.assertEquals(booking.getSelectedDateCO(), booking.getCheckoutDate(newId));
        Assert.assertEquals("Delete", booking.deleteButton(newId));
    }

    @Test
    public void addHotelBookingDateManualInput() {

        String firstName = "Brian'O Davies";
        String surname = "Steven'Reeves";
        double price = 340.56;
        String depositPaid = "false";
        String checkIn = (LocalDate.now().plusDays(7)).toString();
        String checkOut = (LocalDate.now().plusDays(16)).toString();
        int timeout = 20;

        //Enter new booking details and save
        booking
                .fillInFirstName(firstName)
                .fillInLastName(surname)
                .fillInPrice(price)
                .depositPaid(depositPaid)
                .enterCheckinDate(checkIn) //manual date entry
                .enterCheckoutDate(checkOut); //manual date entry

        //Record current bookings count & Ids
        booking.rowsCountBeforeBooking();
        booking.rowIdsBefore();

        //Save booking
        booking.saveBooking();

        //Wait & confirm new booking has been processed
        booking.waitForNewBookingDetails(booking.getRowsCountBefore(), timeout);
        Assert.assertEquals(booking.getRowsCountBefore()+ 1, booking.getRowsCountAfter());

        //Compare between previous & new set of row Ids
        booking.rowIdsAfter();
        newId = booking.rowIdComparison(booking.getRowIdsBefore(), booking.getRowIdsAfter());

        Assert.assertEquals(firstName, booking.getFirstName(newId));
        Assert.assertEquals(surname, booking.getLastName(newId));
        Assert.assertEquals(price, booking.getPrice(newId), 0.00);
        Assert.assertEquals(depositPaid, booking.getDepositPaid(newId));
        Assert.assertEquals(checkIn, booking.getCheckinDate(newId));
        Assert.assertEquals(checkOut, booking.getCheckoutDate(newId));
        Assert.assertEquals("Delete", booking.deleteButton(newId));
    }

    @Test
    public void addHotelBookingLongNames() {

        String firstName = "Hubert Blaine-Williams-Smith";
        String surname = "Wolfeschlegelsteinhausenbergerdorff";
        String depositPaid = "true";
        String checkinDay = "1";
        String checkoutDay = "28";
        double price = 9305.31;
        int timeout = 20;

        //Enter new booking details and save
        booking
                .fillInFirstName(firstName)
                .fillInLastName(surname)
                .fillInPrice(price)
                .depositPaid(depositPaid)
                .selectCheckInDate(checkinDay)
                .selectCheckOutDate(checkoutDay);

        //Record current bookings count & Ids
        booking.rowsCountBeforeBooking();
        booking.rowIdsBefore();

        //Save booking
        booking.saveBooking();

        //Wait & confirm new booking has been processed
        booking.waitForNewBookingDetails(booking.getRowsCountBefore(), timeout);
        Assert.assertEquals(booking.getRowsCountBefore()+ 1, booking.getRowsCountAfter());

        //Compare between previous & new set of row Ids
        booking.rowIdsAfter();
        newId = booking.rowIdComparison(booking.getRowIdsBefore(), booking.getRowIdsAfter());

        Assert.assertEquals(firstName, booking.getFirstName(newId));
        Assert.assertEquals(surname, booking.getLastName(newId));
        Assert.assertEquals(price, booking.getPrice(newId), 0.00);
        Assert.assertEquals(depositPaid, booking.getDepositPaid(newId));
        Assert.assertEquals(booking.getSelectedDateCI(), booking.getCheckinDate(newId));
        Assert.assertEquals(booking.getSelectedDateCO(), booking.getCheckoutDate(newId));
        Assert.assertEquals("Delete", booking.deleteButton(newId));
    }

    @Test
    public void attemptHotelBookingWithoutPrice() {

        String firstName = "Test";
        String surname = "NoPrice";
        String depositPaid = "true";
        String checkinDay = "10";
        String checkoutDay = "20";

        //Enter new booking details and save
        booking
                .fillInFirstName(firstName)
                .fillInLastName(surname)
                .depositPaid(depositPaid)
                .selectCheckInDate(checkinDay)
                .selectCheckOutDate(checkoutDay);

        //Record current bookings count & Ids
        booking.rowsCountBeforeBooking();
        booking.rowIdsBefore();

        //Attempt to save booking
        booking.saveBooking();

        //Confirm new row has not been added
        booking.rowsCountAfterAction();
        Assert.assertEquals(booking.getRowsCountBefore(), booking.getRowsCountAfter());

        //Compare between previous & new set of row Ids to ensure is same
        booking.rowIdsAfter();
        Assert.assertEquals(booking.getRowIdsBefore(), booking.getRowIdsAfter());
    }

    @Test
    public void attemptHotelBookingWithoutDate() {

        String firstName = "Test";
        String surname = "NoDate";
        String depositPaid = "false";
        double price = 2051;

        //Enter new booking details and save
        booking
                .fillInFirstName(firstName)
                .fillInLastName(surname)
                .depositPaid(depositPaid)
                .fillInPrice(price);

        //Record current bookings count & Ids
        booking.rowsCountBeforeBooking();
        booking.rowIdsBefore();

        //Attempt to save booking
        booking.saveBooking();

        //Confirm new row has not been added
        booking.rowsCountAfterAction();
        Assert.assertEquals(booking.getRowsCountBefore(), booking.getRowsCountAfter());

        //Compare between previous & new set of row Ids to ensure is same
        booking.rowIdsAfter();
        Assert.assertEquals(booking.getRowIdsBefore(), booking.getRowIdsAfter());
    }

    @Test
    public void deleteNewHotelBooking() {

        String firstName = "Hotel";
        String surname = "Deleter";
        String depositPaid = "true";
        String checkinDay = "10";
        String checkoutDay = "20";
        double price = 5305.30;
        int timeout = 20;

        //Enter new booking details and save
        booking
                .fillInFirstName(firstName)
                .fillInLastName(surname)
                .fillInPrice(price)
                .depositPaid(depositPaid)
                .selectCheckInDate(checkinDay)
                .selectCheckOutDate(checkoutDay);

        //Save booking and extract new booking ID
        booking.rowsCountBeforeBooking();
        booking.rowIdsBefore();
        booking.saveBooking();
        booking.waitForNewBookingDetails(booking.getRowsCountBefore(), timeout);
        booking.rowIdsAfter();
        newId = booking.rowIdComparison(booking.getRowIdsBefore(), booking.getRowIdsAfter());

        //Delete the newly created booking from list
        booking.deleteBooking(booking.rowsCountAfterAction(), newId, 10);
        Assert.assertEquals(booking.getRowsCountBefore(), booking.getRowsCountAfter());
        Assert.assertFalse("Booking deletion NOT successful!", booking.rowIdsAfter().contains(newId));
    }

    @AfterClass
    public static void TearDownClass()
    {
        booking.tearDown();
    }
}
