package view.protocols;

import model.BookingSummary;

public interface NewBookingsView extends BannerView {
    void displayNoBookingsMessage();
    void clearBookings();
    void addBooking(BookingSummary booking);
}
