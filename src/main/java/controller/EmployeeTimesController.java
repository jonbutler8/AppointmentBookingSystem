package controller;

import controller.protocols.ViewController;
import model.EmployeeRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.FXCalendarComponent;
import view.protocols.EmployeeTimesView;


public class EmployeeTimesController extends EmployeePickerController {

    private static final Logger logger =
      LogManager.getLogger(EmployeeTimesController.class);
    private WorkingCalendarController childController;

    EmployeeTimesController(ViewController viewController, EmployeeTimesView
      view, DaoManager daoManager, PermissionManager permissionManager) {
        super(viewController, view, daoManager.employeeDao, permissionManager);
        childController = new WorkingCalendarController(daoManager, permissionManager);
    }

    public void initializeCalendarController(FXCalendarComponent calendarView) {
        calendarView.initController(childController);
    }

    @Override
    protected void applyUpdate(EmployeeRecord newEmployee) {
        childController.setEmployee(newEmployee);
        childController.updateCalendar();
    }
}