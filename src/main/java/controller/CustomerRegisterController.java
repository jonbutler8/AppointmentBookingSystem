package controller;

import controller.protocols.BusinessDao;
import controller.protocols.UserDao;
import model.NameIDTuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.FXCustomerRegisterView;
import view.FXRegisterView;

import java.util.ArrayList;

public class CustomerRegisterController extends RegisterController {
    private FXCustomerRegisterView view;
    private BusinessDao businessDao;

    private static final Logger logger = LogManager.getLogger(CustomerRegisterController.class);
    CustomerRegisterController(UserDao userDao, BusinessDao businessDao) {
        this.userDao = userDao;
        this.businessDao = businessDao;
    }

    public void setView(FXRegisterView newView) {
        super.setView(newView);
        this.view = (FXCustomerRegisterView) newView;
    }

    private void populateBusinesses() {
        ArrayList<NameIDTuple> businesses = businessDao.getAllBusinesses();
        for (NameIDTuple business : businesses) {
            view.addBusinessItem(business);
        }
    }

    public void setDefaults() {
        populateBusinesses();
    }

    @Override
    protected void trimFormWhitespace() {
        super.trimFormWhitespace();
        view.setLastName((view.getLastName()).trim());
    }

    @Override
    protected boolean attemptRegister(String username, String password, String confirmPassword, String phoneNumber,
        String address, String firstName) {
        String lastName = view.getLastName();
        NameIDTuple business = view.getBusiness();

        boolean success = false;

        logger.info("Attempting registration");

        boolean valid = validateForm(username, password, confirmPassword, phoneNumber, address, firstName)
                & validateLastName(lastName)
                & validateBusiness(business)
                && !checkUserExists(username);

        if (!valid) {
            setFormInvalidMessage();
        }
        else if (userDao.createCustomer(username, confirmPassword,
          firstName, lastName, phoneNumber, address, business.id)) {
            logger.info("Registration success");
            success = true;
        }

        return success;
    }

    public boolean validateBusiness(NameIDTuple selectedItem) {
        if (selectedItem == null) {
            view.setBusinessError(BLANK_ERROR);
            return false;
        }
        return true;
    }

    public boolean validateLastName(String lastName) {
        return validateName(lastName, FXCustomerRegisterView.LAST_NAME_INT);
    }
}
