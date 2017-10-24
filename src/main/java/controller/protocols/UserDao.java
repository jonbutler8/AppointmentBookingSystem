package controller.protocols;

import model.protocols.User;

/**
 * Used to access information about users.
 */
public interface UserDao {
    /**
     * Used to test if a user exists with provided credentials.
     * @param username The username of the user to find.
     * @param password The password of the user to find.
     * @return Returns true if a user is found with matching credentials or
     \ false otherwise.
     */
    boolean isValidLogin(String username, String password);

    /**
     * Used to test if a user exists with the provided username.
     * @param username The username of the user to find.
     * @return Returns true if a user is found with matching username or
     \ false otherwise.
     */
    boolean userExists(String username);

    /**
     * Used to insert a new customer with the given username and password into
     * the system
     * @param username The username of the user to create
     * @param password The password of the user to create
     * @param businessID
     * @return Returns true if a user was created successfully, false otherwise
     */
    boolean createCustomer(String username, String password,
      String firstName, String lastName, String phoneNumber, String address, int businessID);

    /**
     * Used to get a users type from the database - Needed for permissions
     * @param id The id of the user to get userType of
     * @return Returns the userType as a string
     */
    String getUserType(int id);

    /**
     * Returns a user object based on the username provided. The user object
     * stores the user ID and uses a reference to the UserDao to retrieve
     * other information about the user when called on.
     * @param username The username of the user to create.
     * @return Returns a User object based on the username provided.
     */
    User getUser(String username);

    /**
     * Gets the username of a user providing an id.
     * @param id The id of the user to get the username of.
     * @return Returns the username in the form of a string.
     */
    String getUsername(int id);

    /**
     * Gets the business ID associated with a user.
     * @param id The id of the user to get the business ID of.
     * @return Returns the business id in the form of an integer.
     */
    int getBusinessID(int id);

    /**
     * Used to insert a new business account and associated business
     * with the given parameters into the system
     * @return Returns true if a user was created successfully, false otherwise
     */
    boolean createBusinessUser(String username, String password, String businessName, String phoneNumber,
            String address);
}
