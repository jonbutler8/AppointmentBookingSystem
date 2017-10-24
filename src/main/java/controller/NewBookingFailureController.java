package controller;

import controller.protocols.ViewController;
import view.protocols.NewBookingSubmitView;

public class NewBookingFailureController {

    private ViewController viewController;
    private NewBookingSubmitView view;

    NewBookingFailureController(ViewController viewController,
      NewBookingSubmitView view) {
        this.viewController = viewController;
        this.view = view;
    }

    public void populateView(String message) {
        view.displayMessage(message);
    }

    public void done() {
        viewController.gotoHome();
    }
}
