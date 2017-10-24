package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import view.protocols.FXView;

public class FXRegisterBaseView extends FXView implements Initializable {

    @FXML
    private Button login;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert login != null : "fx:id=\"login\" was not injected.";
    }
}
