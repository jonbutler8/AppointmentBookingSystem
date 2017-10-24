package controller;

import controller.protocols.BannerViewController;
import controller.protocols.ServiceDao;
import controller.protocols.ViewController;
import model.NewBookingHandler;
import model.Service;
import model.protocols.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.NewBookingServiceView;

import java.util.ArrayList;
import java.util.HashMap;

public class NewBookingServiceController extends BannerViewController {

    private static final Logger logger =
      LogManager.getLogger(NewBookingServiceController.class);

    private NewBookingHandler newBookingHandler;
    private NewBookingServiceView view;
    private ServiceDao serviceDao;
    private ViewController viewController;
    private PermissionManager permissionManager;

    NewBookingServiceController(ViewController viewController,
      NewBookingServiceView view, NewBookingHandler newBookingHandler,
      ServiceDao serviceDao, PermissionManager permissionManager) {
        super(view, permissionManager.getLoggedInUser().businessID());
        this.viewController = viewController;
        this.view = view;
        this.newBookingHandler = newBookingHandler;
        this.serviceDao = serviceDao;
        this.permissionManager = permissionManager;
    }

    @Override
    public void populateView() {
        super.populateView();
        User user = permissionManager.getLoggedInUser();
        HashMap<Service, ArrayList<Integer>> services = serviceDao
          .getAllServices(user.businessID());
        boolean success = true;
        for (Service service : services.keySet()) {
            if(service == null) {
                viewController.gotoError("There are no services!");
                success = false;
            }
        }
        if(success)
            view.displayServices(services);
    }

    public void setBookingService(int id, int duration) {
        newBookingHandler.setBookingService(id, duration);
        viewController.gotoNewBookingTime(newBookingHandler);
    }
}
