package view;

import controller.EmployeePickerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import model.EmployeeRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.EmployeePickerView;
import view.protocols.FXView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXEmployeePickerView extends FXView implements EmployeePickerView, Initializable   {
    @FXML
    private ComboBox<EmployeeRecord> employeePicker;

    private static final Logger logger =
            LogManager.getLogger(FXEmployeePickerView.class);
    private EmployeePickerController controller;

    @Override
    public void addEmployeeItem(EmployeeRecord employee) {
        employeePicker.getItems().add(employee);
    }

    @Override
    public EmployeeRecord getSelectedEmployee() {
        return employeePicker.getSelectionModel().getSelectedItem();
    }

    public void setController(EmployeePickerController controller) {
        this.controller = controller;
        controller.setDefaults();
        employeePicker.setOnAction(listener ->
        controller.checkForUpdate());
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
}
