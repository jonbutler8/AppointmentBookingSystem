package controller;

import java.util.ArrayList;

import controller.protocols.BusinessDao;
import controller.protocols.TimeblockController;
import controller.protocols.WorkingTimesDao;
import model.NameIDTuple;

public class BusinessHoursCalendarController extends CalendarController {

    public BusinessHoursCalendarController(DaoManager manager, PermissionManager pm) {
        super(manager.workingTimesDao, manager.businessDao, pm);
    }

    protected ArrayList<Integer> getDayTimes(int day) {
        return businessDao.getBusinessOperatingTimes(business.id, day);
    }
    

    protected String getCalendarSubtitle() {
        return business.name;
    }

    @Override
    protected TimeblockController getBlockController() {
        return new BusinessTimeblockController(this, 
                businessDao, workingTimesDao, business.id);
    }

    @Override
    protected String getCalendarTitle() {
        return "Business Hours";
    }
    
    @Override
    protected ArrayList<Integer> getDisabledDayTimes(int day) {
        return new ArrayList<Integer>();
    }

}
