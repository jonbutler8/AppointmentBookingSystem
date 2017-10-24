package view;

import controller.NewBookingCustomerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.protocols.NewBookingCustomerView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXNewBookingCustomerView implements NewBookingCustomerView, Initializable {

    private NewBookingCustomerController controller;

    @FXML
    private ImageView banner;
    @FXML
    private TextField firstName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label phoneNumberLabel;

    public void setController(NewBookingCustomerController controller) {
        this.controller = controller;
    }

    @Override
    public void setFirstNameMessage(String message) {
        firstNameLabel.setText(message);
        if (message.isEmpty()) {
            firstNameLabel.setManaged(false);
        }
        else {
            firstNameLabel.setManaged(true);
        }
    }

    @Override
    public void setPhoneNumberMessage(String message) {
        phoneNumberLabel.setText(message);
        if (message.isEmpty()) {
            firstNameLabel.setManaged(false);
        }
        else {
            firstNameLabel.setManaged(true);
        }
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @FXML
    public void next() {
        controller.next(firstName.getText(), phoneNumber.getText());
    }

    private void clearMessages() {
        firstNameLabel.setText("");
        phoneNumberLabel.setText("");
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        clearMessages();
        firstName.textProperty().addListener(listener -> {
            clearMessages();
        });
        phoneNumber.textProperty().addListener(listener -> {
            clearMessages();
        });
    }
}
