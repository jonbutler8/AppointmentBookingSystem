package controller.protocols;

import model.EmployeeRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to access information about employees.
 */
public interface EmployeeDao {

    /**
     * addEmployee is used to insert created employees with parameters
     * firstName and lastName into the database.
     * @param firstName The firstName of the employee to create
     * @param lastName The lastName of the employee to create
     * @return Returns true if a employee was created successfully, false otherwise'
     */

    boolean addEmployee(String firstName, String lastName, int businessID);

    /**
     * Checks if an employee already exists with parameters
     * firstName and lastName.
     * @param firstName The lastName of the employee to find.
     * @param lastName The lastName of the employee to find.
     * @return Returns true if a employee is found with matching firstname
     * and last name, otherwise return false.
     */
    boolean employeeExists(String firstName, String lastName, int businessID);

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query to get information on all employees
     * @return ArrayList<EmployeeRecord> an array list of all employees in the system
     */
    ArrayList<EmployeeRecord> getAllEmployees(int businessID);


    /**
     * Retrieves all employees for a business that have at least one shift
     * throughout the week.
     * @return A list off all employees in the form of an EmployeeRecord.
     */
    List<EmployeeRecord> getAllWorkingEmployees(int businessID);
}
