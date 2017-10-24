package controller;

import controller.protocols.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Booking;
import model.NewBookingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.*;

import java.io.IOException;

public class FXViewController implements ViewController {

    private static final Logger logger =
      LogManager.getLogger(FXViewController.class);

    public static final String FXML_RESOURCE_DIR = "/fxml/";
    private static final String INSUFFICIENT_PERMISSIONS_MSG =
      "Insufficient permission: ";

    private Stage stage;
    private FXViewContainer container;
    private PermissionManager permissionManager;
    private ViewControllerFactory viewControllerFactory;

    public FXViewController(Stage stage, FXViewContainer viewContainer,
      DaoManager daoManager, PermissionManager permissionManager) {
        this.stage = stage;
        this.container = viewContainer;
        this.permissionManager = permissionManager;
        this.viewControllerFactory = new ViewControllerFactory(this,
          daoManager, permissionManager);
    }

    @Override
    public void gotoAddEmployee() {
        if(permissionManager.view.canGoToAddEmployee()) {
            logger.info("Switch scene to addEmployee");
            FXAddEmployeeView view = (FXAddEmployeeView)
              switchScene("AddEmployee.fxml");
            if(view != null) {
                AddEmployeeController controller = viewControllerFactory.getController(view);
                view.setController(controller);
                controller.populateView();
                setTitle("ABS - Add Employee");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToAddEmployee");
    }

    private void gotoBOHome() {
        if(permissionManager.view.canGoToBOHome()) {
            logger.info("Switch scene to BOHome");
            FXBOHomeView view = (FXBOHomeView)
              switchScene("BOHome.fxml");
            if(view != null) {
                BOHomeController controller = viewControllerFactory
                  .getController(view);
                view.setController(controller);
                view.setStage(stage);
                controller.populateView();
                setTitle("ABS - Business Owner");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToBoHome");
    }

    @Override
    public void gotoBookingsSummary() {
        logger.info("Switch scene to BookingsSummary");
        FXBookingsSummaryView view = (FXBookingsSummaryView)
          switchScene("BookingsSummary.fxml");
        if(view != null) {
            BookingsSummaryController controller = viewControllerFactory
              .getController(view);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - Bookings Summary");
        }
    }

    @Override
    public void gotoCustomerBookings() {
        logger.info("Switch scene to CustomerBookings");
        FXCustomerBookingsView view = (FXCustomerBookingsView)
          switchScene("CustomerBookings.fxml");
        if(view != null) {
            CustomerBookingsController controller = viewControllerFactory
              .getController(view);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - Bookings");
        }
    }

    private void gotoCustomerHome() {
        logger.info("Switch scene to CustomerHome");
        FXCustomerHomeView view = (FXCustomerHomeView)
          switchScene("CustomerHome.fxml");
        if(view != null) {
            CustomerHomeController controller = viewControllerFactory
              .getController(view);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - Home");
        }
    }

    @Override
    public void gotoCustomerRegister() {
        if(permissionManager.view.canGoToRegister()) {
            logger.info("Switch scene to customer registration");
            FXCustomerRegisterView customerRegister = (FXCustomerRegisterView)
              switchScene("CustomerRegister.fxml");
            if(customerRegister != null) {
                customerRegister.initViewController(this);
                customerRegister.initController(
                  viewControllerFactory.getCustomerRegisterController());
                setTitle("ABS - Customer Registration");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToRegister");
    }

    @Override
    public void gotoEmployeeTimeManager() {
        if(permissionManager.view.canGoToEmployeeTimes()) {
            logger.info("Switch scene to employee time manager");
            FXEmployeeTimesView view = (FXEmployeeTimesView)
              switchScene("EmployeePicker.fxml");
            if(view != null) {
                view.setController(viewControllerFactory.getController(view));
                setTitle("ABS - Manage Employee Schedules");
                stage.sizeToScene();
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToEmployeeTimes");
    }

    @Override
    public void gotoHome() {
        boolean loggedIn = false;
        if(permissionManager.view.canGoToSUHome()) {
            gotoSUHome();
            loggedIn = true;
        }
        else if(permissionManager.view.canGoToBOHome()) {
            gotoBOHome();
            loggedIn = true;
        }
        else if (permissionManager.view.canGoToCustomerHome()) {
            gotoCustomerHome();
            loggedIn = true;
        }
        else {
            logger.info(INSUFFICIENT_PERMISSIONS_MSG +
              "canGoToSUHome, canGoToBoHome, canGoToCustomerHome");
        }
        if (loggedIn) {
            container.setUserDisplay(permissionManager.getLoggedInUser()
              .getUsername() + "  |");
            container.setButtonsVisible(true);
        }
    }

    @Override
    public void gotoLogin() {
        if(permissionManager.view.canGoToLogin()) {
            logger.info("Switch scene to Login");
            FXLoginView view = (FXLoginView) switchScene("Login.fxml");
            if(view != null) {
                view.setController(viewControllerFactory.getController(view));
                setTitle("ABS - Login");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToLogin");
    }

    @Override
    public void gotoModifyBooking(Booking booking) {
        logger.info("Switch scene to ModifyBookings");
        FXModifyBookingView view = (FXModifyBookingView)
          switchScene("ModifyBooking.fxml");
        if(view != null) {
            ModifyBookingController controller = viewControllerFactory
              .getController(view, booking);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - Modify Booking");
        }
    }

    @Override
    public void gotoNewBookingCustomer() {
        logger.info("Switch scene to NewBookingCustomer");
        FXNewBookingCustomerView view = (FXNewBookingCustomerView)
          switchScene("NewBookingCustomer.fxml");
        if(view != null) {
            NewBookingCustomerController controller = viewControllerFactory
              .getController(view);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - New Booking Customer Selection");
        }
    }

    @Override
    public void gotoError(String message) {
        logger.info("Switch scene to Error");
        FXNewBookingFailureView view = (FXNewBookingFailureView)
          switchScene("NewBookingFailure.fxml");
        if(view != null) {
            NewBookingFailureController controller = viewControllerFactory
              .getController(view);
            view.setController(controller);
            controller.populateView(message);
            setTitle("ABS - Error!");
        }
    }

    @Override
    public void gotoNewBookings() {
        logger.info("Switch scene to NewBookings");
        FXNewBookingsView view = (FXNewBookingsView)
          switchScene("NewBookings.fxml");
        if (view != null) {
            NewBookingsController controller = viewControllerFactory
              .getController(view);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - New Bookings");
        }
    }

    @Override
    public void gotoNewBookingService(NewBookingHandler newBookingHandler) {
        logger.info("Switch scene to NewBookingService");
        FXNewBookingServiceView view = (FXNewBookingServiceView)
          switchScene("NewBookingService.fxml");
        if(view != null) {
            NewBookingServiceController controller = viewControllerFactory
              .getController(view, newBookingHandler);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - New Booking Service Selection");
        }
    }

    @Override
    public void gotoNewBookingSuccess(NewBookingHandler newBookingHandler) {
        logger.info("Switch scene to NewBookingSuccess");
        FXNewBookingSuccessView view = (FXNewBookingSuccessView)
          switchScene("NewBookingSuccess.fxml");
        if(view != null) {
            NewBookingSuccessController controller = viewControllerFactory
              .getController(view, newBookingHandler);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - Success!");
        }
    }

    @Override
    public void gotoNewBookingTime(NewBookingHandler newBookingHandler) {
        logger.info("Switch scene to NewBookingTime");
        FXNewBookingTimeView view = (FXNewBookingTimeView)
          switchScene("NewBookingTime.fxml");
        if(view != null) {
            NewBookingTimeController controller = viewControllerFactory
              .getController(view, newBookingHandler);
            view.setController(controller);
            controller.populateView();
            setTitle("ABS - New Booking Time Selection");
        }
    }

    @Override
    public void gotoSUHome() {
        logger.info("Switch scene to SUHome.");
        FXSUHomeView view = (FXSUHomeView)
          switchScene("SUHome.fxml");
        if(view != null) {
            SuperUserHomeController controller = viewControllerFactory
              .getController(view);
            view.setController(controller);
            setTitle("ABS - Choose User Type");
        }
    }

    @Override
    public void gotoRegisterSuccess() {
        if(permissionManager.view.canGoToRegister()) {
            logger.info("Switch scene to register success");
            FXRegisterBaseView registerSuccess = (FXRegisterBaseView)
              switchScene("RegisterSuccess.fxml");
            if(registerSuccess != null) {
                registerSuccess.initViewController(this);
                setTitle("ABS - Registration successful");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToRegister");
    }


    @Override
    public void gotoBusinessHours() {
        if(permissionManager.view.canGoToBusinessHours()) {
            logger.info("Switch scene to business hours management");
            FXBusinessHoursView view = (FXBusinessHoursView)
              switchScene("BusinessHours.fxml");
            if(view != null) {
                view.setController(viewControllerFactory.getController(view));
                setTitle("ABS - Manage Business Hours");
                stage.sizeToScene();
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToEmployeeTimes");

    }


    @Override
    public void gotoEmployeeServiceToggle() {
        if(permissionManager.view.canGoToEmployeeService()) {
            logger.info("Switch scene to employee service toggle");
            FXEmployeeServiceToggleView serviceToggle = (FXEmployeeServiceToggleView)
              switchScene("EmployeeServiceToggler.fxml");
            if(serviceToggle != null) {
                EmployeeServiceToggleController controller = viewControllerFactory.
                        getController(serviceToggle);
                serviceToggle.setController(controller);
                controller.populateView();
                setTitle("ABS - Set Employee Services");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToEmployeeService");

    }

    @Override
    public void logout() {
        permissionManager.logout();
        gotoLogin();
        container.setUserDisplay("");
        container.setButtonsVisible(false);
    }

    private void setTitle(String title) {
        if(stage != null)
            stage.setTitle(title);
    }

    private Initializable switchScene(String fxml) {
        Initializable view;
        FXMLLoader loader;
        Node node;

        try {
            loader = new FXMLLoader(getClass().getResource(FXML_RESOURCE_DIR +
              fxml));
            node = loader.load();
            /* Anchor to all sides */
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            container.getContainer().getChildren().setAll(node);
            view = loader.getController();
            if(view == null)
                throw new NullPointerException();
            return view;
        }
        catch(IOException | NullPointerException e) {
            logger.fatal(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void gotoBusinessRegisterSuccess() {
        if(permissionManager.view.canGoToBusinessRegister()) {
            logger.info("Switch scene to register success");
            FXRegisterBaseView registerSuccess = (FXRegisterBaseView)
              switchScene("BusinessRegisterSuccess.fxml");
            if(registerSuccess != null) {
                registerSuccess.initViewController(this);
                setTitle("ABS - Business registration successful");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToBusinessRegister");
    }

    @Override
    public void gotoRegisterBusiness() {
        if(permissionManager.view.canGoToBusinessRegister()) {
            logger.info("Switch scene to register business.");
            FXBusinessRegisterView view = (FXBusinessRegisterView)
              switchScene("BusinessRegister.fxml");
            if(view != null) {
                view.initViewController(this);
                BusinessRegisterController controller = viewControllerFactory.getController(view);
                view.initController(controller);
                setTitle("ABS - Business Registration");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToBusinessRegister");
    }

    @Override
    public void gotoModifyServices() {
        if(permissionManager.view.canGoToModifyServices()) {
            logger.info("Switch scene to modify services.");
            FXServiceConfigView view = (FXServiceConfigView)
              switchScene("ServiceConfiguration.fxml");
            if(view != null) {
                view.initViewController(this);
                ServiceConfigController controller = viewControllerFactory.getController(view);
                view.initController(controller);
                controller.populateView();
                setTitle("ABS - Add/Modify Services");
            }
        }
        else
            logger.info(INSUFFICIENT_PERMISSIONS_MSG + "canGoToModifyServices");

    }


}
