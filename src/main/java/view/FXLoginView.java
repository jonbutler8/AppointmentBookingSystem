package view;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import view.protocols.LoginView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXLoginView implements LoginView, Initializable {

    private LoginController loginController;
    @FXML
    public Label loginMessage;
    @FXML
    private TextField username;
    @FXML
    public Label usernameMessage;
    @FXML
    public Label passwordMessage;
    @FXML
    private PasswordField password;

    public void setController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void clearMessages() {
        passwordMessage.setText("");
        loginMessage.setText("");
        usernameMessage.setText("");
    }

    @Override
    public void setPasswordMessage(String message) {
        passwordMessage.setText(message);
    }

    @Override
    public void setLoginMessage(String message) {
        loginMessage.setText(message);
    }

    @Override
    public void setUsernameMessage(String message) {
        usernameMessage.setText(message);
    }

    @FXML
    private void login() {
        loginController.attemptLogin(username.getText(), password.getText());
    }

    @FXML
    private void register() {
        loginController.gotoRegister();
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        username.textProperty().addListener(listener -> {
            clearMessages();
        });
        password.textProperty().addListener(listener -> {
            clearMessages();
        });
    }
}