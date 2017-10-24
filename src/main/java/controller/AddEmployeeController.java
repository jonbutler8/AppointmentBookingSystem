package controller;

import controller.protocols.BannerViewController;
import controller.protocols.EmployeeDao;
import controller.validator.NameValidator;
import controller.validator.ValidationResult;
import controller.validator.protocols.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.AddEmployeeView;

public class AddEmployeeController extends BannerViewController {

    private static final Logger logger =
      LogManager.getLogger(AddEmployeeController.class);

    private EmployeeDao employeeDao;
    private AddEmployeeView view;
    private PermissionManager permissionManager;

    AddEmployeeController(AddEmployeeView view, EmployeeDao employeeDao,
      PermissionManager permissionManager) {
        super(view, permissionManager.getLoggedInUser().businessID());
        this.view = view;
        this.employeeDao = employeeDao;
        this.permissionManager = permissionManager;
    }

    public boolean addEmployee(String firstName, String lastName) {
        boolean success = true;
        Validator validator = new NameValidator();

        ValidationResult validationResult = validator.validate(firstName);
        if(!validationResult.valid()) {
            view.setResultMessage("");
            view.setUsernameMessage(validationResult.message());
            success = false;
        }

        validationResult = validator.validate(lastName);
        if(!validationResult.valid()) {
            view.setResultMessage("");
            view.setSurnameMessage(validationResult.message());
            success = false;
        }

        if (employeeDao.employeeExists(firstName, lastName, permissionManager
        .getLoggedInUser().businessID())) {
            view.setResultMessage(firstName + " " + lastName + " already exists!");
            logger.info("Duplicate employee");
            success = false;
        }

        if(success){
            if (employeeDao.addEmployee(firstName, lastName, permissionManager
              .getLoggedInUser().businessID())) {
                view.setResultMessage("Successfully Added " + firstName + " " + lastName + ".");
                logger.info("Successfully added employee");
                success = true;
            }
        }
        return success;
    }
}
