package controller.protocols;

import java.util.ArrayList;

/**
 * Accesses and modifies employee working time information
 */
public interface WorkingTimesDao {
    
    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query for the provided employee ID
     * to retrieve all working times for the specified day of week,
     * @param  int id The employee ID of the employee whose times to retrieve
     * @param  int day The day of week to query, ranging 0-6 inclusive
     * @return Returns an ArrayList of start times for the employee's shifts
     * in minutes, or an empty ArrayList if there are no working times for the day
     */
    public ArrayList<Integer> getEmployeeWorkTimes(int id, int day);

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update for the provided employee ID
     * to remove the working time matching the provided day and start time
     * @param  int id The employee ID of the employee whose time to remove
     * @param  int startTime the start time of the working time (in minutes from midnight)
     * @param int The day of the working time
     * @return Returns the boolean value of whether the operation was successful
     */
    boolean removeWorkingTime(int id, int day, int startTime);

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update for the provided employee ID
     * to add the specified working time
     * @param  int id The employee ID of the employee whose time to remove
     * @param  int day The day of the working time
     * @return int startTime The time of a day of the working time, in minutes
     * from midnight
     */
    boolean addWorkingTime(int id, int day, int startTime);
    
    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query for the provided employee ID
     * to check if the passed working time exists
     * @param  int id The employee ID of the employee whose times to retrieve
     * @param  int day The day of week to query, ranging 1-7 inclusive
     * @return Returns true of the working time exists
     */
    public boolean workingTimeExists(int id, int day, int startTime);

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs an update to remove all working times matching 
     * the provided start and end times inclusive, for all employees
     * @param startTime the start time of the working time
     * @return Returns the boolean value of whether the operation was successful,
     */
    boolean removeWorkingTimeAllEmployees(int day, int startTime);
}
