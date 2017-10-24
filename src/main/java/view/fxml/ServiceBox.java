package view.fxml;

import controller.NewBookingServiceController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Service;

import java.util.ArrayList;

public class ServiceBox extends VBox {

    private static double ZERO_ANCHOR = 0.0;
    private static double BUTTON_SPACING = 5.0;
    private static String BUTTON_SUFFIX = " mins.";

    public ServiceBox(NewBookingServiceController controller, Service service, ArrayList<Integer> durations) {
        Label serviceName = new Label(service.name());
        AnchorPane serviceNamePane = new AnchorPane(serviceName);

        HBox durationButtons = new HBox();
        for(Integer duration : durations) {
            Button durationButton = new Button(duration + BUTTON_SUFFIX);
            AnchorPane durationButtonPane = new AnchorPane(durationButton);

            AnchorPane.setLeftAnchor(durationButton, ZERO_ANCHOR);
            AnchorPane.setRightAnchor(durationButton, ZERO_ANCHOR);
            durationButton.getStyleClass().add("blue-button");

            HBox.setHgrow(durationButtonPane, Priority.ALWAYS);
            durationButtons.getChildren().add(durationButtonPane);

            durationButton.setOnAction(event -> {
                controller.setBookingService(service.id(), duration);
            });
        }

        AnchorPane.setLeftAnchor(serviceName, ZERO_ANCHOR);
        AnchorPane.setRightAnchor(serviceName, ZERO_ANCHOR);
        serviceName.getStyleClass().add("service-label");

        durationButtons.setSpacing(BUTTON_SPACING);

        getChildren().setAll(serviceNamePane, durationButtons);
        getStyleClass().add("service-box");
    }
}
