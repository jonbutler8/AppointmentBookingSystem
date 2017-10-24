package view;

import controller.EmployeeTimesController;
import controller.FXViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import model.EmployeeRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.EmployeeTimesView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXEmployeeTimesView extends FXEmployeePickerView implements EmployeeTimesView, Initializable  {

    private static final Logger logger =
            LogManager.getLogger(FXEmployeeTimesView.class);
    private static final String CALENDAR_XML = "FXScheduleCalendar.fxml";
    private EmployeeTimesController controller;

    @FXML
    private AnchorPane calendarOuterBox;

    private FXCalendarComponent calendarView;

    public void setController(EmployeeTimesController controller) {
        super.setController(controller);
        this.controller = controller;
        controller.initializeCalendarController(calendarView);
    }

    private void initializeCalendar() {
        CalendarInitializer init = new CalendarInitializer();
        calendarView = init.initialize(this, calendarOuterBox);
    }

    public void setCalendarVisible(boolean visible) {
        calendarOuterBox.setVisible(visible);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        setCalendarVisible(false);
        initializeCalendar();
    }
}