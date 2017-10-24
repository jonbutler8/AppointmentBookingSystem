package controller;

import controller.protocols.ViewController;
import model.NewBookingHandler;
import view.protocols.NewBookingSubmitView;

public class NewBookingSuccessController {

    private ViewController viewController;
    private NewBookingSubmitView view;

   NewBookingSuccessController(ViewController viewController,
      NewBookingSubmitView view, NewBookingHandler newBookingHandler) {
        this.viewController = viewController;
        this.view = view;
        if(newBookingHandler != null) {
            newBookingHandler.clear();
        }
    }

    void populateView() {
        view.displayMessage("Thanks for booking!");
    }

    public void done() {
        viewController.gotoHome();
    }
}
