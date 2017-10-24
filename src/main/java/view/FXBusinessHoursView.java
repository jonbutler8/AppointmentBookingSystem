package view;

import controller.BusinessHoursController;
import controller.EmployeeTimesController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import view.protocols.BusinessHoursView;
import view.protocols.CalendarOuterView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class FXBusinessHoursView implements Initializable, CalendarOuterView, BusinessHoursView  {

    private static final Logger logger =
            LogManager.getLogger(FXBusinessHoursView.class);
    private BusinessHoursController controller;

    @FXML
    private AnchorPane calendarOuterBox;

    private FXCalendarComponent calendarView;

    public void setController(BusinessHoursController controller) {
        this.controller = controller;
        controller.initializeCalendarController(calendarView);
    }

    private void initializeCalendar() {
        CalendarInitializer init = new CalendarInitializer();
        calendarView = init.initialize(this, calendarOuterBox);
        calendarOuterBox.setVisible(true);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        initializeCalendar();
    }

    @Override
    public void setCalendarVisible(boolean visible) {
        calendarOuterBox.setVisible(visible);
        
    }
}