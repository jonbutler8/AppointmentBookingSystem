package controller;

import controller.protocols.PermissionDao;
import model.protocols.User;

public class PermissionManager {

    public ViewPermissionManager view;
    public User user;

    public PermissionManager(PermissionDao permissionDao) {
        this.view = new ViewPermissionManager(permissionDao);
    }

    void setLoggedInUser(User user) {
        this.user = user;
        view.setLoggedInUser(user);
    }

    User getLoggedInUser() {
        return user;
    }

    void logout() {
        setLoggedInUser(null);
    }
}
