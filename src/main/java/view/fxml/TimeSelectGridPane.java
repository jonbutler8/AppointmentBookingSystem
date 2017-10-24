package view.fxml;

import controller.protocols.TimeSelectController;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.TimeOfDay;

import java.util.ArrayList;

public class TimeSelectGridPane extends VBox {

    public TimeSelectGridPane(TimeSelectController controller, TimeOfDay
      startTime, TimeOfDay endTime, int blockMins, ArrayList<TimeOfDay>
      availableTimes, TimeOfDay selectedTime) {
        GridPane timeLabels = new GridPane();
        GridPane timeSlots = new GridPane();
        timeSlots.setGridLinesVisible(true);
        timeLabels.getRowConstraints().add(new RowConstraints(20));
        timeSlots.getRowConstraints().add(new RowConstraints(25));

        for(int time = startTime.asInt(); time < endTime.asInt(); time += blockMins) {

            timeLabels.getColumnConstraints().add(new ColumnConstraints(38));
            timeSlots.getColumnConstraints().add(new ColumnConstraints(38));

            int i = (time - startTime.asInt()) / blockMins;
            AnchorPane pane = new AnchorPane();
            timeSlots.add(pane, i, 0);
            TimeOfDay currentTime = new TimeOfDay(time);
            if(currentTime.getMinutes() == 0) {
                Label label = new Label(String.format("%02d:00", currentTime.getHours()));
                timeLabels.add(label, i, 0);
            }

            boolean available = false;
            if(!availableTimes.isEmpty()) {
                if (availableTimes.get(0).asInt() == time) {
                    available = true;
                    availableTimes.remove(0);
                }
            }

            if(available) {
                final int clickedTime = time;
                pane.getStyleClass().add("available-time-block");
                pane.setOnMouseClicked(event ->
                  controller.setTime(clickedTime));

                if(selectedTime != null) {
                    if(selectedTime.asInt() == time) {
                        pane.getStyleClass().clear();
                        pane.getStyleClass().add("selected-time-block");
                    }
                }
            }
            else {
                pane.getStyleClass().add("unavailable-time-block");
            }
        }

        getChildren().setAll(timeLabels, timeSlots);
    }
}
