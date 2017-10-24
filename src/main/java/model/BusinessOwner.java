package model;

import controller.protocols.BusinessDao;
import controller.protocols.UserDao;
import model.protocols.AbstractUser;

public class BusinessOwner extends AbstractUser {

    public BusinessOwner(UserDao userDao, BusinessDao businessDao, int id) {
        super(userDao, id, businessDao, userDao.getBusinessID(id));
    }
}
