package view;

import controller.NewBookingsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.BookingSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.fxml.BookingSummaryBox;
import view.protocols.NewBookingsView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXNewBookingsView implements NewBookingsView, Initializable {

    private static final Logger logger =
      LogManager.getLogger(FXNewBookingsView.class);
    private NewBookingsController controller;

    @FXML
    private ImageView banner;
    @FXML
    private VBox newBookingsBox;

    public void setController(NewBookingsController controller) {
        this.controller = controller;
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @Override
    public void displayNoBookingsMessage() {
        Label messageLabel = new Label("No new bookings.");
        messageLabel.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(messageLabel, 10.0);
        AnchorPane.setRightAnchor(messageLabel, 0.0);
        AnchorPane.setBottomAnchor(messageLabel, 0.0);
        AnchorPane.setLeftAnchor(messageLabel, 0.0);
        AnchorPane messagePane = new AnchorPane(messageLabel);
        newBookingsBox.getChildren().add(messagePane);
    }

    @Override
    public void clearBookings() {
        newBookingsBox.getChildren().clear();
    }

    @Override
    public void addBooking(BookingSummary booking) {
        HBox newBookingBox = new HBox(new BookingSummaryBox(booking));
        AnchorPane spacer = new AnchorPane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button modifyButton = new Button("Modify");
        modifyButton.getStyleClass().add("orange-button");
        modifyButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Modify Booking?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> controller.modifyBooking(booking));
        });
        AnchorPane.setBottomAnchor(modifyButton, 5.0);
        AnchorPane.setRightAnchor(modifyButton, 5.0);
        AnchorPane modifyButtonPane = new AnchorPane(modifyButton);

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("red-button");
        deleteButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete Booking?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> controller.deleteBooking(booking));
        });
        AnchorPane.setBottomAnchor(deleteButton, 5.0);
        AnchorPane deleteButtonPane = new AnchorPane(deleteButton);

        newBookingBox.getChildren().addAll(spacer, modifyButtonPane, deleteButtonPane);
        newBookingsBox.getChildren().addAll(newBookingBox, new Separator());
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // TODO: Add errors for non-injected items
    }
}
