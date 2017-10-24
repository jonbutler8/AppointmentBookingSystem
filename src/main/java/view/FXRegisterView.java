package view;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.CustomerRegisterController;
import controller.RegisterController;
import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class FXRegisterView extends FXRegisterBaseView {


    protected RegisterController registerController;
    @FXML
    private Button register;
    @FXML
    private TextField address;
    @FXML
    private TextField confirmPassword;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private TextField name;
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label confirmPasswordError;
    @FXML
    private Label phoneNumberError;
    @FXML
    private Label addressError;
    @FXML
    private Label formError;
    @FXML
    private Label nameError;

    
    static final Logger logger =
            LogManager.getLogger(FXRegisterView.class);

    public FXRegisterView() {
        super();
    }

    public void initController(RegisterController registerController) {
        this.registerController = registerController;
        registerController.setView(this);
        createListeners();
    
    }
    
    public void setNameError(String error, int nameIndex) {
        setFirstNameError(error);     
    }

    protected void createListeners() {
        username.textProperty().addListener(listener -> {
            validateUsername();
        }) ;
        password.textProperty().addListener(listener -> {
            validatePassword();
            validateConfirmPassword();
        }) ;
        confirmPassword.textProperty().addListener(listener -> {
            validateConfirmPassword();
        }) ;
        phoneNumber.textProperty().addListener(listener -> {
            validatePhoneNumber();
        }) ;
        address.textProperty().addListener(listener -> {
            validateAddress();
        }) ;
        name.textProperty().addListener(listener -> {
            validateFirstName();
        }) ;
    }

    public void setUsername(String username) {
        setField(username, this.username);
    }

    public void setPhoneNumber(String phoneNumber) {
        setField(phoneNumber, this.phoneNumber);
    }

    public void setFirstName(String firstName) {
        setField(firstName, this.name);
    }


    public String getAddress() {
        return address.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    public String getFirstName() {
        return name.getText();
    }


    public String getPhoneNumber() {
        return phoneNumber.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getConfirmPassword() {
        return confirmPassword.getText();
    }

    public void setAddress(String address) {
        setField(address, this.address);
    }

    public void setField(String newText, TextField field) {
        field.setText(newText);
    }

    @FXML
    private void validateUsername() {
        if (registerController.validateUsername(username.getText())) {
            setError("", usernameError, username);
        };
    }

    @FXML
    private void validatePassword() {
        if (registerController.validatePassword(password.getText())) {
            setError("", passwordError, password);
        };
    }

    @FXML
    private void validateConfirmPassword() {
        if (registerController.validateConfirmPassword(password.getText(), 
                confirmPassword.getText())) {
            setError("", confirmPasswordError, confirmPassword);
        };
    }

    @FXML
    private void validatePhoneNumber() {
        if (registerController.validatePhoneNumber(phoneNumber.getText())) {
            setError("", phoneNumberError, phoneNumber);
        };
    }

    @FXML
    private void validateAddress() {
        if (registerController.validateAddress(address.getText())) {
            setError("", addressError, address);
        };
    }

    @FXML
    private void validateFirstName() {
        if (registerController.validateFirstName(name.getText())) {
            setError("", nameError, name);
        };
    }



    public void setUsernameError(String error) {
        setError(error, usernameError, username);
    }

    public void setPasswordError(String error) {
        setError(error, passwordError, password);
    }

    public void setConfirmPasswordError(String error) {
        setError(error, confirmPasswordError, confirmPassword);
    }

    public void setPhoneNumberError(String error) {
        setError(error, phoneNumberError, phoneNumber);
    }

    public void setAddressError(String error) {
        setError(error, addressError, address);  
    }

    public void setFirstNameError(String error) {
        setError(error, nameError, name);  
    }



    protected void setError(String message, Label errorLabel, Styleable associated) {
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
    
    protected abstract void registerSucessAction();

    @FXML
    private void register() {
        if (registerController.trySubmitForm()) {
            registerSucessAction();
        }
        else {
            logger.info("Failed to register");
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert register != null : "fx:id=\"register\" was not injected.";
        assert username != null : "fx:id=\"username\" was not injected.";
        assert password != null : "fx:id=\"password\" was not injected.";
        assert confirmPassword != null :
          "fx:id=\"confirmPassword\" was not injected.";
    }

    public void setFormError(String error) {
        setError(error, formError, null);
        
    }



}