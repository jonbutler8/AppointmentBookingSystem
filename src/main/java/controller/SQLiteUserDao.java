package controller;

import controller.protocols.BusinessDao;
import controller.protocols.CustomerDao;
import controller.protocols.DatabaseController;
import controller.protocols.UserDao;
import model.UserFactory;
import model.protocols.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Accesses user information from the SQLite user database.
 */
public class SQLiteUserDao implements UserDao {

    private static final Logger logger =
      LogManager.getLogger(SQLiteUserDao.class);
    private DatabaseController databaseController;
    private CustomerDao customerDao;
    private BusinessDao businessDao;

    SQLiteUserDao(DatabaseController databaseController,
      CustomerDao customerDao, BusinessDao businessDao) {
        this.databaseController = databaseController;
        this.customerDao = customerDao;
        this.businessDao = businessDao;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query to determine if the provided credentials
     * match a user.
     * @param username The username of the user to find.
     * @param password The password of the user to find.
     * @return Returns true if a user is found with matching credentials or
     \ false otherwise.
     */
    @Override
    public boolean isValidLogin(String username, String password) {
        ResultSet resultSet;
        String query;

        logger.debug("Checking credentials for user: " + username);

        query = "SELECT COUNT(*) FROM `user` " +
          "WHERE `username` = ? AND `password` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(username, password)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /**
     * Used to insert a new customer with the given username and password into
     * the system
     * @param username The username of the user to create
     * @param password The password of the user to create
     * @return Returns true if a user was created successfully, false otherwise
     */
    @Override
    public boolean createCustomer(String username, String password,
      String firstName, String lastName, String phoneNumber, String address, int businessID) {

        if(firstName == null || phoneNumber == null)
            return false;

        logger.debug(String.format("Adding customer: %s", username));

        List<String> queries = new ArrayList<>();
        List<List<?>> args = new ArrayList<>();

        queries.add("INSERT INTO `user` (`username`, `password`, `userTypeID`, `businessID`)" +
          " VALUES (?, ?, (SELECT `id` FROM `userType` WHERE `name` = " +
          "'customer'), ?)");

        args.add(new ArrayList<>(Arrays.asList(username, password, businessID)));

        if(!customerDao.customerExists(firstName, phoneNumber, businessID)) {

            queries.add("INSERT INTO `customer` (`userID`, `firstName`, " +
              "`lastName`, `phoneNumber`, `address`, `businessID`) VALUES ((SELECT `id` FROM"+
              " `user` WHERE `username` = ?), ?, ?, ?, ?, ?)");

            args.add(new ArrayList<>(Arrays.asList(username, firstName, lastName,
              phoneNumber, address, businessID)));
        }
        else if(!customerDao.customerHasAccount(firstName, phoneNumber, businessID)){
            queries.add("UPDATE `customer` SET `userID` = (SELECT `id` FROM " +
              "`user` WHERE `username` = ?), `lastName` = ?, `address` = ? " +
              "WHERE `firstName` = ? and `phoneNumber` = ?");

            args.add(new ArrayList<>(Arrays.asList(username, lastName, address,
              firstName, phoneNumber)));
        }
        else {
            return false;
        }
        return databaseController.insertMultiple(queries, args) == 2;
    }

    /**
     * Used to insert a new business with the given parameters into the system
     * @return Returns true if a user was created successfully, false otherwise
     */
    @Override
    public boolean createBusinessUser(String username, String password,
      String businessName, String phoneNumber, String address) {

        logger.debug(String.format("Adding business: %s", businessName));

        List<String> queries = new ArrayList<>();
        List<List<?>> args = new ArrayList<>();

        queries.add("INSERT INTO `business` (`name`, `phoneNumber`, " +
                "`address`) VALUES (?, ?, ?)");
        args.add(new ArrayList<>(Arrays.asList(businessName, phoneNumber, address)));

        queries.add("INSERT INTO `user` (`username`, `password`, `userTypeID`, `businessID`)" +
          " VALUES (?, ?, (SELECT `id` FROM `userType` WHERE `name` = " +
          "'businessOwner'), (SELECT `id` FROM `business` WHERE `name` = " +
          "?))");

        args.add(new ArrayList<>(Arrays.asList(username, password, businessName)));

        return databaseController.insertMultiple(queries, args) == 2;
    }

    /**
     * Used to test if a user exists with the provided username.
     * @param username The username of the user to find.
     * @return Returns true if a user is found with matching username or
     * false otherwise.
     */
    @Override
    public boolean userExists(String username) {
        ResultSet resultSet;
        String query;

        logger.debug(String.format("Checking if user exists: %n", username));

        query = "SELECT COUNT(*) FROM `user` WHERE lower(`username`) = lower" +
          "(?)";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(username)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /**
     * Used to get a users type from the database - Needed for permissions
     * @param id The id of the user to get userType of
     * @return Returns the userType as a string
     */
    @Override
    public String getUserType(int id) {
        ResultSet resultSet;
        String query;

        logger.debug("Getting user type of: " + id);

        query = "SELECT `name` FROM `userType` " +
          "WHERE `id` = (SELECT `userTypeID` FROM `user` WHERE `id` = ?)";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(id)));

        return databaseController.getString(resultSet, "name", true);
    }

    /**
     * Returns a user object based on the username provided. The user object
     * stores the user ID and uses a reference to the UserDao to retrieve
     * other information about the user when called on.
     * @param username The username of the user to create.
     * @return Returns a User object based on the username provided.
     */
    @Override
    public User getUser(String username) {
        assert username != null : "Username can not be null";

        ResultSet resultSet;
        String query;

        logger.info("Getting user of: " + username);

        query = "SELECT `id` FROM `user` WHERE `username` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(username)));

        int userID = databaseController.getInt(resultSet, "id", true);

        return new UserFactory(this, customerDao, businessDao)
          .createUser(userID);
    }

    /**
     * Gets the username of a user providing an id.
     * @param id The id of the user to get the username of.
     * @return Returns the username in the form of a string.
     */
    @Override
    public String getUsername(int id) {
        ResultSet resultSet;
        String query;

        logger.debug("Getting username of: " + id);

        query = "SELECT `username` FROM `user` WHERE `id` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(id)));

        return databaseController.getString(resultSet, "username", true);
    }

    /**
     * Gets the business ID associated with a user.
     * @param id The id of the user to get the business ID of.
     * @return Returns the business id in the form of an integer.
     */
    @Override
    public int getBusinessID(int id) {
        ResultSet resultSet;
        String query;

        logger.debug("Getting businessID of: " + id);

        query = "SELECT `businessID` FROM `user` WHERE `id` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(id)));

        return databaseController.getInt(resultSet, "businessID", true);
    }
}
