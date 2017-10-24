package controller.protocols;

import model.Booking;
import model.NewBookingHandler;

/*
 * The ViewController provides other controllers the ability to switch views.
 */
public interface ViewController {
    void gotoLogin();
    void gotoCustomerRegister();
    void gotoRegisterSuccess();
    void gotoAddEmployee();
    void gotoCustomerBookings();
    void gotoNewBookingCustomer();
    void gotoNewBookingService(NewBookingHandler newBookingHandler);
    void gotoNewBookingTime(NewBookingHandler newBookingHandler);
    void gotoNewBookingSuccess(NewBookingHandler newBookingHandler);
    void gotoError(String message);
    void gotoNewBookings();
    void gotoModifyBooking(Booking booking);
    void gotoBookingsSummary();
    void gotoEmployeeTimeManager();
    void gotoHome();
    void logout();
    void gotoEmployeeServiceToggle();
    void gotoBusinessHours();
    void gotoSUHome();
    void gotoRegisterBusiness();
    void gotoBusinessRegisterSuccess();
    void gotoModifyServices();
}
