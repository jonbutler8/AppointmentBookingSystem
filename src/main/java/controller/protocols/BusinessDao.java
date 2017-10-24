package controller.protocols;

import java.util.ArrayList;

import model.NameIDTuple;
import model.TimeOfDay;

public interface BusinessDao {

    TimeOfDay getOpeningTime(int businessID, int day);

    TimeOfDay getClosingTime(int businessID, int day);

    String getBusinessName(int businessID);

    public boolean removeOperatingTime(int id, int day, int startTime);

    public boolean businessOperatingTimeExists(int id, int day, int startTime);

    public ArrayList<Integer> getBusinessOperatingTimes(int id, int day);

    public boolean addOperatingTime(int id, int day, int startTime);

    public ArrayList<NameIDTuple> getAllBusinesses();

    /**
     * Used to test if a business exists with the provided business name.
     * @param username The name of the business to find.
     * @return Returns true if a business is found with matching business name or
     * false otherwise.
     */
    boolean businessExists(String businessName);

}
