package model;

import controller.DummyCustomerDao;
import controller.DummyUserDao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class UserFactoryTest {
    private UserFactory userFactory = null;

    @Before
    public void beforeEachTest() {
        userFactory = new UserFactory(new DummyUserDao(), new
          DummyCustomerDao(), null);
    }

    @Test
    public void createCustomer() {
        assertTrue(userFactory.createUser(1)
          instanceof Customer);
    }

    @Test
    public void createBusinessOwner() {
        assertTrue(userFactory.createUser(2)
          instanceof BusinessOwner);
    }

    @Test
    public void createNullUser() {
        assertNull(userFactory.createUser(0));
    }
}
