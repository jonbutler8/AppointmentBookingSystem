package app;

import controller.*;
import controller.protocols.DatabaseController;
import controller.protocols.DatabaseInitialiser;
import controller.protocols.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import view.FXViewInitialiser;
import view.protocols.ViewInitialiser;

/**
 * Appointment Booking System
 * Software Engineering Process and Tools - Semester 1 2017
 * RMIT Melbourne
 * Authors: Daniel Majoinen, Jon Butler, Brandon Tran
 */
public class ABS extends Application {

    /**
     * The entry point in to the program. Does nothing but pass the arguments
     * to the start method which allows the use of JavaFX.
     * @param args The programs arguments provided at execution.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method is initiated from from launch() and provides a stage to
     * initiate a JavaFX scene from. Here all top level objects are
     * instantiated with all implementation choices decided.
     * @param stage A stage used in JavaFX to create a scene/window.
     */
    @Override
    public void start(Stage stage) {
        // Database
        DatabaseManager databaseManager = new SQLiteDatabaseManager();
        DatabaseController databaseController =
          new SQLiteDatabaseController(databaseManager);
        DatabaseInitialiser databaseInitialiser =
          new SQLiteDatabaseInitialiser(databaseManager, databaseController);
        // Data Access Objects & Permissions
        DaoManager daoManager = new DaoManager(databaseController);
        PermissionManager permissionManager = new PermissionManager
          (daoManager.permissionDao);
        // View
        ViewInitialiser viewInitialiser = new FXViewInitialiser(stage);
        viewInitialiser.createViewController(daoManager, permissionManager);

        // Init program
        databaseInitialiser.init();
        viewInitialiser.init();
    }
}
