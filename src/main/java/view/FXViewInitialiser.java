package view;

import controller.DaoManager;
import controller.FXViewController;
import controller.PermissionManager;
import controller.ViewContainerController;
import controller.protocols.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.ViewInitialiser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Creates the initial scene to populate in the future and acts as a factory to
 * create a ViewController to allow switching of scenes.
 */
public class FXViewInitialiser implements ViewInitialiser {

    private static final Logger logger =
      LogManager.getLogger(FXViewInitialiser.class);
    private static final int WINDOW_MIN_HEIGHT = 600;
    private static final int WINDOW_MIN_WIDTH = 800;
    private static final String FXML_RESOURCE_DIR = "/fxml/";
    private static final String VIEW_CONTAINER_FXML = "ViewContainer.fxml";

    private ViewController viewController;
    private Stage stage;

    public FXViewInitialiser(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initialises the window, defining the min width & min height, and
     * determining the first scene to display.
     */
    @Override
    public void init() {
        viewController.gotoLogin();
        stage.setMinWidth(WINDOW_MIN_WIDTH);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.show();
    }

    /**
     * Creates a ViewController allowing other classes to switch between
     * scenes. This is executed from Start() and shouldn't be used elsewhere.
     * @return Returns a ViewController to use when switching scenes or NULL if
     * an error occurs.
     */
    @Override
    public ViewController createViewController(DaoManager daoManager,
      PermissionManager permissionManager) {
        FXViewContainer view;

        view = (FXViewContainer) createScene();
        try {
            if(view == null)
                throw new NullPointerException();
            this.viewController = new FXViewController(stage, view,
              daoManager, permissionManager);
            view.setController(new ViewContainerController(viewController,
              view));
            return viewController;
        }
        catch(NullPointerException e) {
            logger.fatal(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Creates the initial scene for future population.
     * @return Returns the view controller for the container scene.
     */
    private Initializable createScene() {
        FXMLLoader loader;
        InputStream in;
        Parent pane;
        Scene scene;

        loader = new FXMLLoader();
        in = getClass().getResourceAsStream(FXML_RESOURCE_DIR +
            VIEW_CONTAINER_FXML);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(getClass().getResource(FXML_RESOURCE_DIR +
            VIEW_CONTAINER_FXML));
        try {
            pane = loader.load(in);
            scene = new Scene(pane);
            stage.setScene(scene);
            in.close();
        }
        catch(IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return loader.getController();
    }
}
