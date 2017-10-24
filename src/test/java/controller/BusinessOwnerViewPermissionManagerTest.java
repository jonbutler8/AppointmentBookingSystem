package controller;

import controller.protocols.BusinessDao;
import controller.protocols.PermissionDao;
import controller.protocols.UserDao;
import model.BusinessOwner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class BusinessOwnerViewPermissionManagerTest {

    @Mock private PermissionDao mockDao;
    @Mock private UserDao mockUserDao;
    @Mock private BusinessDao mockBusinessDao;
    private ViewPermissionManager viewPermissionManager = null;

    @Before
    public void beforeEachTest() {
        BusinessOwner businessOwner;
        MockitoAnnotations.initMocks(this);

        when(mockUserDao.getBusinessID(anyInt())).thenReturn(1);
        businessOwner = new BusinessOwner(mockUserDao, mockBusinessDao, 1);

        viewPermissionManager = new ViewPermissionManager(mockDao);
        viewPermissionManager.setLoggedInUser(businessOwner);

        when(mockDao.checkKeyByUsername("canGoToAddEmployee", businessOwner.getUsername())).thenReturn(true);
    }

    @Test
    public void businessOwnerCanGoToLogin() {
        assertFalse(viewPermissionManager.canGoToLogin());
    }

    @Test
    public void businessOwnerCanGoToRegiser() {
        assertFalse(viewPermissionManager.canGoToRegister());
    }

    @Test
    public void businessOwnerCanNotGoToAddEmployee() {
        assertTrue(viewPermissionManager.canGoToAddEmployee());
    }
}
