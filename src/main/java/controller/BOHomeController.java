package controller;

import controller.protocols.BannerViewController;
import controller.protocols.ViewController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.BOHomeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class BOHomeController extends BannerViewController {

    private static final Logger logger =
      LogManager.getLogger(BOHomeController.class);

    private BOHomeView view;
    private ViewController viewController;
    private PermissionManager permissionManager;

    BOHomeController(ViewController viewController, BOHomeView view,
      PermissionManager permissionManager) {
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

    public void addEmployee() {
        viewController.gotoAddEmployee();
    }

    public void businessHours() {
        viewController.gotoBusinessHours();
    }

    public void employeeTimes() {
        viewController.gotoEmployeeTimeManager();
    }
    
    public void modifyServices() {
        viewController.gotoModifyServices();
    }

    public void bookingSummary() {
        viewController.gotoBookingsSummary();
    }

    public void newBookings() {
        viewController.gotoNewBookings();
    }

    public void employeeSerivce() {
        viewController.gotoEmployeeServiceToggle();

    }

    public void createNewBooking() {
        viewController.gotoNewBookingCustomer();
    }

    public void uploadBanner(File source) {
        String extension = getFileExtension(source.getPath());
        File destination = new File("data/"+ permissionManager
          .getLoggedInUser().businessID() +"-banner." + extension);

        try {
            Files.copy(source.toPath(), destination.toPath(), REPLACE_EXISTING);
            viewController.gotoHome();
        }
        catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String getFileExtension(String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i+1);
        }
        return extension;
    }
}
