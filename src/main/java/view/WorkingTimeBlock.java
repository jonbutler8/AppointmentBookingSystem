package view;

import controller.protocols.TimeblockController;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import model.TimeOfDay;
import view.protocols.ToggleableTimeblock;

public class WorkingTimeBlock extends AnchorPane implements ToggleableTimeblock {
    private TimeOfDay startTime;
    private boolean passed;
    private int day;
    private boolean enabledStatus;
    private boolean toggleableStatus;
    private TimeblockController controller;
    
    public WorkingTimeBlock(TimeOfDay startTime, int day, boolean enabled) {
        this(startTime, day, enabled, true);
    }
    
    public WorkingTimeBlock(TimeOfDay startTime, int day, boolean enabled, boolean toggleable) {
        this.startTime = startTime;
        this.day = day;

        getStyleClass().add("timeblock");
        
        this.enabledStatus = enabled;
        this.toggleableStatus = toggleable;
        if (enabled) {
            getStyleClass().add("scheduled-time");
        }
        
        if (toggleable) {
            setListeners();
        }
        else {
            getStyleClass().add("passed");
        }
        
    }
    
    private void setListeners() {
        setOnDragDetected(listener -> {
            ClipboardContent content = new ClipboardContent();
            Dragboard board = this.startDragAndDrop(TransferMode.COPY);
            content.putString("");
            board.setContent(content);
            controller.startAction(this);     
        }) ;
        setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            controller.hoverAction(this);
        });
        
        setOnDragDropped(listener -> controller.endAction(this));
        
        setOnMouseClicked(listener -> {
            controller.startAction(this);
            controller.endAction(this);
        }) ;
    }

    public TimeOfDay getTime() {
        return startTime;
    }
    
    public int getDay() {
        return day;
    }
    
    public void initController(TimeblockController controller) {
        this.controller = controller;
    }

    public void setEnabled(boolean enable) {
        if (enable) {
            if (!enabledStatus) {
                getStyleClass().add("scheduled-time");
            }
        }
        else {
            if (enabledStatus) {
                getStyleClass().remove("scheduled-time");
            }
        }
        enabledStatus = enable;
    }
    
    public void setHighlighted(boolean highlighted) {
        if (highlighted) {
            getStyleClass().add("highlighted");
        }
        else {
            getStyleClass().remove("highlighted");
        }
    }
    
    public boolean getEnabled() {
        return this.enabledStatus;
    }

    public boolean getToggleable() {
        return toggleableStatus;
    }
    
}
