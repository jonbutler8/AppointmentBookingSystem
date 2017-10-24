package controller;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SQLiteUserDaoTest {
    private SQLiteUserDao sqliteUserDao = null;

    @Before
    public void beforeEachTest() {
        sqliteUserDao = new SQLiteUserDao(
          new DummyDatabaseController(true), new DummyCustomerDao(), null);
    }

    @Test
    public void isValidLogin() {
        assertTrue(sqliteUserDao.isValidLogin("testcustomer", "password"));
    }

    @Test
    public void isInvalidLogin() {
        assertFalse(sqliteUserDao.isValidLogin(null, null));
    }

    @Test
    public void validAddCustomer() {
        assertTrue(sqliteUserDao.createCustomer("username", "password",
          "Test", "Customer","0412345678", "123 Fake Street, Somewhere",
          1));
    }

    @Test
    public void invalidAddCustomer() {
        assertFalse(sqliteUserDao.createCustomer(null, null, null, null,
          null, null, 1));
    }

    @Test
    public void validUserExists() {
        assertTrue(sqliteUserDao.userExists("testcustomer"));
    }

    @Test
    public void invalidUserExists() {
        assertFalse(sqliteUserDao.userExists(null));
    }

    @Test
    public void validGetUserTypeCustomer() {
        assertTrue(sqliteUserDao.getUserType(1)
          .equals("customer"));
    }

    @Test
    public void validGetUserTypeBusinessOwner() {
        assertTrue(sqliteUserDao.getUserType(2)
          .equals("businessOwner"));
    }

    @Test
    public void invalidGetUserType() {
        assertTrue(sqliteUserDao.getUserType(0) == null);
    }
}
