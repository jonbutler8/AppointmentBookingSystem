package controller;

import controller.protocols.UserDao;
import controller.protocols.ViewController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.protocols.LoginView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class LoginControllerTest {

	private static final String validCustomerName = "testcustomer";
	private static final String validBusinessName = "businessowner";
	private static final String validPassword = "password";

	@Mock
	private PermissionManager permissionManager;

	@Mock
	private UserDao userDao;

	@Mock
	private LoginView view;

	@Mock
	private ViewController viewController;
	@InjectMocks
	private LoginController loginController;

	@Before
	public void beforeEachTest() {
		MockitoAnnotations.initMocks(this);
		loginController =
				new LoginController(viewController, view, permissionManager, userDao);

		when(userDao.isValidLogin(validCustomerName, "")).thenReturn(false);
		when(userDao.isValidLogin(validCustomerName, validPassword)).thenReturn(true);
		when(userDao.isValidLogin(validBusinessName, validPassword)).thenReturn(true);
	}

	@Test
	public void loginCustomerTest(){
		assertTrue(loginController.attemptLogin(validCustomerName, validPassword));
		verify(userDao, times(1)).isValidLogin(validCustomerName, validPassword);
		verify(viewController, times(1)).gotoHome();
		verify(userDao, times(1)).isValidLogin(anyString(), anyString());
	}

	@Test
	public void loginBusinessTest(){
		assertTrue(loginController.attemptLogin(validBusinessName, validPassword));
		verify(userDao, times(1)).isValidLogin(validBusinessName, validPassword);
		verify(viewController, times(1)).gotoHome();
		verify(userDao, times(1)).isValidLogin(anyString(), anyString());
	}

	@Test
	public void invalidLoginTest(){
		assertFalse(loginController.attemptLogin(validBusinessName, ""));
		verify(userDao, times(0)).isValidLogin(validBusinessName, "");
		verify(view, times(1)).setLoginMessage("Incorrect Username or Password.");
		verify(viewController, never()).gotoHome();
	}
}
