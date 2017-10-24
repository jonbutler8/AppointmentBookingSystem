package controller;

import controller.protocols.BannerViewController;
import controller.protocols.ViewController;
import model.Customer;
import model.NewBookingHandler;
import view.protocols.CustomerHomeView;

public class CustomerHomeController extends BannerViewController {

    private ViewController viewController;
    private PermissionManager permissionManager;
    private CustomerHomeView view;

    CustomerHomeController(ViewController viewController,
      CustomerHomeView view, PermissionManager permissionManager) {
        super(view, permissionManager.getLoggedInUser()
          .businessID());
        this.view = view;
        this.viewController = viewController;
        this.permissionManager = permissionManager;
    }

    @Override
    public void populateView() {
        super.populateView();
        view.setBusinessName(permissionManager.getLoggedInUser().getBusinessName());
    }

    public void gotoNewBooking() {
        Customer customer = (Customer) permissionManager.getLoggedInUser();
        viewController.gotoNewBookingService(new NewBookingHandler(
          customer.customerID, permissionManager.getLoggedInUser().businessID()));
    }

    public void gotoViewBookings() {
        viewController.gotoCustomerBookings();
    }
}
