package view;

import controller.SuperUserHomeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import view.protocols.SUHomeView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXSUHomeView implements SUHomeView, Initializable {

    private SuperUserHomeController controller;

    public void setController(SuperUserHomeController controller) {
        this.controller = controller;
    }

    @FXML
    private void selectRegisterBusiness() {
        controller.gotoRegisterBusiness();
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    }
}
