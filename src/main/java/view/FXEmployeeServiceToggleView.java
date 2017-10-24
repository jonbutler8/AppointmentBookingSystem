package view;

import controller.EmployeeServiceToggleController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.BannerView;
import view.protocols.EmployeeServiceToggleView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXEmployeeServiceToggleView extends FXEmployeePickerView implements EmployeeServiceToggleView, BannerView {

    private static final int SERVICE_SPACING = 10;

    private static final Logger logger =
            LogManager.getLogger(FXEmployeeServiceToggleView.class);
    private EmployeeServiceToggleController controller;

    @FXML
    private ImageView banner;
    @FXML
    private VBox checkboxBox;

    public void setController(EmployeeServiceToggleController controller) {
        super.setController(controller);
        this.controller = controller;
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @Override
    public void setSubtitleText(String text) {

    }

    @Override
    public void setServices(ArrayList<CheckBox> serviceBoxes) {
        checkboxBox.setSpacing(SERVICE_SPACING);
        checkboxBox.getChildren().clear();
        checkboxBox.getChildren().addAll(serviceBoxes);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }
}