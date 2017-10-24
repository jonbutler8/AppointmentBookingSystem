package controller;

import java.util.ArrayList;

import controller.protocols.EmployeeDao;
import controller.protocols.TimeblockController;
import controller.protocols.WorkingTimesDao;
import model.EmployeeRecord;


public class WorkingCalendarController extends CalendarController {
    private EmployeeRecord currentEmployee;
    protected EmployeeDao employeeDao;

    public WorkingCalendarController(DaoManager manager, 
            PermissionManager permissionManager) {
        super(manager.workingTimesDao, manager.businessDao, permissionManager);
        this.employeeDao = manager.employeeDao;
    }

    public void setEmployee(EmployeeRecord employee) {
        currentEmployee = employee;
    }
    
    protected ArrayList<Integer> getDayTimes(int day) {
        return workingTimesDao.getEmployeeWorkTimes(currentEmployee.id, day);
    }
    

    protected String getCalendarSubtitle() {
        return currentEmployee.lastName + ", " + currentEmployee.firstName;
    }

    @Override
    protected TimeblockController getBlockController() {
        return new WorkingTimeblockController(this, 
                currentEmployee, workingTimesDao);
    }

    @Override
    protected String getCalendarTitle() {
        return "Working times";
    }
    
    @Override
    protected ArrayList<Integer> getDisabledDayTimes(int day) {
        return getComplementTimes(businessDao.getBusinessOperatingTimes(business.id, day));
    }

}
