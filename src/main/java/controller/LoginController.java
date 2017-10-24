package controller;

import controller.protocols.UserDao;
import controller.protocols.ViewController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.LoginView;

/**
 * The LoginController controls actions performed in the LoginView. The
 * LoginView is the first view which is displayed to the user, and can either
 * direct them to an appropriate home page on successful login or to the
 * register screen.
 */
public class LoginController {

    private static final Logger logger =
      LogManager.getLogger(LoginController.class);

    private ViewController viewController;
    private LoginView view;
    private UserDao userDao;
    private PermissionManager permissionManager;

    LoginController(ViewController viewController, LoginView view,
      PermissionManager permissionManager, UserDao userDao) {
        this.viewController = viewController;
        this.userDao = userDao;
        this.permissionManager = permissionManager;
        this.view = view;
    }

    /*
     * The register button was pressed in the view -- Switch to Register View.
     */
    public void gotoRegister() {
        viewController.gotoCustomerRegister();
    }

    /*
     * The Login button was pressed in the view -- Attempt to login
     */
    public boolean attemptLogin(String username, String password) {
        if (validPassword(password) & validUsername(username)) {
            if(userDao.isValidLogin(username, password)) {
                logger.info("Login successful");
                view.clearMessages();
                permissionManager.setLoggedInUser(userDao.getUser(username));
                viewController.gotoHome();
                return true;
            }
        }
        view.setLoginMessage("Incorrect Username or Password.");
        return false;
    }

    /*
     * Helper method for attemptLogin() to verify the password field is valid
     * before querying the database.
     */
    private boolean validPassword(String password) {
        boolean valid = true;
        if (password.isEmpty()){
            logger.debug("Empty password");
            view.setPasswordMessage("*");
            valid = false;
        }
        return valid;
    }

    /*
     * Helper method for attemptLogin() to verify the username field is valid
     * before querying the database.
     */
    private boolean validUsername(String username) {
        boolean valid = true;
        if (username.isEmpty()){
            logger.debug("Empty username");
            view.setUsernameMessage("*");
            valid = false;
        }
        return valid;
    }
}