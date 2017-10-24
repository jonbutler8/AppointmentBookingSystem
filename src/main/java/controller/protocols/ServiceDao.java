package controller.protocols;

import model.EmployeeRecord;
import model.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ServiceDao {
    Service getService(int id);
    HashMap<Service, ArrayList<Integer>> getAllServices(int businessID);
    List<Service> getDisallowedServices(EmployeeRecord employee);
    boolean canDoService(EmployeeRecord employee, Service service);
    boolean canDoService(EmployeeRecord employee, int serviceID);
    boolean toggleService(EmployeeRecord employee, int serviceID);
    
    /**
     * Used to test if a service exists with the provided service name.
     * @param serviceName The name of the service to find.
     * @param businessID The business ID of the business to search in
     * @return Returns true if a service is found with service name or
     * false otherwise.
     */
    boolean serviceExists(String serviceName, int businessID);
    boolean createService(String serviceName, int businessID);
    boolean toggleServiceTime(int serviceID, int minuteDuration);
    boolean serviceTimeExists(int serviceID, int minuteDuration);
    ArrayList<Service> getAllExistingServices(int businessID);
    boolean deleteService(int serviceID);
}
