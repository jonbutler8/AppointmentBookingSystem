package controller;

import controller.protocols.BusinessDao;
import controller.protocols.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.FXBusinessRegisterView;
import view.FXRegisterView;

public class BusinessRegisterController extends RegisterController {

    private FXBusinessRegisterView view;
    private BusinessDao businessDao;

    private static final Logger logger = LogManager.getLogger(CustomerRegisterController.class);

    BusinessRegisterController(UserDao userDao, BusinessDao businessDao) {
        this.userDao = userDao;
        this.businessDao = businessDao;
    }

    @Override
    public void setView(FXRegisterView newView) {
        super.setView(newView);
        this.view = (FXBusinessRegisterView) newView;
    }

    @Override
    protected boolean attemptRegister(String username, String password, String confirmPassword, String phoneNumber,
        String address, String name) {
        boolean success = false;

        logger.info("Attempting registration");

        boolean valid =
                validateForm(username, password, confirmPassword, phoneNumber, address, name)
                & !checkUserExists(username) & !checkBusinessExists(name);

        if (!valid) {
            setFormInvalidMessage();
        }
        else if (userDao.createBusinessUser(username, password,
                name, phoneNumber, address)) {
            logger.info("Registration success");
            success = true;
        }
        return success;
    }

    boolean checkBusinessExists(String businessName) {
        if (businessDao.businessExists(businessName)) {
            view.setBusinessError("That business name is taken");
            return true;
        }
        return false;
    }


}
