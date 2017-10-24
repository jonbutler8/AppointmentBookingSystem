package controller;

import controller.protocols.EmployeeDao;
import controller.protocols.ViewController;
import model.EmployeeRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.EmployeePickerView;

import java.util.ArrayList;

public abstract class EmployeePickerController {

    private static final Logger logger =
      LogManager.getLogger(EmployeePickerController.class);

    private ViewController viewController;
    private EmployeeDao employeeDao;
    private EmployeePickerView view;
    protected PermissionManager permissionManager;
    private int lastEmployeeID = -1;

    EmployeePickerController(ViewController viewController, EmployeePickerView
      view, EmployeeDao employeeDao, PermissionManager permissionManager) {
        this.viewController = viewController;
        this.view = view;
        this.employeeDao = employeeDao;
        this.permissionManager = permissionManager;
    }

    private void populateEmployees() {
        ArrayList<EmployeeRecord> employees = employeeDao.getAllEmployees(
          permissionManager.getLoggedInUser().businessID());

        if(employees.isEmpty()) {
            viewController.gotoError("You have no employees!");
        }
        else {
            for (EmployeeRecord employee : employees) {
                view.addEmployeeItem(employee);
            }
        }
    }

    public int getEmployeeID() {
        return lastEmployeeID;
    }

    public void setDefaults() {
        populateEmployees();
    }

    public void checkForUpdate() {
        checkForUpdate(false);
    }

    protected abstract void applyUpdate(EmployeeRecord newEmployee);

    private void checkForUpdate(boolean forceUpdate) {
        EmployeeRecord newEmployee = view.getSelectedEmployee();

        // Can't update if we don't have enough information yet
        if (newEmployee == null) {
            return;
        }

        // Only update if the employee has changed or if force update is enabled
        if (newEmployee.id != lastEmployeeID || forceUpdate) {
            lastEmployeeID = newEmployee.id;
            applyUpdate(newEmployee);
        }
    }
}
