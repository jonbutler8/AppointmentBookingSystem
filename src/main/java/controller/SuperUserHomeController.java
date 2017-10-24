package controller;

import controller.protocols.ViewController;

public class SuperUserHomeController {

    private ViewController viewController;

    SuperUserHomeController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void gotoRegisterBusiness() {
        viewController.gotoRegisterBusiness();
    }
}
