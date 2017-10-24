package view;

import controller.BookingsSummaryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.BookingSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.fxml.BookingSummaryBox;
import view.protocols.BookingsSummaryView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXBookingsSummaryView extends FXNewBookingsView implements BookingsSummaryView, Initializable {

    private static final Logger logger =
            LogManager.getLogger(FXBookingsSummaryView.class);
    private BookingsSummaryController controller;

    @FXML
    private ImageView banner;
    @FXML
    private VBox newBookingsBox;

    public void setController(BookingsSummaryController controller) {
        this.controller = controller;
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @Override
    public void displayNoBookingsMessage() {
        Label messageLabel = new Label("No past bookings.");
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

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
}
