package controller;

import controller.protocols.DatabaseController;
import controller.protocols.EmployeeDao;
import model.EmployeeRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Accesses employee information from the SQLite employee database.
 */
public class SQLiteEmployeeDao implements EmployeeDao {

    private static final Logger logger =
      LogManager.getLogger(SQLiteEmployeeDao.class);
    private DatabaseController databaseController;

    SQLiteEmployeeDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query to insert the provided credentials of the
     * employee to the database.
     * @param firstName The firstName of the employee to find.
     * @param lastName The lastName of the employee to find.
     * @return Returns true if a employee is inserted or
     * false otherwise.
     */
    @Override
    public boolean addEmployee(String firstName, String lastName, int
      businessID) {
        boolean result = false;
        String query;

        logger.debug(String.format("Adding Employee: %s %s",
          firstName, lastName));

        query = "INSERT INTO `employee` (`firstName`, `lastName`, " +
          "`businessID`) VALUES (?, ?, ?)";
        if (databaseController.insert(query,
          new ArrayList<>(Arrays.asList(firstName, lastName, businessID))) > 0)
            result = true;

        return result;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query for the provided credentials
     * to determine if an employee exists credentials.
     * @param firstName The firstName of the employee to find.
     * @param lastName The lastName of the employee to find.
     * @return Returns true if an employee's credentials match or
     * false otherwise.
     */
    @Override
    public boolean employeeExists(String firstName, String lastName, int
      businessID) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Checking for employee: %s %s",
          firstName, lastName));

        query = "SELECT COUNT(*) FROM `employee` " +
          "WHERE lower(`firstName`) = lower(?) AND lower(`lastName`) = lower" +
          "(?) AND businessID = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(firstName, lastName, businessID)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query to get information on all employees.
     * @return ArrayList<EmployeeRecord> an array list of all employees in the
     * system.
     */
    @Override
    public ArrayList<EmployeeRecord> getAllEmployees(int businessID) {
        ResultSet resultSet;
        String query;
        ArrayList<EmployeeRecord> result = new ArrayList<>();

        logger.debug("Querying for all employees");

        query = "SELECT * FROM `employee` WHERE `businessID` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(businessID)));
        while (databaseController.resultSetHasNext(resultSet)) {
            result.add(extractEmployee(resultSet));
        }
        databaseController.closeResultSet(resultSet);
        return result;
    }

    /**
     * Retrieves all employees for a business that have at least one shift
     * throughout the week.
     * @return A list off all employees in the form of an EmployeeRecord.
     */
    @Override
    public List<EmployeeRecord> getAllWorkingEmployees(int businessID) {
        ResultSet resultSet;
        String query;
        ArrayList<EmployeeRecord> result = new ArrayList<>();

        query = "SELECT DISTINCT employee.id, employee.firstName, employee" +
          ".lastName, employee.businessID FROM `employee`, `workingTimes` " +
          "WHERE workingTimes.employeeID = employee.id " +
          "AND employee.businessID = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(businessID)));
        while (databaseController.resultSetHasNext(resultSet)) {
            result.add(extractEmployee(resultSet));
        }
        databaseController.closeResultSet(resultSet);
        return result;
    }

    /*
     * Helper method for getAllEmployees & getAllWorkingEmployees to extract
     * an EmployeeRecord from a row in a ResultSet.
     */
    private EmployeeRecord extractEmployee(ResultSet resultSet) {
        int id = databaseController.getInt(resultSet, "id");
        String firstName =databaseController.getString(resultSet, "firstName");
        String lastName = databaseController.getString(resultSet, "lastName");
        int businessID = databaseController.getInt(resultSet, "businessID");
        return new EmployeeRecord(id, firstName, lastName, businessID);
    }
}
