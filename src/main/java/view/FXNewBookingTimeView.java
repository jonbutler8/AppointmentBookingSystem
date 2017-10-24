package view;

import controller.NewBookingTimeController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.EmployeeRecord;
import model.TimeOfDay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.fxml.TimeSelectGridPane;
import view.protocols.NewBookingTimeView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXNewBookingTimeView implements NewBookingTimeView {
    private static final Logger logger =
            LogManager.getLogger(FXNewBookingTimeView.class);
    private NewBookingTimeController controller;

    @FXML
    private ImageView banner;
    @FXML
    private DatePicker date;
    @FXML
    private Label service;
    @FXML
    private Label dayOfMonth;
    @FXML
    private Button previousDay;
    @FXML
    private Button nextDay;
    @FXML
    private Button submit;
    @FXML
    private AnchorPane employeePane;
    @FXML
    private AnchorPane timeSelectPane;
    @FXML
    private Label message;

    private ComboBox<EmployeeRecord> employeeRecordBox = new ComboBox<>();

    public void setController(NewBookingTimeController controller) {
        this.controller = controller;
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @FXML
    private void setDate() {
        controller.setDate(date.getValue());
    }

    @Override
    public void setDate(LocalDate date) {
        this.date.setValue(date);
    }

    @Override
    public void setService(String service) {
        this.service.setText(service);
    }

    @Override
    public void setEmployee(String employee) {
        Label employeeLabel = new Label(employee);
        AnchorPane.setTopAnchor(employeeLabel, 0.0);
        AnchorPane.setBottomAnchor(employeeLabel, 0.0);
        this.employeePane.getChildren().setAll(employeeLabel);
    }

    @Override
    public void setEmployee(List<EmployeeRecord> employees) {
        AnchorPane.setTopAnchor(employeeRecordBox, 0.0);
        AnchorPane.setBottomAnchor(employeeRecordBox, 0.0);
        employeeRecordBox.getItems().setAll(employees);
        employeeRecordBox.getStyleClass().add("employee-picker");
        employeeRecordBox.setOnAction(event -> {
            controller.setEmployee(employeeRecordBox.getSelectionModel()
              .getSelectedItem());
        });
        this.employeePane.getChildren().setAll(employeeRecordBox);
        employeeRecordBox.getSelectionModel().selectFirst();
    }

    @Override
    public void setPreviousDayOptionVisibility(boolean visible) {
        previousDay.setVisible(visible);
    }

    @Override
    public void setNextDayOptionVisibility(boolean visible) {
        nextDay.setVisible(visible);
    }

    @Override
    public void setNextDayOptionDisable(boolean disable) {
        nextDay.setDisable(disable);
    }

    @Override
    public void setSubmitDisable(boolean disable) {
        submit.setDisable(disable);
    }

    @Override
    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth.setText(dayOfMonth);
    }

    @Override
    public void displayDay(LocalDate currentDate, TimeOfDay
      startTime, TimeOfDay endTime, ArrayList<TimeOfDay>
      availableTimes, TimeOfDay selectedTime) {
        int blockMins = 30;

        if(startTime == null || endTime == null) {
            setMessage("Closed. Select another date.");
        }
        else {
            TimeSelectGridPane timeGrid = new TimeSelectGridPane(controller,
              startTime, endTime, blockMins, availableTimes, selectedTime);
            timeSelectPane.getChildren().setAll(timeGrid);
        }
    }

    @FXML
    public void gotoPreviousDay() {
        controller.gotoPreviousDay();
    }

    @FXML
    public void gotoNextDay() {
        controller.gotoNextDay();
    }

    @FXML
    public void goBack() {
        controller.goBack();
    }

    @FXML
    public void submit() {
        controller.submit();
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        final Callback<DatePicker, DateCell> dayCellFactory =
          new Callback<DatePicker, DateCell>() {
              @Override
              public DateCell call(final DatePicker datePicker) {
                  return new DateCell() {
                      @Override
                      public void updateItem(LocalDate item, boolean empty) {
                          super.updateItem(item, empty);

                          if (item.isBefore(LocalDate.now())) {
                              setDisable(true);
                              setStyle("-fx-background-color: #d0d2d1;");
                          }
                      }
                  };
              }
          };
        date.setDayCellFactory(dayCellFactory);
        date.getEditor().setOnMouseClicked(event -> date.show());
    }
}
