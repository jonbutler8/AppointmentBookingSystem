package view.protocols;

import controller.protocols.TimeblockController;
import model.TimeOfDay;

public interface ToggleableTimeblock {
    public void setEnabled(boolean enabled);
    public void initController(TimeblockController controller);
    public boolean getEnabled();
    public TimeOfDay getTime();
    public int getDay();
    public void setHighlighted(boolean b);
    public boolean getToggleable();
}
