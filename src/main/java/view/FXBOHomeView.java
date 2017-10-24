package view;

import controller.BOHomeController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.protocols.BOHomeView;
import view.protocols.BannerView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FXBOHomeView implements BOHomeView, BannerView {

    private BOHomeController controller;
    private Stage stage;

    @FXML
    private Label businessName;
    @FXML
    private ImageView banner;

    public void setController(BOHomeController controller) {
        this.controller = controller;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @Override
    public void setBusinessName(String name) {
        businessName.setText(name);
    }

    @FXML
    private void selectAddEmployee() {
        controller.addEmployee();
    }

    @FXML
    private void selectEmployeeTimes() {
        controller.employeeTimes();
    }

    @FXML
    private void selectBookingSummary() {
        controller.bookingSummary();
    }

    @FXML
    private void selectNewBookings() {
        controller.newBookings();
    }

    @FXML
    private void selectBusinessHours() {
        controller.businessHours();
    }

    @FXML
    public void selectEmployeeService() {
        controller.employeeSerivce();
    }

    @FXML
    private void selectCreateNewBooking() {
        controller.createNewBooking();
    }

    @FXML
    private void selectModifyServices() {
        controller.modifyServices();
    }

    @FXML
    private void selectModifyBanner() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("Image Files",
            "*.png"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            controller.uploadBanner(selectedFile);
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
}
