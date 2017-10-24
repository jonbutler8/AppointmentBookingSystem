package controller;

import controller.protocols.BannerViewController;
import controller.protocols.BookingDao;
import model.BookingSummary;
import model.Customer;
import view.protocols.CustomerBookingsView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerBookingsController extends BannerViewController {

    private CustomerBookingsView view;
    private BookingDao bookingDao;
    private PermissionManager permsManager;

    CustomerBookingsController(CustomerBookingsView view, BookingDao
      bookingDao, PermissionManager permsManager) {
        super(view, permsManager.getLoggedInUser().businessID());
        this.view = view;
        this.bookingDao = bookingDao;
        this.permsManager = permsManager;
    }

    @Override
    public void populateView() {
        super.populateView();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(30);
        Customer customer = (Customer) permsManager.user;
        ArrayList<BookingSummary> bookingSummaries = bookingDao
          .getBookings(customer.customerID, startDate, endDate, permsManager.getLoggedInUser().businessID());

        if(!bookingSummaries.isEmpty()) {
            for(BookingSummary booking : bookingSummaries) {
                view.addBooking(booking);
            }
        }
        else {
            view.displayNoBookingsMessage();
        }
    }
}
