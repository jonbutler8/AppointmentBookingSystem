package controller;

import java.util.ArrayList;

import controller.protocols.WorkingTimesDao;
import model.EmployeeRecord;
import model.TimeOfDay;
import view.protocols.ToggleableTimeblock;

public class WorkingTimeblockController extends TimeblockController {
    private WorkingTimesDao timesDao;
    private EmployeeRecord employee;
    
    public WorkingTimeblockController(CalendarController parentController, EmployeeRecord employee, 
            WorkingTimesDao timesDao) {
        super(parentController);
        this.timesDao = timesDao;
        this.employee = employee;
    }

    
    protected void applySingleTimeChange(ToggleableTimeblock view, boolean enable) {
        TimeOfDay time = view.getTime();
        int day = view.getDay();
        int timestamp = time.asInt();
        

        if (enable) {
            if (!timesDao.workingTimeExists(employee.id, day, timestamp)) {
                timesDao.addWorkingTime(
                        employee.id, day, timestamp);
            }
            else {
                //parentController.updateCalendar();
            }
        }
        else {
            timesDao.removeWorkingTime(
                    employee.id, day, time.asInt());
        }
        view.setEnabled(enable);
    }
}
