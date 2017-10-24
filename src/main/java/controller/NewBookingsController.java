package controller;

import controller.protocols.BannerViewController;
import controller.protocols.BookingDao;
import controller.protocols.ViewController;
import model.BookingSummary;
import view.protocols.NewBookingsView;

import java.time.LocalDate;
import java.util.ArrayList;

public class NewBookingsController extends BannerViewController {

    private ViewController viewController;
    private NewBookingsView view;
    private BookingDao bookingDao;
    private PermissionManager permissionManager;

    NewBookingsController(ViewController viewController, NewBookingsView view,
      BookingDao bookingDao, PermissionManager permissionManager) {
        super(view, permissionManager.getLoggedInUser().businessID());
        this.viewController = viewController;
        this.view = view;
        this.bookingDao = bookingDao;
        this.permissionManager = permissionManager;
    }

    @Override
    public void populateView() {
        super.populateView();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        view.clearBookings();
        ArrayList<BookingSummary> bookings = bookingDao.getBookings(startDate,
          endDate, permissionManager.getLoggedInUser().businessID());
        if(!bookings.isEmpty()) {
            for(BookingSummary booking : bookings) {
                view.addBooking(booking);
            }
        }
        else {
            view.displayNoBookingsMessage();
        }
    }

    public void modifyBooking(BookingSummary booking) {
        viewController.gotoModifyBooking(bookingDao.getBooking(booking.id));
    }

    public void deleteBooking(BookingSummary booking) {
        bookingDao.deleteBooking(booking.id);
        populateView();
    }
}
