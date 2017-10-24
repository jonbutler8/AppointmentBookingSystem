package controller.protocols;

import java.util.ArrayList;

import view.WorkingTimeBlock;
import view.protocols.ToggleableTimeblock;

public interface TimeblockController {

    //public void clickAction(E source);

    public void addBlock(WorkingTimeBlock newBlock);

    public void startAction(WorkingTimeBlock source);
    public void endAction(WorkingTimeBlock source);

    public void hoverAction(WorkingTimeBlock source);


    
    //TODO: Move methods up
}
