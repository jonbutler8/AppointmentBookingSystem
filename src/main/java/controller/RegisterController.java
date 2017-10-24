package controller;

import controller.protocols.UserDao;
import controller.validator.*;
import controller.validator.protocols.Validator;
import view.FXCustomerRegisterView;
import view.FXRegisterView;

public abstract class RegisterController {

    public static final String BLANK_ERROR = "This field is required";
    protected UserDao userDao;
    protected FXRegisterView view;

    public void setView(FXRegisterView view) {
        this.view = view;
    }

    protected void trimFormWhitespace() {
        view.setAddress(view.getAddress().trim());
        view.setPhoneNumber(view.getPhoneNumber().trim());
        view.setUsername(view.getUsername().trim());
        view.setFirstName((view.getFirstName()).trim());
    }

    boolean checkUserExists(String username) {
        if (userDao.userExists(username)) {
            view.setUsernameError("That username is taken");
            return true;
        }
        return false;
    }

    boolean validateForm(String username, String password, String confirmPassword, String phoneNumber,
            String address, String firstName) {
        if (!validatePassword(password) | !validateUsername(username)
                | !validateConfirmPassword(password, confirmPassword)
                | !validatePhoneNumber(phoneNumber) | !validateFirstName(firstName)
                | !validateAddress(address)) {
            setFormInvalidMessage();
            return false;
        }
        return true;
    }

    void setFormInvalidMessage() {
        view.setFormError("Could not register - review your input and try again.");
    }

    public boolean trySubmitForm() {
        view.setFormError("");
        trimFormWhitespace();
        return attemptRegister(view.getUsername(), view.getPassword(),
          view.getConfirmPassword(),
          view.getPhoneNumber(), view.getAddress(), view.getFirstName());
    }

    protected abstract boolean attemptRegister(String username, String password,
      String confirmPassword, String phoneNumber, String address,
      String firstName);

    public boolean validatePassword(String password) {
        view.setFormError("");
        Validator validator = new PasswordValidator();
        ValidationResult validationResult = validator.validate(password);
        if (!validationResult.valid()) {
            view.setPasswordError(validationResult.message());
        }
        return validationResult.valid();
    }

    public boolean validateUsername(String username) {
        view.setFormError("");
        Validator validator = new UsernameValidator();
        ValidationResult validationResult = validator.validate(username);
        if(!validationResult.valid()) {
            view.setUsernameError(validationResult.message());
        }
        return validationResult.valid();
    }

    public boolean validateConfirmPassword(String password, String confirmPassword) {
        view.setFormError("");
        boolean matches = password.equals(confirmPassword);
        if (!matches) {
            view.setConfirmPasswordError("Passwords must match");
        }
        return matches;
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        view.setFormError("");
        Validator validator = new PhoneNumberValidator();
        ValidationResult validationResult = validator.validate(phoneNumber);
        if(!validationResult.valid()) {
            view.setPhoneNumberError(validationResult.message());
        }
        return validationResult.valid();
    }

    public boolean validateAddress(String address) {
        view.setFormError("");
        Validator validator = new AddressValidator();
        ValidationResult validationResult = validator.validate(address);
        if(!validationResult.valid()) {
            view.setAddressError(validationResult.message());
        }
        return validationResult.valid();
    }

    boolean validateName(String name, int nameIndex) {
        view.setFormError("");
        Validator validator = new NameValidator();
        ValidationResult validationResult = validator.validate(name);
        if(!validationResult.valid()) {
            view.setNameError(validationResult.message(), nameIndex);
        }
        return validationResult.valid();
    }

    public boolean validateFirstName(String firstName) {
        return validateName(firstName, FXCustomerRegisterView.FIRST_NAME_INT);
    }

}