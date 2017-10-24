package model.protocols;

import controller.protocols.BusinessDao;
import controller.protocols.UserDao;

public abstract class AbstractUser implements User {

    private UserDao userDao;
    private BusinessDao businessDao;
    private final int userID;
    private final int businessID;

    public AbstractUser(UserDao userDao, int userID, BusinessDao businessDao,
      int businessID) {
        this.userDao = userDao;
        this.businessDao = businessDao;
        this.userID = userID;
        this.businessID = businessID;
    }

    @Override
    public int userID() {
        return userID;
    }

    @Override
    public String getUsername() {
        return userDao.getUsername(userID);
    }

    @Override
    public int businessID() {
        return businessID;
    }

    @Override
    public String getBusinessName() {
        return businessDao.getBusinessName(businessID);
    }
}
