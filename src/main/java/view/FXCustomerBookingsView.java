package view;

import controller.CustomerBookingsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.BookingSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.fxml.BookingSummaryBox;
import view.protocols.CustomerBookingsView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXCustomerBookingsView extends FXBookingsSummaryView implements
  CustomerBookingsView, Initializable {

    private static final Logger logger =
            LogManager.getLogger(FXCustomerBookingsView.class);
    private CustomerBookingsController controller;

    @FXML
    private VBox newBookingsBox;

    @Override
    public void displayNoBookingsMessage() {
        Label messageLabel = new Label("No bookings.");
        messageLabel.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(messageLabel, 10.0);
        AnchorPane.setRightAnchor(messageLabel, 0.0);
        AnchorPane.setBottomAnchor(messageLabel, 0.0);
        AnchorPane.setLeftAnchor(messageLabel, 0.0);
        AnchorPane messagePane = new AnchorPane(messageLabel);
        newBookingsBox.getChildren().add(messagePane);
    }

    @Override
    public void addBooking(BookingSummary booking) {
        HBox newBookingBox = new HBox(new BookingSummaryBox(booking));
        newBookingsBox.getChildren().addAll(newBookingBox, new Separator());
    }

    public void setController(CustomerBookingsController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
}
