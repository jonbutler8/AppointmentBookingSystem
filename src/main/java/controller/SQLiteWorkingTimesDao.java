package controller;

import controller.protocols.DatabaseController;
import controller.protocols.WorkingTimesDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Accesses and modifies employee working time information from the SQLite database.
 */
public class SQLiteWorkingTimesDao implements WorkingTimesDao {

    private static final Logger logger =
      LogManager.getLogger(SQLiteWorkingTimesDao.class);
    private DatabaseController databaseController;

    SQLiteWorkingTimesDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query for the provided employee ID
     * to retrieve all working times for the specified day of week,
     * @param id The employee ID of the employee whose times to retrieve
     * @param day The day of week to query, ranging 0-6 inclusive
     * @return Returns an ArrayList of start times for the employee's shifts,
     * or an empty ArrayList if there are no working times for the day
     */
    public ArrayList<Integer> getEmployeeWorkTimes(int id, int day) {
        ResultSet resultSet;
        ArrayList<Integer> result = new ArrayList<Integer>();
        String query;

        logger.debug(String.format("Getting working times of employee id: %d",
          id));

        if(id == 0) {
            query = "SELECT DISTINCT `start` FROM `workingTimes`" +
              "WHERE `day` = ?";
            resultSet = databaseController.select(query,
              new ArrayList<>(Arrays.asList(day)));
        }
        else {
            query = "SELECT `start` FROM `workingTimes` " +
              "WHERE `employeeID` = ? " +
              "AND `day` = ?";
            resultSet = databaseController.select(query,
              new ArrayList<>(Arrays.asList(id, day)));
        }

        while (databaseController.resultSetHasNext(resultSet)) {
            result.add(
                databaseController.getInt(resultSet, "start"));
        }

        databaseController.closeResultSet(resultSet);
        return result;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query for the provided employee ID
     * to check if the passed working time exists
     * @param id The employee ID of the employee whose times to retrieve
     * @param day The day of week to query, ranging 1-7 inclusive
     * @return Returns true of the working time exists
     */
    public boolean workingTimeExists(int id, int day, int startTime) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format(
          "Checking working time on day %d at %d of employee id: %d",
          day, startTime, id));

        query = "SELECT COUNT(*) FROM `workingTimes` " +
          "WHERE `employeeID` = ? " +
          "AND `day` = ? AND `start` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(id, day, startTime)));

       return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update for the provided employee ID
     * to remove the working time matching the provided start and end times,
     * inclusive
     * @param id The employee ID of the employee whose time to remove
     * @param startTime the start time of the working time
     * @return Returns the boolean value of whether the operation was successful,
     */

    @Override
    public boolean removeWorkingTime(int id, int day, int startTime) {
        boolean result = false;
        String query;

        logger.debug(String.format(
          "Removing working time on day %d at %d of employee id: %d",
          day, startTime, id));

        query = "DELETE FROM `workingTimes` " +
          "WHERE `employeeID` = ? " +
          "AND `start` = ? AND `day` = ?";

        if (databaseController.insert(query,
          new ArrayList<>(Arrays.asList(id, startTime, day))) == 1) {
            result = true;
        }

        return result;
    }
    
    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update to remove all working times matching 
     * the provided start and end times inclusive, for all employees
     * @param startTime the start time of the working time
     * @return Returns the boolean value of whether the operation was successful,
     */

    @Override
    public boolean removeWorkingTimeAllEmployees(int day, int startTime) {
        boolean result = false;
        String query;

        logger.debug(String.format(
          "Removing working time on day %d at %d of all employees",
          day, startTime));

        query = "DELETE FROM `workingTimes` " +
          "WHERE `start` = ? AND `day` = ?";

        if (databaseController.insert(query,
          new ArrayList<>(Arrays.asList(startTime, day))) == 1) {
            result = true;
        }

        return result;
    }
    
    

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update for the provided employee ID
     * to add the specified working time
     * @param id The employee ID of the employee whose time to remove
     * @param day The day of the working time
     * @return int startTime The time of a day of the working time, in minutes
     * from midnight
     */
    @Override
    public boolean addWorkingTime(int id, int day, int startTime) {
        boolean result = false;
        String query;

        logger.debug(String.format(
          "Adding working time on day %d at %d of employee id: %d",
          id, day, startTime));

        query = "INSERT INTO `workingTimes` (`employeeID`, `day`, `start`) " +
          "VALUES (?, ?, ?)";

        if (databaseController.insert(query, new ArrayList<>(
                Arrays.asList(id, day, startTime))) > 0)
                  result = true;

        return result;
    }
}
