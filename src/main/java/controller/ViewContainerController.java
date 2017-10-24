package controller;

import controller.protocols.ViewController;
import view.protocols.ViewContainerView;

public class ViewContainerController {

    private ViewController viewController;
    private ViewContainerView view;

    public ViewContainerController(ViewController viewController,
      ViewContainerView view) {
        this.viewController = viewController;
        this.view = view;
    }

    public void home() {
        viewController.gotoHome();
    }

    public void logout() {
        viewController.logout();
    }
}
