package view;

import controller.ServiceConfigController;
import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Service;
import view.protocols.FXView;
import view.protocols.ServiceConfigView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXServiceConfigView extends FXView implements ServiceConfigView {

    @FXML
    private ImageView banner;
    @FXML
    private Button addButton, deleteButton;
    @FXML
    private TextField serviceName;
    @FXML
    private ComboBox<Service> serviceSelector;
    @FXML
    private GridPane timesGrid;
    @FXML
    private Label formError, formMessage;
    private static final int NUM_GRID_ROWS = 2;
    private static final int NUM_GRID_COLS = 2;

    private ServiceConfigController controller;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }

    public void initController(ServiceConfigController controller) {
        this.controller = controller;
        controller.setDefaults();
        createListeners();
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    private void createListeners() {
        serviceName.textProperty().addListener(listener -> {
            validateServiceName();
        }) ;
        serviceSelector.setOnAction(listener ->
        controller.checkForUpdate());

    }

    @FXML
    private void validateServiceName() {
        if (controller.validateServiceName(serviceName.getText())) {
            setError("", formError, serviceName);
        };
    }

    private void setError(String message, Label errorLabel, Styleable associated) {
        errorLabel.setText(message);
        if (message.length() == 0) {
            errorLabel.setManaged(false);
            if (associated != null) {
                associated.getStyleClass().remove("error");
            }
        }
        else {
            errorLabel.setManaged(true);
            if (associated != null) {
                ObservableList<String> styles = associated.getStyleClass();
                if (!styles.contains("error")) {
                    associated.getStyleClass().add("error");
                }
            }
        }
    }

    public void setFormMessage(String text) {
        formMessage.setText(text);
        if (!text.isEmpty()) {
            formMessage.setManaged(true);
        }
    }

    public String getEnteredServiceName() {
        return serviceName.getText();
    }
    public void setEnteredServiceName(String text) {
        serviceName.setText(text);
    }

    public void addService() {
        controller.tryAddService();
    }


    public void setServiceItems(ArrayList<Service> services) {
        serviceSelector.getItems().clear();
        serviceSelector.getItems().addAll(services);
    }

    public void deleteService() {
        controller.deleteService();
    }

    public void setFormError(String error) {
        setError(error, formError, serviceName);
    }

    public void setDurations(ArrayList<CheckBox> durationBoxes) {
        timesGrid.getChildren().clear();
        int count = 0;
        for (CheckBox duration : durationBoxes) {
            timesGrid.add(duration, count%NUM_GRID_ROWS, count/NUM_GRID_ROWS);
            count++;
        }
    }

    public void disableAllBoxes() {
        for (Node n : timesGrid.getChildren()) {
            n.setDisable(true);
        }
    }

    public void setDeleteText(String serviceName) {
        if (serviceName.isEmpty()) {
            deleteButton.setText("Delete existing service");
            deleteButton.setDisable(true);
        }
        else {
            deleteButton.setText("Delete " + serviceName);
            deleteButton.setDisable(false);
        }

    }

    public Service getSelectedService() {
        return serviceSelector.getSelectionModel().getSelectedItem();
    }

}
