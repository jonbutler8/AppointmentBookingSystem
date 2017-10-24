package model;

import controller.protocols.BusinessDao;
import controller.protocols.CustomerDao;
import controller.protocols.UserDao;
import model.protocols.AbstractUser;

public class Customer extends AbstractUser {

    public final int customerID;

    public Customer(CustomerDao customerDao, UserDao userDao, BusinessDao
      businessDao, int userID) {
        super(userDao, userID, businessDao, userDao.getBusinessID(userID));
        this.customerID = customerDao.getCustomerID(userID);
    }
}
