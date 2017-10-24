package controller;

import controller.protocols.ViewController;
import model.Booking;
import model.NewBookingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import view.FXBusinessHoursView;
import view.FXBusinessRegisterView;
import view.FXLoginView;
import view.FXServiceConfigView;
import view.protocols.*;

class ViewControllerFactory {

    private static final Logger logger =
      LogManager.getLogger(ViewControllerFactory.class);

    private ViewController viewController;
    private PermissionManager permissionManager;
    private DaoManager daoManager;

    ViewControllerFactory(ViewController viewController, DaoManager
      daoManager, PermissionManager permissionManager) {
        this.viewController = viewController;
        this.daoManager = daoManager;
        this.permissionManager = permissionManager;
        logger.debug("Instantiated:  ViewControllerFactory");
    }

    AddEmployeeController getController(AddEmployeeView view) {
        logger.debug("Instantiating: AddEmployeeController");
        return new AddEmployeeController(view, daoManager.employeeDao,
          permissionManager);
    }

    BOHomeController getController(BOHomeView view) {
        return new BOHomeController(viewController, view, permissionManager);
    }

    BookingsSummaryController getController(BookingsSummaryView view) {
        return new BookingsSummaryController(viewController, view, daoManager.bookingDao, permissionManager);
    }

    CustomerBookingsController getController(CustomerBookingsView view) {
        return new CustomerBookingsController(view, daoManager.bookingDao,
          permissionManager);
    }

    CustomerHomeController getController(CustomerHomeView view) {
        return new CustomerHomeController(viewController, view,
          permissionManager);
    }

    // TODO: Refactor - standardise to getController(viewController, view, ...)
    CustomerRegisterController getCustomerRegisterController() {
        logger.debug("Instantiating: CustomerRegisterController");
        return new CustomerRegisterController(daoManager.userDao, daoManager.businessDao);
    }

    EmployeeTimesController getController(EmployeeTimesView view) {
        logger.debug("Instantiating: EmployeeTimesController");
        return new EmployeeTimesController(viewController, view,
          daoManager, permissionManager);
    }

    EmployeeServiceToggleController getController(EmployeeServiceToggleView view) {
        logger.debug("Instantiating: EmployeeServiceToggleController");
        return new EmployeeServiceToggleController(viewController, view,
          daoManager.employeeDao, daoManager.serviceDao, permissionManager);
    }

    LoginController getController(FXLoginView view) {
        logger.debug("Instantiating: LoginController");
        return new LoginController(viewController, view, permissionManager,
          daoManager.userDao);
    }

    NewBookingCustomerController getController(NewBookingCustomerView view) {
        return new NewBookingCustomerController(viewController, view,
          daoManager.customerDao, permissionManager);
    }

    NewBookingServiceController getController(NewBookingServiceView view,
      NewBookingHandler newBookingHandler) {
        return new NewBookingServiceController(viewController, view,
          newBookingHandler, daoManager.serviceDao, permissionManager);
    }

    NewBookingTimeController getController(NewBookingTimeView view,
      NewBookingHandler newBookingHandler) {
        return new NewBookingTimeController(viewController, view,
          newBookingHandler, daoManager, permissionManager);
    }

    NewBookingSuccessController getController(NewBookingSuccessView view,
      NewBookingHandler newBookingHandler) {
        return new NewBookingSuccessController(viewController, view,
          newBookingHandler);
    }

    NewBookingFailureController getController(NewBookingFailureView view) {
        return new NewBookingFailureController(viewController, view);
    }

    NewBookingsController getController(NewBookingsView view) {
        return new NewBookingsController(viewController, view,
          daoManager.bookingDao, permissionManager);
    }

    ModifyBookingController getController(ModifyBookingView view,
      Booking booking) {
        return new ModifyBookingController(viewController, view, daoManager,
          booking, permissionManager);
    }

    public BusinessHoursController getController(FXBusinessHoursView view) {
        return new BusinessHoursController(viewController, view,
                daoManager, permissionManager);
    }

    SuperUserHomeController getController(SUHomeView view) {
        return new SuperUserHomeController(viewController);
    }

    BusinessRegisterController getController(FXBusinessRegisterView view) {
        return new BusinessRegisterController(daoManager.userDao, daoManager.businessDao);
    }

    ServiceConfigController getController(FXServiceConfigView view) {
        return new ServiceConfigController(view, daoManager.serviceDao,
          permissionManager);
    }
}
