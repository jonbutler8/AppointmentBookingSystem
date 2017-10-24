package view;

import controller.AddEmployeeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.protocols.AddEmployeeView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXAddEmployeeView implements AddEmployeeView, Initializable {

    private AddEmployeeController addEmployeeController;

    @FXML
    private ImageView banner;
    @FXML
    public Label firstNameLabel;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    public Label lastNameLabel;
    @FXML
    private Button addEmployee;
    @FXML
    private Label resultMessage;

    public void setController(AddEmployeeController addEmployeeController) {
        this.addEmployeeController = addEmployeeController;
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @FXML
    private void addEmployee() {
        addEmployeeController.addEmployee(firstName.getText(),
          lastName.getText());
    }

    @Override
    public void setSurnameMessage(String message){
        lastNameLabel.setText(message);
    }

    @Override
    public void setUsernameMessage(String message){
        firstNameLabel.setText(message);
    }

    @Override
    public void setResultMessage(String message){
        resultMessage.setText(message);
    }

    @Override
    public void clearMessages() {
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        resultMessage.setText("");
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert firstName != null : "fx:id=\"firstName\" was not injected.";
        assert lastName != null : "fx:id=\"lastName\" was not injected.";
        assert addEmployee != null : "fx:id=\"addEmployee\" was not injected.";

        firstName.textProperty().addListener(listener -> {
            clearMessages();
        });
        lastName.textProperty().addListener(listener -> {
            clearMessages();
        });
    }
}
