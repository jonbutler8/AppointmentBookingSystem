package controller;


import model.TimeOfDay;
import view.WorkingTimeBlock;
import view.protocols.ToggleableTimeblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class TimeblockController implements controller.protocols.TimeblockController {
    
    private static final Logger logger =
            LogManager.getLogger(ViewPermissionManager.class);
    
    
    protected CalendarController parentController;
;
    private Map<Integer, WorkingTimeBlock> timeblockMap = new HashMap<Integer, WorkingTimeBlock>();
    private ArrayList<ToggleableTimeblock> selectedBlocks = new ArrayList<ToggleableTimeblock>();
    
    private WorkingTimeBlock startBlock = null;
    protected WorkingTimeBlock endBlock = null;
    
    public TimeblockController(CalendarController parentController) {
        this.parentController = parentController;
    }
    
    @Override
    public void addBlock(WorkingTimeBlock block) {
        int minutesInDay = TimeOfDay.HOURS_IN_DAY * TimeOfDay.MINUTES_IN_HOUR;
        int dayMinutes = block.getDay() * minutesInDay;
        int offsetMinutes = block.getTime().asInt();
        int totalTime = dayMinutes + offsetMinutes;
        timeblockMap.put(totalTime, block);
    }
    
    private ArrayList<ToggleableTimeblock> buildTimeblockSelection(ToggleableTimeblock endSelectionBlock) {
        ArrayList<ToggleableTimeblock> result = new ArrayList<ToggleableTimeblock>();
        
        // Find the earlier time
        int startDay = startBlock.getDay();
        int endDay = endSelectionBlock.getDay();
        int startTime = startBlock.getTime().asInt();
        int endTime = endSelectionBlock.getTime().asInt();
        if (endDay < startDay || (endDay == startDay && endTime <= startTime)) {
            int tempStart = startTime;
            startTime = endTime;
            endTime = tempStart;
            
            int tempStartDay = startDay;
            startDay = endDay;
            endDay = tempStartDay;
        }
        
        int dayMinutes = (TimeOfDay.MINUTES_IN_HOUR * TimeOfDay.HOURS_IN_DAY);
        int lastPossibleShiftStart = dayMinutes - WorkingCalendarController.BLOCK_MINUTES;
        
        // Loop over each day to add all its times to the list
        for (int currentDay = startDay; currentDay <= endDay; currentDay++) {
            
            // Start at the beginning of the day if this is a day in the middle of the range
            if (currentDay != startDay) {
                startTime = 0;
            }
            
            // Determine what the start/end times of this day's selection are
            int currentDayEndTime = currentDay == endDay ? endTime : lastPossibleShiftStart;
            int currentDayStartTime = currentDay == startDay ? startTime : 0;
            
            // For each time block in the select of the day, add to the list
            for (int thisTime = currentDayStartTime; thisTime <= currentDayEndTime; 
                    thisTime += WorkingCalendarController.BLOCK_MINUTES) {
                ToggleableTimeblock thisBlock = timeblockMap.get((currentDay * dayMinutes) + thisTime);
                if (thisBlock != null && thisBlock.getToggleable()) {
                    result.add(thisBlock);
                }
            }
        }
        return result;
        
    }
    
    protected abstract void applySingleTimeChange(ToggleableTimeblock block, boolean enabled);
    
    protected void applyAllTimeChanges(boolean enabled) {
        ArrayList<ToggleableTimeblock> selection = buildTimeblockSelection(endBlock);
        for (ToggleableTimeblock block : selection) {
            applySingleTimeChange(block, enabled);
        }
    }
    

    
    public void startAction(WorkingTimeBlock source) {
        this.startBlock = source;
        wipeSelectedBlocks();
        selectedBlocks = new ArrayList<ToggleableTimeblock>();
    }
    
    private void wipeSelectedBlocks() {
        for (ToggleableTimeblock block : selectedBlocks) {
            block.setHighlighted(false);
        }
    }

    public void endAction(WorkingTimeBlock source) {
        this.endBlock = source;
        clickAction();
    }

    public void clickAction() {
        wipeSelectedBlocks();
        logger.debug("starting to apply changes to working timeblocks");
        applyAllTimeChanges(!startBlock.getEnabled());
        startBlock = null;
        endBlock = null;
        
    }
    
    public void hoverAction(WorkingTimeBlock source) {
        ArrayList<ToggleableTimeblock> newSelection = buildTimeblockSelection(source);
        ArrayList<ToggleableTimeblock> oldSelection = selectedBlocks;
        
        // Get the blocks that are no longer selected
        ArrayList<ToggleableTimeblock> toDeselect = new ArrayList<ToggleableTimeblock>(oldSelection);
        toDeselect.removeAll(newSelection);
       
        // Get the blocks that are new to the selection
        ArrayList<ToggleableTimeblock> toSelect = new ArrayList<ToggleableTimeblock>(newSelection);
        toSelect.removeAll(oldSelection);
        
        // Update the blocks that are no longer in the selection
        for (ToggleableTimeblock block : toDeselect) {
            block.setHighlighted(false);
        }
        
        // Update the newly selected blocks
        for (ToggleableTimeblock block : toSelect) {
            block.setHighlighted(true);
        }

        // Take the new selection as the new stored selection
        selectedBlocks = newSelection;   
    }


    
    
}