package model;

import controller.protocols.BusinessDao;
import controller.protocols.UserDao;
import model.protocols.AbstractUser;

public class SuperUser extends AbstractUser {

    public SuperUser(UserDao userDao, BusinessDao businessDao, int userID) {
        super(userDao, userID, businessDao, userDao.getBusinessID(userID));
    }
}
