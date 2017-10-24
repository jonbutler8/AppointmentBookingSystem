package controller;

import controller.protocols.BusinessDao;
import controller.protocols.UserDao;
import model.NameIDTuple;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.FXCustomerRegisterView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CustomerRegisterControllerTest {
    @Mock FXCustomerRegisterView view;
    @Mock UserDao dao;
    @Mock UserDao yesDao;
    @Mock BusinessDao busDao;
    private CustomerRegisterController controller;

    // Mock form values
    private static final String VALID_ADDRESS = "133 abc street";
    private static final String VALID_USER = "abc123";
    private static final String VALID_PHONE = "0312341234";
    private static final String VALID_PASS = "abc123ABC!";
    private static final String VALID_FIRST = "Julian";
    private static final String VALID_LAST = "Stone";
    private NameIDTuple validBusiness;

    @Before
    public void beforeEachTest() {
        validBusiness = new NameIDTuple(1, "Lily Massage");
        // Initialize test subject with mock dependencies
        MockitoAnnotations.initMocks(this);
        controller = new CustomerRegisterController(dao, busDao);
        controller.setView(view);

        // Set up valid responses for the mock view
        when(view.getAddress()).thenReturn(VALID_ADDRESS);
        when(view.getPhoneNumber()).thenReturn(VALID_PHONE);
        when(view.getUsername()).thenReturn(VALID_USER);
        when(view.getPassword()).thenReturn(VALID_PASS);
        when(view.getConfirmPassword()).thenReturn(VALID_PASS);
        when(view.getFirstName()).thenReturn(VALID_FIRST);
        when(view.getLastName()).thenReturn(VALID_LAST);
        when(view.getBusiness()).thenReturn(validBusiness);

        // Set up a valid user response
        when(dao.userExists("abc123")).thenReturn(false);
        when(dao.createCustomer(VALID_USER, VALID_PASS, VALID_FIRST,
          VALID_LAST, VALID_PHONE, VALID_ADDRESS, 1)).thenReturn(true);

    }

    // Changes the test to another valid user
    public void setValidUser(String username, String password,
            String firstName, String lastName, String phoneNumber, String address) {
        when(view.getUsername()).thenReturn(username);
        when(view.getPassword()).thenReturn(password);
        when(view.getConfirmPassword()).thenReturn(password);
        when(view.getPhoneNumber()).thenReturn(phoneNumber);
        when(view.getAddress()).thenReturn(address);
        when(view.getFirstName()).thenReturn(firstName);
        when(view.getLastName()).thenReturn(lastName);
        when(dao.createCustomer(username, password, firstName, lastName,
          phoneNumber, address, 1)).thenReturn(true);
    }

    public void verifyBadForm() {
        assertFalse(controller.trySubmitForm());
        verify(dao, never()).createCustomer(
          anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt());
    }

    public void verifyBadUserTest() {
        verifyBadForm();
        verify(view, times(1)).setUsernameError(anyString());
    }

    public void verifyBadPasswordTest() {
        verifyBadForm();
        verify(view, times(1)).setPasswordError(anyString());
    }

    public void verifyBadFirstNameTest() {
        verifyBadForm();
        verify(view, times(1)).setNameError(anyString(), anyInt());
    }

    public void verifyBadLastNameTest() {
        verifyBadForm();
        verify(view, times(1)).setNameError(anyString(), anyInt());
    }

    public void verifyBadPhoneTest() {
        verifyBadForm();
        verify(view, times(1)).setPhoneNumberError(anyString());
    }

    public void setMockViewPasswords(String password) {
        when(view.getPassword()).thenReturn(password);
        when(view.getConfirmPassword()).thenReturn(password);
    }

    @Test
    // trySubmitFormTest() test 1
    public void trySubmitFormTestValid() {
        assertTrue(controller.trySubmitForm());
        verify(dao, times(1)).createCustomer(
                anyString(), anyString(), anyString(), anyString(), anyString
            (), anyString(), anyInt());
    }
    //Equivalent tests for each child's validation
    @Test
    public void validateUsernameTestValid() {
        assertTrue(controller.validateUsername(VALID_USER));
    }
    @Test
    public void validatePasswordTestValid() {
        assertTrue(controller.validatePassword(VALID_PASS));
    }
    @Test
    public void validateConfirmPasswordTestValid() {
        assertTrue(controller.validateConfirmPassword(VALID_PASS, VALID_PASS));
    }
    @Test
    public void validatePhoneNumberTestValid() {
        assertTrue(controller.validatePhoneNumber(VALID_PHONE));
    }
    @Test
    public void validateAddressTestValid() {
        assertTrue(controller.validateAddress(VALID_ADDRESS));
    }

    @Test
    // trySubmitFormTest() test 2
    public void trySubmitFormTestUsernameBlank() {
        when(view.getUsername()).thenReturn("");
        verifyBadForm();
        verify(view, times(1)).setUsernameError(CustomerRegisterController.BLANK_ERROR);
    }
    //Equivalent validateUsername() test
    @Test
    public void validateUsernameTestUsernameBlank() {
        assertFalse(controller.validateUsername(""));
    }


    @Test
    // trySubmitFormTest() test 3
    public void trySubmitFormTestUsernameTooShort() {
        when(view.getUsername()).thenReturn("a1");
        verifyBadUserTest();
    }
    @Test
    public void validateUsernameTestUsernameTooShort() {
        assertFalse(controller.validateUsername("a1"));
    }

    @Test
    // trySubmitFormTest() test 4
    public void trySubmitFormTestUsernameTooLong() {
        when(view.getUsername()).thenReturn("a11111111111111111111111111111");
        verifyBadUserTest();
    }
    @Test
    public void validateUsernameTestUsernameTooLong() {
        assertFalse(controller.validateUsername("a11111111111111111111111111111"));
    }

    @Test
    // trySubmitFormTest() test 5
    public void trySubmitFormTestUsernameStartsNumber() {
        when(view.getUsername()).thenReturn("123abc");
        verifyBadUserTest();
    }
    @Test
    public void validateUsernameTestUsernameStartsNumber() {
        assertFalse(controller.validateUsername("123abc"));
    }

    @Test
    // trySubmitFormTest() test 6
    public void trySubmitFormTestUsernameExists() {
        when(dao.userExists(VALID_USER)).thenReturn(true);
        verifyBadUserTest();
    }

    @Test
    // trySubmitFormTest() test 7
    public void trySubmitFormTestPasswordBlank() {
        setMockViewPasswords("");
        verifyBadForm();
        verify(view, times(1)).setPasswordError(CustomerRegisterController.BLANK_ERROR);
    }
    @Test
    public void validatePasswordTestBlank() {
        assertFalse(controller.validatePassword(""));
    }



    @Test
    // trySubmitFormTest() test 8
    public void trySubmitFormTestPasswordTooShort() {
        setMockViewPasswords("Aa!1");
        verifyBadPasswordTest();
    }
    @Test
    public void validatePasswordTestTooShort() {
        assertFalse(controller.validatePassword("Aa!1"));
    }

    @Test
    // trySubmitFormTest() test 9
    public void trySubmitFormTestPasswordTooLong() {
        setMockViewPasswords("Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1");
        verifyBadPasswordTest();
    }
    @Test
    public void validatePasswordTestTooLong() {
        assertFalse(controller.validatePassword("Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1Aa!1"));
    }

    @Test
    // trySubmitFormTest() test 10
    public void trySubmitFormTestPasswordMismatch() {
        when(view.getPassword()).thenReturn("I(Y4T(EHov!j5dj");
        verifyBadForm();
        verify(view, times(1)).setConfirmPasswordError(anyString());
    }
    @Test
    public void validateConfirmPasswordTestMismatch() {
        assertFalse(controller.validateConfirmPassword(VALID_PASS, "I(Y4T(EHov!j5dj"));
    }

    @Test
    // trySubmitFormTest() test 11
    public void trySubmitFormTestPasswordNoUpperCase() {
        setMockViewPasswords("abc123abc!");
        verifyBadPasswordTest();
    }
    @Test
    public void validatePasswordTestNoUpperCase() {
        assertFalse(controller.validatePassword("abc123abc!"));
    }

    @Test
    // trySubmitFormTest() test 12
    public void trySubmitFormTestPasswordNoLowerCase() {
        setMockViewPasswords("ABC123ABC!");
        verifyBadPasswordTest();
    }
    @Test
    public void validatePasswordTestNoLowerCase()  {
        assertFalse(controller.validatePassword("ABC123ABC!"));
    }

    @Test
    // trySubmitFormTest() test 13
    public void trySubmitFormTestPasswordNoNumber() {
        setMockViewPasswords("ABCabcABC!");
        verifyBadPasswordTest();
    }
    @Test
    public void validatePasswordTestNoNumber()  {
        assertFalse(controller.validatePassword("ABC123ABC!"));
    }

    @Test
    // trySubmitFormTest() test 14
    public void trySubmitFormTestPasswordNoSpecial() {
        setMockViewPasswords("abcABC123g");
        verifyBadPasswordTest();
    }
    @Test
    public void validatePasswordTestNoSpecial() {
        assertFalse(controller.validatePassword("abcABC123g"));
    }

    @Test
    // trySubmitFormTest() test 15
    public void trySubmitFormTestPhoneBlank() {
        when(view.getPhoneNumber()).thenReturn("");
        verifyBadForm();
        verify(view, times(1)).setPhoneNumberError(CustomerRegisterController.BLANK_ERROR);
    }
    @Test
    public void validatePhoneTestBlank() {
        assertFalse(controller.validatePhoneNumber(""));
    }


    @Test
    // trySubmitFormTest() test 16
    public void trySubmitFormTestPhoneTooShort() {
        when(view.getPhoneNumber()).thenReturn("049999999");
        verifyBadPhoneTest();
    }
    @Test
    public void validatePhoneTestTooShort() {
        assertFalse(controller.validatePhoneNumber("049999999"));
    }

    @Test
    // trySubmitFormTest() test 17
    public void trySubmitFormTestPhoneTooLong() {
        when(view.getPhoneNumber()).thenReturn("04999999999");
        verifyBadPhoneTest();
    }
    @Test
    public void validatePhoneTestTooLong() {
        assertFalse(controller.validatePhoneNumber("04999999999"));
    }

    @Test
    // trySubmitFormTest() test 18
    public void trySubmitFormTestPhoneNotAreaCode() {
        when(view.getPhoneNumber()).thenReturn("5499999999");
        verifyBadPhoneTest();
    }
    @Test
    public void validatePhoneTestNotAreaCode() {
        assertFalse(controller.validatePhoneNumber("5499999999"));
    }

    @Test
    // trySubmitFormTest() test 19
    public void trySubmitFormTestAddressBlank() {
        when(view.getAddress()).thenReturn("");
        verifyBadForm();
        verify(view, times(1)).setAddressError(CustomerRegisterController.BLANK_ERROR);
    }
    @Test
    public void validateAddressTestBlank() {
        assertFalse(controller.validateAddress(""));
    }

    @Test
    // trySubmitFormTest() test 20
    public void trySubmitFormTestValid2() {
        setValidUser("Jane123", "905AIfl!8^Afz", "Jackie", "Thomas", "0489965432", "Place Building Somewhere");
        assertTrue(controller.trySubmitForm());
        verify(dao, times(1)).createCustomer(
                "Jane123", "905AIfl!8^Afz", "Jackie", "Thomas", "0489965432", "Place Building Somewhere", 1);
    }

    @Test
    // trySubmitFormTest() test 21
    public void trySubmitFormTestValid3() {
        setValidUser("B94obbie", "I(Y4T(EHov!j5dj", "Robert", "Ericson", "0922769901", "2a Fleet Street Suburb 3000");
        assertTrue(controller.trySubmitForm());
        verify(dao, times(1)).createCustomer(
                "B94obbie", "I(Y4T(EHov!j5dj", "Robert", "Ericson", "0922769901", "2a Fleet Street Suburb 3000", 1);
    }

    @Test
    public void trySubmitFormTestFirstNameBlank() {
        when(view.getFirstName()).thenReturn("");
        verifyBadForm();
        verify(view, times(1)).setNameError(
                CustomerRegisterController.BLANK_ERROR, view.FIRST_NAME_INT);
    }
    @Test
    public void validateFirstNameTestBlank() {
        assertFalse(controller.validateFirstName(""));
    }

    @Test
    public void trySubmitFormTestLastNameBlank() {
        when(view.getLastName()).thenReturn("");
        verifyBadForm();
        verify(view, times(1)).setNameError(CustomerRegisterController.BLANK_ERROR, view.LAST_NAME_INT);
    }
    @Test
    public void validateLastNameTestBlank() {
        assertFalse(controller.validateLastName(""));
    }

    @Test
    public void trySubmitFormTestFirstNameNumbers() {
        when(view.getFirstName()).thenReturn("afd32523");
        verifyBadFirstNameTest();
    }
    @Test
    public void validateFirstNameTestNumbers() {
        assertFalse(controller.validateFirstName("afd32523"));
    }

    @Test
    public void trySubmitFormTestLastNameNumbers() {
        when(view.getLastName()).thenReturn("afd32523");
        verifyBadLastNameTest();
    }
    @Test
    public void validateLastNameTestNumbers() {
        assertFalse(controller.validateLastName("afd32523"));
    }

    @Test
    public void trySubmitFormTestFirstNameSpecial() {
        when(view.getFirstName()).thenReturn("Ale(xander");
        verifyBadFirstNameTest();
    }
    @Test
    public void validateFirstNameTestSpecial() {
        assertFalse(controller.validateFirstName("Ale(xander"));
    }

    @Test
    public void trySubmitFormTestLastNameSpecial() {
        when(view.getLastName()).thenReturn("Do)nehew");
        verifyBadLastNameTest();
    }
    @Test
    public void validateLastNameTestSpecial() {
        assertFalse(controller.validateLastName("Do)nehew"));
    }




}
