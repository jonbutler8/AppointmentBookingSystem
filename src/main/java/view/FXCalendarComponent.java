package view;

import controller.CalendarController;
import controller.WorkingCalendarController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import view.WorkingTimeBlock;
import view.protocols.CalendarOuterView;

public class FXCalendarComponent implements Initializable {

    private CalendarController controller;
    private CalendarOuterView parentView;
    public static final int DAYS_IN_WEEK = 7;
    public static final int NUM_TIME_BLOCKS = 48;
    public static final short START_AT_WEEK_START = 1;
    public static final short CENTER_AT_START_TIME = 0;
    
    public static final int FIRST_SHIFT_HRS = 9;
    public static final int FIRST_SHIFT_MINS = 0;
    public static final int LAST_SHIFT_HRS = 17;
    public static final int LAST_SHIFT_MINS = 30;
    
    @FXML
    private GridPane calendarGrid;
    
    @FXML
    private Label titleLabel, subtitleLabel;
    
    public void setCalendar(ArrayList<WorkingTimeBlock> times) {
        parentView.setCalendarVisible(false);
        int count = 0;
        calendarGrid.getChildren().clear();
        for (int day = 0; day < FXCalendarComponent.DAYS_IN_WEEK; day++) {
            for (int halfHour = 0; halfHour < FXCalendarComponent.NUM_TIME_BLOCKS; halfHour++) {
                calendarGrid.add(times.get(count++), halfHour, day);
            }
        }

        parentView.setCalendarVisible(true);
    }
    
    public void setTitleText(String title) {
        this.titleLabel.setText(title);
    }
    public void setSubtitleText(String subtitle) {
        this.subtitleLabel.setText(subtitle);
    }

    public void initController(CalendarController controller) {
        this.controller = controller;
        controller.setView(this);
    }



    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }



    public void setParentView(CalendarOuterView parentView) {
        this.parentView = parentView;
        
    }
}
