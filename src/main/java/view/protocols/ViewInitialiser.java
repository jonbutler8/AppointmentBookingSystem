package view.protocols;

import controller.DaoManager;
import controller.PermissionManager;
import controller.protocols.ViewController;

public interface ViewInitialiser {
    /**
     * Creates a ViewController allowing other classes to switch between
     * scenes. This is executed from Start() and shouldn't be used elsewhere.
     * @return Returns a ViewController to use when switching scenes or NULL if
     * an error occurs.
     */
    ViewController createViewController(DaoManager daoManager,
      PermissionManager permissionManager);
    /**
     * Initialises the window, defining the min width & min height, and
     * determining the first scene to display.
     */
    void init();
}
