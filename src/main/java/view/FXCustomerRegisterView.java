package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.CustomerRegisterController;
import controller.RegisterController;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.EmployeeRecord;
import model.NameIDTuple;

public class FXCustomerRegisterView extends FXRegisterView {
    static final Logger logger =
            LogManager.getLogger(FXCustomerRegisterView.class);
    
    private CustomerRegisterController registerController;

    @FXML
    private ComboBox<NameIDTuple> businessSelect;
    @FXML
    private Label businessError;
    
    @FXML
    private TextField secondName;
    @FXML
    private Label secondNameError;

      
    
    public static final int FIRST_NAME_INT = 0;
    public static final int LAST_NAME_INT = 1;
    
    @Override
    public void initController(RegisterController cont) {
        super.initController(cont);
        this.registerController = (CustomerRegisterController) cont;
        registerController.setDefaults();
    }
    

    
    @Override
    protected void createListeners() {
        super.createListeners();
        secondName.textProperty().addListener(listener -> {
            validateLastName();
        }) ;
    }
    
    
    

    public void setLastName(String lastName) {
        setField(lastName, this.secondName);
    }

    public String getLastName() {
        return secondName.getText();
    }
    
    @FXML
    private void validateBusiness() {
        if (registerController.validateBusiness(businessSelect.getSelectionModel().getSelectedItem())) {
            setError("", businessError, businessSelect);
        };
    }
    
    public void setLastNameError(String error) {
        setError(error, secondNameError, secondName);  
    }
    public void setBusinessError(String error) {
        setError(error, businessError, businessSelect);  
    }
    
    @Override
    public void setNameError(String error, int nameIndex) {
        switch (nameIndex) {
            case FIRST_NAME_INT:
                setFirstNameError(error); break;
            case LAST_NAME_INT:
                setLastNameError(error); break;
        }
        
    }
    
    @FXML
    private void validateLastName() {
        if (registerController.validateLastName(secondName.getText())) {
            setError("", secondNameError, secondName);
        }
    }

    public void addBusinessItem(NameIDTuple business) {
        businessSelect.getItems().add(business);
        
    }

    public NameIDTuple getBusiness() {
        return businessSelect.getSelectionModel().getSelectedItem();
    }



    @Override
    protected void registerSucessAction() {
        viewController.gotoRegisterSuccess();
    }
}
