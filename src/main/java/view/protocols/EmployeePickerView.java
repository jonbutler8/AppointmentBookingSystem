package view.protocols;

import model.EmployeeRecord;

public interface EmployeePickerView {
    void addEmployeeItem(EmployeeRecord employee);
    EmployeeRecord getSelectedEmployee();
}
