package view;

import controller.ViewContainerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.ViewContainerView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXViewContainer implements ViewContainerView, Initializable {

    private static final Logger logger =
      LogManager.getLogger(FXViewContainer.class);

    private ViewContainerController controller;
    @FXML
    private AnchorPane container;
    @FXML
    private Label globalMessage;
    @FXML
    private Button logout, home;
    @FXML
    private Label userDisplay;

    public void setController(ViewContainerController controller) {
        this.controller = controller;
    }

    public AnchorPane getContainer() {
        return container;
    }

    public void setUserDisplay(String message) {
        userDisplay.setText(message);
    }

    public Label getGlobalMessage() {
        return globalMessage;
    }

    public void setButtonsVisible(boolean disabled) {
        logout.setVisible(disabled);
        home.setVisible(disabled);
    }

    @FXML
    public void backToHome() {
       controller.home();
    }
    @FXML
    public void logout() {
        controller.logout();
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert container != null : "fx:id=\"container\" was not injected.";
    }
}
