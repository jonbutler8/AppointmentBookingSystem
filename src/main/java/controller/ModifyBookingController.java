package controller;

import controller.protocols.ViewController;
import model.Booking;
import model.NewBookingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.ModifyBookingView;

public class ModifyBookingController extends NewBookingTimeController {

    private static final Logger logger =
            LogManager.getLogger(ModifyBookingController.class);

    private Booking booking;

    ModifyBookingController(ViewController viewController,
      ModifyBookingView view, DaoManager daoManager, Booking booking,
      PermissionManager permissionManager) {
        super(viewController, view, new NewBookingHandler(booking.customerID,
          booking), daoManager, permissionManager);
        this.booking = booking;
    }

    @Override
    public void submit() {
        if(bookingDao.deleteBooking(booking.id))
            super.submit();
    }

    @Override
    public void goBack() {
        viewController.gotoNewBookings();
    }
}
