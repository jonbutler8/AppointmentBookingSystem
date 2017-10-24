package controller.protocols;

import model.Booking;
import model.BookingSummary;
import model.NewBookingHandler;

import java.time.LocalDate;
import java.util.ArrayList;

public interface BookingDao {
    ArrayList<Booking> getBookingsByDateForEmployee(int employeeID, LocalDate localDate, int businessID);
    boolean addBooking(NewBookingHandler bookingHandler);
    ArrayList<BookingSummary> getBookings(LocalDate startDate,
      LocalDate endDate, int businessID);
    ArrayList<BookingSummary> getBookings(int customerID, LocalDate startDate,
      LocalDate endDate, int businessID);
    boolean deleteBooking(int bookingID);
    Booking getBooking(int bookingID);
}
