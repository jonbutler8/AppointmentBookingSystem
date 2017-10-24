package model;

import controller.protocols.BusinessDao;
import controller.protocols.CustomerDao;
import controller.protocols.UserDao;
import model.protocols.User;

public class UserFactory {

    private final UserDao userDao;
    private final CustomerDao customerDao;
    private final BusinessDao businessDao;

    public UserFactory(UserDao userDao, CustomerDao customerDao, BusinessDao
      businessDao) {
        this.userDao = userDao;
        this.customerDao = customerDao;
        this.businessDao = businessDao;
    }

    public User createUser(int userID) {
        String userType = userDao.getUserType(userID);
        if(userType != null) {
            if("businessOwner".equals(userType))
                return new BusinessOwner(userDao, businessDao, userID);
            if("customer".equals(userType))
                return new Customer(customerDao, userDao, businessDao, userID);
            if("superUser".equals(userType))
                return new SuperUser(userDao, businessDao, userID);
        }
        return null;
    }
}
