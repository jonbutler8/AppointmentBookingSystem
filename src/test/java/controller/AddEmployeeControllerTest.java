package controller;

import controller.protocols.BusinessDao;
import controller.protocols.EmployeeDao;
import controller.protocols.UserDao;
import model.BusinessOwner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.FXAddEmployeeView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class AddEmployeeControllerTest {
    private static final String validFirstName = "John";
    private static final String validLastName = "Thompson";
    private static final String existingFirstName = "Jane";
    private static final String existingLastName = "Jackson";

    @Mock private FXAddEmployeeView mockView;
    @Mock private EmployeeDao mockDao;
    @Mock private PermissionManager mockPermissionManager;
    @Mock private UserDao mockUserDao;
    @Mock private BusinessDao mockBusinessDao;
    private AddEmployeeController testController;

    @Before
    public void beforeEachTest() {
        BusinessOwner businessOwner;
        MockitoAnnotations.initMocks(this);

        when(mockUserDao.getBusinessID(anyInt())).thenReturn(1);
        businessOwner = new BusinessOwner(mockUserDao, mockBusinessDao, 1);
        when(mockPermissionManager.getLoggedInUser()).thenReturn(businessOwner);

        testController = new AddEmployeeController(mockView, mockDao,
          mockPermissionManager);

        // The dao mock will allow John Thompson to be added
        when(mockDao.employeeExists(validFirstName, validLastName, 1))
            .thenReturn(false);
        when(mockDao.addEmployee(validFirstName, validLastName, 1))
            .thenReturn(true);
        when(mockDao.addEmployee(validFirstName, existingLastName, 1))
            .thenReturn(true);

        // The dao will not allow Jane Jackson to be added because she exists
        when(mockDao.employeeExists(existingFirstName, existingLastName, 1))
            .thenReturn(true);
    }

    @Test
    // Test 1 of addEmployee()
    public void addEmployeeValid() {
        assertTrue(testController.addEmployee(validFirstName, validLastName));
        verify(mockView, times(1))
            .setResultMessage("Successfully Added " + validFirstName + " " + validLastName + ".");
        verify(mockDao, times(1)).addEmployee(validFirstName, validLastName, 1);
    }

    @Test
    // Test 2 of addEmployee()
    public void addEmployeeExists() {
        assertFalse(testController.addEmployee(existingFirstName, existingLastName));
        verify(mockView, times(1))
            .setResultMessage(existingFirstName + " " + existingLastName + " already exists!");
        verify(mockDao, times(0)).addEmployee(anyString(), anyString(), anyInt());
    }

    @Test
    // Test 3 of addEmployee()
    public void addEmployeeMixedNames() {
        assertTrue(testController.addEmployee(validFirstName, existingLastName));
        verify(mockView, times(1))
            .setResultMessage("Successfully Added " + validFirstName + " " + existingLastName + ".");
        verify(mockDao, times(1)).addEmployee(validFirstName, existingLastName, 1);
    }

    @Test
    // Test 4 of addEmployee()
    public void addEmployeeFirstNameEmpty() {
        assertFalse(testController.addEmployee("", validLastName));
        verify(mockView, times(1)).setResultMessage("");
        verify(mockView, times(1)).setUsernameMessage("This field is required");
        verify(mockDao, times(0)).addEmployee(anyString(), anyString(), anyInt());
    }

    @Test
    // Test 5 of addEmployee()
    public void addEmployeeLastNameEmpty() {
        ArgumentCaptor<String> args = ArgumentCaptor.forClass(String.class);
        assertFalse(testController.addEmployee(validLastName, ""));

        verify(mockView, times(1)).setResultMessage("");
        verify(mockView, times(1)).setSurnameMessage("This field is required");
        verify(mockDao, times(0)).addEmployee(anyString(), anyString(), anyInt());
    }

    @Test
    // Test 6 of addEmployee()
    public void addEmployeeFirstNameHasNumbers() {
        assertFalse(testController.addEmployee("1", validLastName));
        verify(mockView, times(1)).setResultMessage("");
        verify(mockView, times(1)).setUsernameMessage("Cannot contain numbers!");
        verify(mockDao, times(0)).addEmployee(anyString(), anyString(), anyInt());
    }

    @Test
    // Test 7 of addEmployee()
    public void addEmployeeLastNameHasNumbers() {
        assertFalse(testController.addEmployee(validFirstName, "Thomp5son"));
        verify(mockView, times(1)).setResultMessage("");
        verify(mockView, times(1)).setSurnameMessage("Cannot contain numbers!");
        verify(mockDao, times(0)).addEmployee(anyString(), anyString(), anyInt());
    }

    @Test
    // Test 8 of addEmployee()
    public void addEmployeeFirstNameHasSpecial() {
        assertFalse(testController.addEmployee("Jo*n", validLastName));
        verify(mockView, times(1)).setResultMessage("");
        verify(mockView, times(1)).setUsernameMessage("Cannot contain special characters!");
        verify(mockDao, times(0)).addEmployee(anyString(), anyString(), anyInt());
    }

    @Test
    // Test 8 of addEmployee()
    public void addEmployeeLastNameHasSpecial() {
        assertFalse(testController.addEmployee(validFirstName, "Ja||kson"));
        verify(mockView, times(1)).setResultMessage("");
        verify(mockView, times(1)).setSurnameMessage("Cannot contain special characters!");
        verify(mockDao, times(0)).addEmployee(anyString(), anyString(), anyInt());
    }

}