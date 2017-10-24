package controller.protocols;

public interface CustomerDao {
    int getCustomerID(int userID);
    int getCustomerID(String firstName, String phoneNumber);
    int getUserID(int customerID);
    boolean customerExists(String firstName, String phoneNumber, int businessID);
    boolean customerHasAccount(String firstName, String phoneNumber, int businessID);
    boolean createCustomer(String firstName, String phoneNumber, int businessID);
}
