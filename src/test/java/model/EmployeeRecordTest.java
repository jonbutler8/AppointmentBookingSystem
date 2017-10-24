package model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeRecordTest {
    private EmployeeRecord employeeOne;
    private EmployeeRecord employeeTwo;
    private EmployeeRecord idMismatch;
    private EmployeeRecord fnameMismatch;
    private EmployeeRecord lnameMismatch;


    @Before
    public void beforeEachTest() {
        employeeOne = new EmployeeRecord(5, "Name", "LName", 1);
        employeeTwo = new EmployeeRecord(5, "Name", "LName", 1);
        idMismatch = new EmployeeRecord(6, "Name", "LName", 1);
        fnameMismatch = new EmployeeRecord(5, "OtherName", "LName", 1);
        lnameMismatch = new EmployeeRecord(6, "Name", "OtherLName", 1);
    }

    @Test
    // testEquals test 1
    public void testEqualsTrue() {
        assertTrue(employeeOne.equals(employeeTwo));
    }

    @Test
    // testEquals test 2
    public void testEqualsNull() {
        assertFalse(employeeOne.equals(null));
    }

    @Test
    // testEquals test 3
    public void testEqualsIdMismatch() {
        assertFalse(employeeOne.equals(idMismatch));
    }

    @Test
    // testEquals test 4
    public void testEqualsFNameMismatch() {
        assertFalse(employeeOne.equals(fnameMismatch));
    }

    @Test
    // testEquals test 5
    public void testEqualsLNameMismatch() {
        assertFalse(employeeOne.equals(lnameMismatch));
    }


}
