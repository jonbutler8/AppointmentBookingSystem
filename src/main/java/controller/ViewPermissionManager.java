package controller;

import controller.protocols.PermissionDao;
import model.protocols.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewPermissionManager {

    private static final Logger logger =
      LogManager.getLogger(ViewPermissionManager.class);

    private PermissionDao permissionDao;
    private User user;

    ViewPermissionManager(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    void setLoggedInUser(User user) {
        this.user = user;
    }

    public boolean canGoToLogin() {
        if(user == null)
            return true;
        else {
            logger.debug("Verify permission: canGoToLogin");
            return permissionDao.checkKeyByUsername("canGoToLogin", user
              .getUsername());
        }
    }

    public boolean canGoToRegister() {
        if(user == null)
            return true;
        else {
            logger.debug("Verify permission: canGoToRegister");
            return permissionDao.checkKeyByUsername("canGoToRegister", user
              .getUsername());
        }
    }

    public boolean canGoToAddEmployee() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToAddEmployee");
            return permissionDao.checkKeyByUsername("canGoToAddEmployee", user
              .getUsername());
        }
    }

    public boolean canGoToBOHome() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToBOHome");
            return permissionDao.checkKeyByUsername("canGoToBOHome", user
              .getUsername());
        }
    }

    public boolean canGoToCustomerHome() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToCustomerHome");
            return permissionDao.checkKeyByUsername("canGoToCustomerHome", user
              .getUsername());
        }
    }

    public boolean canGoToEmployeeTimes() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToEmployeeTimes");
            return permissionDao.checkKeyByUsername("canGoToEmployeeTimes", user
              .getUsername());
        }
    }

    public boolean canGoToEmployeeService() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToEmployeeService");
            return permissionDao.checkKeyByUsername("canGoToEmployeeService", user
              .getUsername());
        }
    }

    public boolean canGoToBusinessHours() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToBusinessHours");
            return permissionDao.checkKeyByUsername("canGoToBusinessHours", user
              .getUsername());
        }
    }

    boolean canGoToSUHome() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToSUHome");
            return permissionDao.checkKeyByUsername("canGoToSUHome", user
              .getUsername());
        }
    }

    public boolean canGoToBusinessRegister() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToBusinessRegister");
            return permissionDao.checkKeyByUsername("canGoToBusinessRegister", user
              .getUsername());
        }
    }

    public boolean canGoToModifyServices() {
        if(user == null)
            return false;
        else {
            logger.debug("Verify permission: canGoToModifyServices");
            return permissionDao.checkKeyByUsername("canGoToModifyServices", user
              .getUsername());
        }
    }
}
