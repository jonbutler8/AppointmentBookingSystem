package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXBusinessRegisterView extends FXRegisterView {
    static final Logger logger =
            LogManager.getLogger(FXBusinessRegisterView.class);
    
    @FXML
    private TextField business;
    @FXML
    private Label businessError;

    @Override
    protected void registerSucessAction() {
        viewController.gotoBusinessRegisterSuccess();
        
    }
    
    public void setBusinessError(String error) {
        setError(error, businessError, business);  
    }

    
    
   
    

   
}
