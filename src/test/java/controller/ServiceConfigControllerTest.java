package controller;

import controller.protocols.BusinessDao;
import controller.protocols.ServiceDao;
import controller.protocols.UserDao;
import model.BusinessOwner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.FXServiceConfigView;

import static controller.validator.protocols.Validator.BLANK_ERROR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServiceConfigControllerTest {

    private static final String VALID_SERVICE_NAME   = "Back Massage";
    private static final String INVALID_SERVICE_NAME = "Toe Massage";
    private static final String SERVICE_ALREADY_EXISTS = "That service " +
      "already exists";

    @Mock private FXServiceConfigView view;
    @Mock private ServiceDao mockServiceDao;
    @Mock private UserDao mockUserDao;
    @Mock private BusinessDao mockBusinessDao;
    @Mock private PermissionManager mockPermissionManager;
    private ServiceConfigController testController;

    @Before
    public void beforeEachTest() {
        BusinessOwner businessOwner;
        MockitoAnnotations.initMocks(this);

        when(mockUserDao.getBusinessID(anyInt())).thenReturn(1);
        businessOwner = new BusinessOwner(mockUserDao, mockBusinessDao, 1);
        when(mockPermissionManager.getLoggedInUser()).thenReturn(businessOwner);

        testController = new ServiceConfigController(view, mockServiceDao,
          mockPermissionManager);

        when(mockServiceDao.toggleServiceTime(anyInt(), anyInt())).thenReturn(true);
        when(mockServiceDao.createService(anyString(), anyInt())).thenReturn(true);
        when(mockServiceDao.serviceExists(INVALID_SERVICE_NAME, 1)).thenReturn(true);
        when(view.getEnteredServiceName()).thenReturn(VALID_SERVICE_NAME);
    }

    @Test
    public void addServiceValid() {
        testController.tryAddService();
        verify(view, times(1)).setFormMessage("Service \"" + VALID_SERVICE_NAME + "\" created");
    }

    @Test
    public void checkServiceExistsValid() {
        assertFalse(testController.checkServiceExists(VALID_SERVICE_NAME));
    }

    @Test
    public void checkServiceExistsInvalid() {
        assertTrue(testController.checkServiceExists(INVALID_SERVICE_NAME));
        verify(view, times(1)).setFormError(SERVICE_ALREADY_EXISTS);
    }

    @Test
    public void addServiceNameEmpty() {
        assertFalse(testController.validateServiceName(""));
        verify(view, times(1)).setFormError(BLANK_ERROR);
    }

    @Test
    public void addServiceNameHasNumbers() {
        assertFalse(testController.validateServiceName("1"));
        verify(view, times(1)).setFormError("Cannot contain numbers!");
    }

    @Test
    public void addServiceNameHasSpecials() {
        assertFalse(testController.validateServiceName("%&$^%"));
        verify(view, times(1)).setFormError("Cannot contain special characters!");
    }

    @Test
    public void addServiceNameTooLong() {
        assertFalse(testController.validateServiceName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        verify(view, times(1)).setFormError("Service name too long!");
    }
}
