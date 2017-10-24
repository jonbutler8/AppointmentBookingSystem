package controller;


import controller.protocols.BusinessDao;
import controller.protocols.ViewController;
import model.NameIDTuple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import view.FXBusinessHoursView;
import view.FXCalendarComponent;
import view.protocols.BusinessHoursView;


public class BusinessHoursController {

    private static final Logger logger =
      LogManager.getLogger(BusinessHoursController.class);
    private BusinessHoursCalendarController childController;
    private BusinessHoursView view;
    private ViewController viewController;

    BusinessHoursController(ViewController viewController, 
            BusinessHoursView view, DaoManager manager, PermissionManager pm) {
        this.viewController = viewController;
        this.view = view;
        childController = new BusinessHoursCalendarController(manager, pm);
    }


    public void initializeCalendarController(FXCalendarComponent calendarView) {
        calendarView.initController(childController);
        childController.updateCalendar();
    }



}
