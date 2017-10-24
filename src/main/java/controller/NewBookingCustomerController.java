package controller;

import controller.protocols.BannerViewController;
import controller.protocols.CustomerDao;
import controller.protocols.ViewController;
import controller.validator.NameValidator;
import controller.validator.PhoneNumberValidator;
import controller.validator.ValidationResult;
import controller.validator.protocols.Validator;
import model.NewBookingHandler;
import view.protocols.NewBookingCustomerView;

public class NewBookingCustomerController extends BannerViewController {

    private ViewController viewController;
    private NewBookingCustomerView view;
    private CustomerDao customerDao;
    private PermissionManager permissionManager;

    NewBookingCustomerController(ViewController viewController,
      NewBookingCustomerView view, CustomerDao customerDao, PermissionManager
      permissionManager) {
        super(view, permissionManager.getLoggedInUser()
          .businessID());
       this.viewController = viewController;
       this.view = view;
       this.customerDao = customerDao;
       this.permissionManager = permissionManager;
    }

    public void next(String firstName, String phoneNumber) {
        if(validate(firstName, phoneNumber)) {
            if(!customerDao.customerExists(firstName, phoneNumber,
              permissionManager.getLoggedInUser().userID())) {
                customerDao.createCustomer(firstName, phoneNumber,
                  permissionManager.getLoggedInUser().userID());
            }
            int id = customerDao.getCustomerID(firstName, phoneNumber);
            viewController.gotoNewBookingService(new NewBookingHandler(id,
              permissionManager.getLoggedInUser().businessID()));
        }
    }

    public boolean validate(String firstName, String phoneNumber) {
        Validator validator;
        ValidationResult validationResult;
        boolean success = true;

        validator = new NameValidator();
        validationResult = validator.validate(firstName);
        if(!validationResult.valid()) {
            view.setFirstNameMessage(validationResult.message());
            success = false;
        }

        validator = new PhoneNumberValidator();
        validationResult = validator.validate(phoneNumber);
        if(!validationResult.valid()) {
            view.setPhoneNumberMessage(validationResult.message());
            success = false;
        }

        return success;
    }
}
