package controller;

import controller.protocols.UserDao;
import model.BusinessOwner;
import model.Customer;
import model.protocols.User;

/**
 * Dummy UserDao object used when testing.
 * Valid User credentials:
 * testcustomer/password
 * testbusinessowner/password
 */
public class DummyUserDao implements UserDao {

    public DummyUserDao() { }

    public boolean isValidLogin(String username, String password) {
        if(username.equals("testcustomer") ||
          username.equals("testbusinessowner")) {
            if(password.equals("password"))
                return true;
        }
        return false;
    }

    public boolean userExists(String username) {
        if(username.equals("testcustomer") ||
          username.equals("testbusinessowner"))
                return true;
        return false;
    }

    @Override
    public String getUserType(int id) {
        if(id == 1) {
            return "customer";
        }
        else if(id == 2) {
            return "businessOwner";
        }
        return null;
    }

    @Override
    public String getUsername(int id) {
        if(id == 1)
            return "testcustomer";
        else if(id == 2)
            return "testbusinessowner";
        return null;
    }

    @Override
    public User getUser(String username) {
        if(username.equals("testcustomer"))
            return new Customer(null, this, null, 1);
        else if(username.equals("testbusinessowner"))
            return new BusinessOwner(this, null, 1);
        else
            return null;
    }

    @Override
    public boolean createCustomer(String username, String password,
      String firstName, String lastName, String phoneNumber, String address,
      int businessID) {
        return true;
    }

    @Override
    public int getBusinessID(int id) {
        return 1;
    }

    @Override
    public boolean createBusinessUser(String username, String password,
      String businessName, String phoneNumber, String address) {
        return true;
    }
}
