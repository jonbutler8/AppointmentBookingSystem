package controller;

import controller.protocols.BannerViewController;
import controller.protocols.BookingDao;
import controller.protocols.ViewController;
import model.BookingSummary;
import view.protocols.BookingsSummaryView;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookingsSummaryController extends BannerViewController {

    private ViewController viewController;
    private BookingsSummaryView view;
    private BookingDao bookingDao;
    private PermissionManager permissionManager;

    BookingsSummaryController(ViewController viewController, BookingsSummaryView view, BookingDao bookingDao, PermissionManager permissionManager) {
        super(view, permissionManager.getLoggedInUser()
          .businessID());
        this.viewController = viewController;
        this.view = view;
        this.bookingDao = bookingDao;
        this.permissionManager = permissionManager;
    }

    @Override
    public void populateView() {
        super.populateView();
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        ArrayList<BookingSummary> bookingSummaries = bookingDao.getBookings
                (startDate, endDate, permissionManager.getLoggedInUser()
                  .businessID());

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
