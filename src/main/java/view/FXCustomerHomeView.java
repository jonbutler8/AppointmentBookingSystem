package view;

import controller.CustomerHomeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.protocols.CustomerHomeView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXCustomerHomeView implements CustomerHomeView, Initializable {

    private CustomerHomeController controller;

    @FXML
    private ImageView banner;
    @FXML
    private Label businessName;

    public void setController(CustomerHomeController controller) {
        this.controller = controller;
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @Override
    public void setBusinessName(String name) {
        businessName.setText(name);
    }

    @FXML
    private void newBooking() {
        controller.gotoNewBooking();
    }

    @FXML
    private void viewBookings() {
        controller.gotoViewBookings();
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // TODO: Add errors for non-injected items
    }
}
