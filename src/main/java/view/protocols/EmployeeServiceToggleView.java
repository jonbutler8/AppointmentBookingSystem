package view.protocols;

import java.util.ArrayList;

import javafx.scene.control.CheckBox;

public interface EmployeeServiceToggleView extends EmployeePickerView {

    public void setSubtitleText(String text);

    public void setServices(ArrayList<CheckBox> serviceBoxes);

}
