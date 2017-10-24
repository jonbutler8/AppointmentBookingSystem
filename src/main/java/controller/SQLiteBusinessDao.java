package controller;

import controller.protocols.BusinessDao;
import controller.protocols.DatabaseController;
import model.NameIDTuple;
import model.TimeOfDay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Accesses and modifies business information from the SQLite database.
 */
public class SQLiteBusinessDao implements BusinessDao {

    private static final Logger logger =
      LogManager.getLogger(SQLiteBusinessDao.class);
    private DatabaseController databaseController;

    SQLiteBusinessDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public TimeOfDay getOpeningTime(int businessID, int day) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Getting opening time of business id: %d",
          businessID));

        query = "SELECT `start` FROM `businessHours` " +
          "WHERE `businessID` = ? " +
          "AND day = ? " +
          "ORDER BY `start` ASC LIMIT 1";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(businessID, day)));

        if(databaseController.resultSetHasNext(resultSet)) {
            return new TimeOfDay(databaseController.getInt(resultSet,
              "start", true));
        }
        else {
            return null;
        }
    }

    @Override
    public TimeOfDay getClosingTime(int businessID, int day) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Getting closing time of business id: %d",
          businessID));

        query = "SELECT `start` FROM `businessHours` " +
          "WHERE `businessID` = ? " +
          "AND day = ? " +
          "ORDER BY `start` DESC LIMIT 1";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(businessID, day)));

        if(databaseController.resultSetHasNext(resultSet)) {
            return new TimeOfDay(databaseController.getInt(resultSet,
              "start", true));
        }
        else {
            return null;
        }
    }

    /**
     * Gets the name of a specified business, by ID.
     * @param businessID The ID of the business to get name of.
     * @return Returns the name of the business in the form of a String.
     */
    @Override
    public String getBusinessName(int businessID) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Getting business name of business id: %d",
          businessID));

        query = "SELECT `name` FROM `business` WHERE `id` = ? ";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(businessID)));

        return databaseController.getString(resultSet, "name", true);
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query for the provided employee ID
     * to retrieve all business operating times for the specified day of week,
     * @param id The business ID of the business to retrieve
     * @param day The day of week to query, ranging 0-6 inclusive
     * @return Returns an ArrayList of start times for the employee's shifts,
     * or an empty ArrayList if there are no working times for the day
     */
    @Override
    public ArrayList<Integer> getBusinessOperatingTimes(int id, int day) {
        ResultSet resultSet;
        ArrayList<Integer> result = new ArrayList<Integer>();
        String query;

        logger.debug(String.format("Getting business hours of business id: %d",
          id));

        query = "SELECT `start` FROM `businessHours` " +
          "WHERE `businessID` = ? " +
          "AND `day` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(id, day)));

        while (databaseController.resultSetHasNext(resultSet)) {
            result.add(databaseController.getInt(resultSet, "start"));
        }
        databaseController.closeResultSet(resultSet);

        return result;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query for the provided business ID
     * to check if the passed business operating time exists
     * @param id The business ID of the employee whose times to retrieve
     * @param day The day of week to query, ranging 1-7 inclusive
     * @return Returns true of the working time exists
     */
    @Override
    public boolean businessOperatingTimeExists(int id, int day, int startTime) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format(
          "Checking business operating time on day %d at %d of business id: %d",
          day, startTime, id));

        query = "SELECT COUNT(*) FROM `businessHours` " +
          "WHERE `businessID` = ? " +
          "AND `day` = ? AND `start` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(id, day, startTime)));

       return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update for the provided business ID
     * to remove the operating time matching the provided start time,
     * inclusive
     * @param id The business ID of the business whose time to remove
     * @param startTime the start time of the operating time
     * @return Returns the boolean value of whether the operation was successful,
     */

    @Override
    public boolean removeOperatingTime(int id, int day, int startTime) {
        boolean result = false;
        String query;

        logger.debug(String.format(
          "Removing business operating time on day %d at %d of business id: %d",
          id, day, startTime));

        query = "DELETE FROM `businessHours` " +
          "WHERE `businessID` = ? " +
          "AND `start` = ? AND `day` = ?";

        if (databaseController.insert(query,
          new ArrayList<>(Arrays.asList(id, startTime, day))) == 1) {
            result = true;
        }

        return result;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update for the provided business ID
     * to add the specified operating time
     * @param id The business ID of the employee whose time to remove
     * @param day The day of the operating time
     * @return int startTime The time of a day of the operating time, in minutes
     * from midnight
     */
    @Override
    public boolean addOperatingTime(int id, int day, int startTime) {
        boolean result = false;
        String query;

        logger.debug(String.format(
          "Adding operating time on day %d at %d of business id: %d",
          id, day, startTime));

        query = "INSERT INTO `businessHours` (`businessID`, `day`, `start`) " +
          "VALUES (?, ?, ?)";

        if (databaseController.insert(query, new ArrayList<>(
                Arrays.asList(id, day, startTime))) > 0)
                  result = true;

        return result;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query to get name and id information on all businesses.
     * @return ArrayList<NameIDTuple> an array list of all busineses in the
     * system.
     */
    @Override
    public ArrayList<NameIDTuple> getAllBusinesses() {
        ResultSet resultSet;
        String query;
        ArrayList<NameIDTuple> result = new ArrayList<>();

        logger.debug("Querying for all businesses");

        query = "SELECT * FROM `business`";

        resultSet = databaseController.select(query,
          null);
        while (databaseController.resultSetHasNext(resultSet)) {
            result.add(extractBusiness(resultSet));
        }
        databaseController.closeResultSet(resultSet);
        return result;
    }

    /**
     * Used to test if a business exists with the provided business name.
     * @param businessName The name of the business to find.
     * @return Returns true if a business is found with matching business name or
     * false otherwise.
     */
    @Override
    public boolean businessExists(String businessName) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Checking if business exists: %n", businessName));

        query = "SELECT COUNT(*) FROM `business` WHERE lower(`name`) = lower" +
          "(?)";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(businessName)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /*
     * Helper method for getAllBusinesses to extract
     * an EmployeeRecord from a row in a ResultSet.
     */
    private NameIDTuple extractBusiness(ResultSet resultSet) {
        int id = databaseController.getInt(resultSet, "id");
        String name =databaseController.getString(resultSet, "name");
        return new NameIDTuple(id, name);
    }
}
