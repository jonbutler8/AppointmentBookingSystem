package controller;

import controller.protocols.BusinessDao;
import controller.protocols.UserDao;
import model.BusinessOwner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.FXBusinessRegisterView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class BusinessRegisterControllerTest {
    
    private static final String VALID_NAME      = "Massage place";
    private static final String INVALID_NAME    = "Invalid name";

    @Mock private FXBusinessRegisterView view;
    @Mock private PermissionManager mockPermissionManager;
    @Mock private UserDao mockUserDao;
    @Mock private BusinessDao mockBusinessDao;
    private BusinessRegisterController controller;

    @Before
    public void beforeEachTest() {
        BusinessOwner businessOwner;
        MockitoAnnotations.initMocks(this);

        when(mockUserDao.getBusinessID(anyInt())).thenReturn(1);
        businessOwner = new BusinessOwner(mockUserDao, mockBusinessDao, 1);
        when(mockPermissionManager.getLoggedInUser()).thenReturn(businessOwner);

        controller = new BusinessRegisterController(mockUserDao, mockBusinessDao);
        controller.setView(view);

        when(mockBusinessDao.businessExists(VALID_NAME)).thenReturn(false);
        when(mockBusinessDao.businessExists(INVALID_NAME)).thenReturn(true);
    }

    @Test
    public void attemptRegisterValid() {
        assertFalse(controller.checkBusinessExists(VALID_NAME));
    }

    @Test
    public void attemptRegisterInValid() {
        assertTrue(controller.checkBusinessExists(INVALID_NAME));
    }
}
