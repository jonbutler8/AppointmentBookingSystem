package controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoggedOutViewPermissionManagerTest {

    private ViewPermissionManager viewPermissionManager = null;

    @Before
    public void beforeEachTest() {
        viewPermissionManager = new ViewPermissionManager(new DummyPermissionDao());
        viewPermissionManager.setLoggedInUser(null);
    }

    @Test
    public void loggedOutCanGoToLogin() {
        assertTrue(viewPermissionManager.canGoToLogin());
    }

    @Test
    public void loggedOutCanGoToRegiser() {
        assertTrue(viewPermissionManager.canGoToRegister());
    }

    @Test
    public void loggedOutCanNotGoToAddEmployee() {
        assertFalse(viewPermissionManager.canGoToAddEmployee());
    }
}
