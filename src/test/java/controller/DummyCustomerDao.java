package controller;

import controller.protocols.CustomerDao;

public class DummyCustomerDao implements CustomerDao {

    public DummyCustomerDao() { }

    @Override
    public int getCustomerID(int userID) {
        if(userID == 1)
            return 1;
        else
            return 0;
    }

    @Override
    public int getCustomerID(String firstName, String phoneNumber) {
        if(firstName.equals("Test") && phoneNumber.equals("0123456789"))
            return 1;
        else
            return 0;
    }

    @Override
    public int getUserID(int customerID) {
        if(customerID == 1)
            return 1;
        else
            return 0;
    }

    @Override
    public boolean customerExists(String firstName, String phoneNumber, int
      businessID) {
        return firstName.equals("Test") && phoneNumber.equals("0123456789");
    }

    @Override
    public boolean createCustomer(String firstName, String phoneNumber, int
      businessID) {
        return !firstName.equals("Test") && !phoneNumber.equals("0123456789");
    }

    @Override
    public boolean customerHasAccount(String firstName, String phoneNumber,
      int businessID) {
        return firstName.equals("Test") && phoneNumber.equals("0123456789");
    }
}
