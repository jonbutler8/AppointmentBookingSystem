package view;

import controller.NewBookingFailureController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import view.protocols.NewBookingFailureView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXNewBookingFailureView implements NewBookingFailureView, Initializable {

    @FXML
    private Label message;

    private NewBookingFailureController controller;

    public void setController(NewBookingFailureController controller) {
        this.controller = controller;
    }

    @Override
    public void displayMessage(String message) {
        this.message.setText(message);
    }

    @FXML
    public void done() {
        controller.done();
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // TODO: Add errors for non-injected items

    }
}
