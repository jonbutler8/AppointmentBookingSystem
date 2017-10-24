package controller;

import controller.protocols.BusinessDao;
import controller.protocols.CustomerDao;
import controller.protocols.UserDao;
import controller.protocols.ViewController;
import model.BusinessOwner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.protocols.NewBookingCustomerView;

import static controller.validator.protocols.Validator.BLANK_ERROR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewBookingCustomerControllerTest {

    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_PHONE     = "0123456789";

    @Mock private ViewController mockViewController;
    @Mock private NewBookingCustomerView mockView;
    @Mock private CustomerDao mockCustomerDao;
    @Mock private PermissionManager mockPermissionManager;
    @Mock private UserDao mockUserDao;
    @Mock private BusinessDao mockBusinessDao;
    private NewBookingCustomerController controller;

    @Before
    public void beforeEachTest() {
        BusinessOwner businessOwner;
        MockitoAnnotations.initMocks(this);

        when(mockUserDao.getBusinessID(anyInt())).thenReturn(1);
        businessOwner = new BusinessOwner(mockUserDao, mockBusinessDao, 1);
        when(mockPermissionManager.getLoggedInUser()).thenReturn(businessOwner);

        controller = new NewBookingCustomerController(mockViewController,
          mockView, mockCustomerDao, mockPermissionManager);
    }

    @Test
    public void newBookingValidCustomer() {
        assertTrue(controller.validate(VALID_FIRST_NAME, VALID_PHONE));
    }

    @Test
    public void newBookingCustomerEmptyFirstName() {
        assertFalse(controller.validate("", VALID_PHONE));
        verify(mockView, times(1)).setFirstNameMessage(BLANK_ERROR);
    }

    @Test
    public void newBookingCustomerFirstNameContainsNumbers() {
        assertFalse(controller.validate("123", VALID_PHONE));
        verify(mockView, times(1)).setFirstNameMessage("Cannot contain numbers!");
    }

    @Test
    public void newBookingCustomerFirstNameContainsSpecials() {
        assertFalse(controller.validate("$^%", VALID_PHONE));
        verify(mockView, times(1)).setFirstNameMessage("Cannot contain special characters!");
    }

    @Test
    public void newBookingCustomerEmptyPhoneNumber() {
        assertFalse(controller.validate(VALID_FIRST_NAME, ""));
        verify(mockView, times(1)).setPhoneNumberMessage(BLANK_ERROR);
    }

    @Test
    public void newBookingCustomerInvalidPhoneNumber() {
        assertFalse(controller.validate(VALID_FIRST_NAME, "12345"));
        verify(mockView, times(1)).setPhoneNumberMessage("Must be a two digit area code (0X) followed by 8 digits");
    }
}
