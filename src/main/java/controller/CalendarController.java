package controller;

import controller.protocols.BusinessDao;
import controller.protocols.TimeblockController;
import controller.protocols.WorkingTimesDao;
import model.NameIDTuple;
import model.TimeOfDay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.FXCalendarComponent;
import view.WorkingTimeBlock;

import java.util.ArrayList;

public abstract class CalendarController {

    private static final Logger logger = LogManager.getLogger(EmployeeTimesController.class);
    protected WorkingTimesDao workingTimesDao;
    protected BusinessDao businessDao;
    private FXCalendarComponent view;
    protected NameIDTuple business;
    public static final int BLOCK_MINUTES = 30;

    public CalendarController(WorkingTimesDao workingTimesDao, BusinessDao businessDao,
            PermissionManager permissionManager) {
        this.workingTimesDao = workingTimesDao;
        this.businessDao = businessDao;

        int businessID = permissionManager.getLoggedInUser().businessID();
        String businessName = permissionManager.getLoggedInUser().getBusinessName();
        business = new NameIDTuple(businessID, businessName);
    }

    public void setView(FXCalendarComponent view) {
        this.view = view;
    }

    // Hook methods for subclasses
    protected abstract String getCalendarTitle();
    protected abstract String getCalendarSubtitle();
    protected abstract TimeblockController getBlockController();
    protected abstract ArrayList<Integer> getDayTimes(int day);
    protected abstract ArrayList<Integer> getDisabledDayTimes(int day);

    /***
     * Given a list of integer timeblocks in a day, return the complement
     * @return an ArrayList of integers with all the times that weren't in the passed list
     */
    protected ArrayList<Integer> getComplementTimes(ArrayList<Integer> times) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        TimeOfDay time = new TimeOfDay(0);
        for (int j = 0; j < FXCalendarComponent.NUM_TIME_BLOCKS; j++) {
                result.add(time.asInt());
                time = time.plusMinutes(BLOCK_MINUTES);
        }
        result.removeAll(times);
        return result;
    }

    private void setCalendar(ArrayList<ArrayList<Integer>> times, ArrayList<ArrayList<Integer>> untoggleableTimes) {
        TimeOfDay firstDayShiftStart = new TimeOfDay(0);
        ArrayList<WorkingTimeBlock> timeBlocks = new ArrayList<WorkingTimeBlock>();
        TimeOfDay blockTime = firstDayShiftStart;
        view.setTitleText(getCalendarTitle());
        view.setSubtitleText(getCalendarSubtitle());

        TimeblockController blockCon = getBlockController();

        for (int i = 0; i < FXCalendarComponent.DAYS_IN_WEEK; i++) {
            for (int j = 0; j < FXCalendarComponent.NUM_TIME_BLOCKS; j++) {
                boolean toggled = false;
                boolean toggleable = true;
                if (times.get(i).contains(blockTime.asInt())) {
                    toggled = true;
                }
                else if (untoggleableTimes.get(i).contains(blockTime.asInt())) {
                    toggleable = false;
                }
                WorkingTimeBlock newBlock = new WorkingTimeBlock(blockTime, i+1, toggled, toggleable);
                newBlock.initController(blockCon);
                blockCon.addBlock(newBlock);
                timeBlocks.add(newBlock);

                blockTime = blockTime.plusMinutes(BLOCK_MINUTES);
            }
            blockTime = firstDayShiftStart;
        }
        view.setCalendar(timeBlocks);
    }


    public void updateCalendar() {
        ArrayList<ArrayList<Integer>> workingTimes = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> toggleableTimes = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < FXCalendarComponent.DAYS_IN_WEEK; i++) {
            logger.debug("Updating calendar");
            workingTimes.add(getDayTimes(i+1));
            logger.debug("Working time length: " + getDayTimes(i+1).size());
            toggleableTimes.add(getDisabledDayTimes(i+1));
        }
        setCalendar(workingTimes, toggleableTimes);
    }
}