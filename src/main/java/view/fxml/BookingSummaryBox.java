package view.fxml;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.BookingSummary;

public class BookingSummaryBox extends HBox {

    public BookingSummaryBox(BookingSummary booking) {
        Label user = new Label(""+booking.customerName);
        Label date = new Label("Date: "+booking.date);
        Label employee = new Label("Employee: "+booking.employeeName);
        Label time = new Label("Time: " + booking.startTime);
        Label service = new Label("Service: "+ booking.service);

        VBox leftColumn = new VBox(user, date, employee);
        VBox rightColumn = new VBox(new Label(), time, service);

        leftColumn.getStyleClass().add("left-column");
        rightColumn.getStyleClass().add("right-column");

        getStyleClass().add("booking-summary");
        getChildren().setAll(leftColumn, rightColumn);
    }
}
