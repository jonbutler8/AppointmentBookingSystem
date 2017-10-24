package controller;

import model.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class CustomerViewPermissionManagerTest {

    private ViewPermissionManager viewPermissionManager = null;

    @Before
    public void beforeEachTest() {
        viewPermissionManager = new ViewPermissionManager(new DummyPermissionDao());
        viewPermissionManager.setLoggedInUser(new Customer(
          new DummyCustomerDao(), new DummyUserDao(), null, 1));
    }

    @Test
    public void customerCanGoToLogin() {
        assertFalse(viewPermissionManager.canGoToLogin());
    }

    @Test
    public void customerCanGoToRegiser() {
        assertFalse(viewPermissionManager.canGoToRegister());
    }

    @Test
    public void customerCanNotGoToAddEmployee() {
        assertFalse(viewPermissionManager.canGoToAddEmployee());
    }
}
