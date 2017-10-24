package controller;

import controller.protocols.CustomerDao;
import controller.protocols.DatabaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLiteCustomerDao implements CustomerDao {

    private static final Logger logger =
      LogManager.getLogger(SQLiteCustomerDao.class);
    private DatabaseController databaseController;

    SQLiteCustomerDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public boolean customerExists(String firstName, String phoneNumber, int businessID) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Checking if customer exists: %s - %s",
          firstName, phoneNumber));

        query = "SELECT COUNT(*) FROM `customer` " +
          "WHERE lower(`firstName`) = lower(?) AND phoneNumber = ? AND businessID = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(firstName, phoneNumber, businessID)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    @Override
    public boolean createCustomer(String firstName, String phoneNumber, int businessID) {
        String query;

        query = "INSERT INTO `customer` (`firstName`, `phoneNumber`, `businessID`) VALUES " +
          "(?, ?, ?)";

        return databaseController.insert(query, new ArrayList<>(
          Arrays.asList(firstName, phoneNumber))) > 0;
    }

    @Override
    public boolean customerHasAccount(String firstName, String phoneNumber, int businessID) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Checking if customer exists: %s - %s",
          firstName, phoneNumber));

        query = "SELECT COUNT(*) FROM `customer` " +
          "WHERE lower(`firstName`) = lower(?) AND phoneNumber = ? AND " +
          "`userID` IS NOT NULL";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(firstName, phoneNumber)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    @Override
    public int getCustomerID(int userID) {
        ResultSet resultSet;
        String query;

        logger.debug("Getting customer ID");

        query = "SELECT `id` FROM `customer` " +
          "WHERE `userID` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(userID)));

        return databaseController.getInt(resultSet, "id", true);
    }

    @Override
    public int getUserID(int customerID) {
        ResultSet resultSet;
        String query;

        logger.debug("Getting user ID");

        query = "SELECT `userID` FROM `customer` " +
          "WHERE `id` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(customerID)));

        return databaseController.getInt(resultSet, "userID", true);
    }

    @Override
    public int getCustomerID(String firstName, String phoneNumber) {
        ResultSet resultSet;
        String query;

        logger.debug("Getting customer ID");

        query = "SELECT `id` FROM `customer` " +
          "WHERE `firstName` = ? AND `phoneNumber` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(firstName, phoneNumber)));

        return databaseController.getInt(resultSet, "id", true);
    }
}
