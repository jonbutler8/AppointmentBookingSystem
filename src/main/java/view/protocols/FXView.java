package view.protocols;

import controller.protocols.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public abstract class FXView implements Initializable {
    protected ViewController viewController;

    public void initViewController(ViewController viewController) {
        this.viewController = viewController;
    }

    @FXML
    private void gotoLogin() {
        viewController.gotoLogin();
    }
    
    @FXML
    private void gotoHome() {
        viewController.gotoHome();
    }
}
