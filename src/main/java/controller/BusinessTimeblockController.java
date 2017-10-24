package controller;

import model.TimeOfDay;
import view.protocols.ToggleableTimeblock;
import controller.protocols.BusinessDao;
import controller.protocols.WorkingTimesDao;

public class BusinessTimeblockController extends controller.TimeblockController {
    private BusinessDao businessDao;
    private WorkingTimesDao workingDao;
    private int businessID = 0;

    public BusinessTimeblockController(CalendarController parentController, 
            BusinessDao businessDao, WorkingTimesDao workingDao, int businessID) {
        super(parentController);
        this.workingDao = workingDao;
        this.businessDao = businessDao;
        this.businessID = businessID;
    }



    @Override
    protected void applySingleTimeChange(ToggleableTimeblock view, boolean enable) {
        TimeOfDay time = view.getTime();
        int day = view.getDay();
        int timestamp = time.asInt();
        

        if (enable) {
            if (!businessDao.businessOperatingTimeExists(businessID, day, timestamp)) {
                businessDao.addOperatingTime(
                        businessID, day, timestamp);
            }
            else {
                //parentController.updateCalendar();
            }
        }
        else {
            
            businessDao.removeOperatingTime(
                    businessID, day, time.asInt());
            workingDao.removeWorkingTimeAllEmployees(day, time.asInt());
        }
        view.setEnabled(enable);
    }
    
}