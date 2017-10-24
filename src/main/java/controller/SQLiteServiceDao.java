package controller;

import controller.protocols.DatabaseController;
import controller.protocols.ServiceDao;
import model.EmployeeRecord;
import model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SQLiteServiceDao implements ServiceDao {

    private static final Logger logger =
            LogManager.getLogger(SQLiteServiceDao.class);

    private DatabaseController databaseController;

    SQLiteServiceDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public HashMap<Service, ArrayList<Integer>> getAllServices(int businessID) {
        HashMap<Service, ArrayList<Integer>> services = new HashMap<>();
        ResultSet resultSet;
        String query;

        logger.debug("Getting all services");

        query = "SELECT service.id, service.name, serviceDuration.duration " +
                "FROM service, serviceDuration " +
                "WHERE service.id = serviceDuration.serviceID " +
                "AND service.businessID = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(businessID)));

        Service service = null;
        ArrayList<Integer> durations = new ArrayList<>();
        while (databaseController.resultSetHasNext(resultSet)) {
            int id = databaseController.getInt(resultSet, "id");
            String name = databaseController.getString(resultSet, "name");
            int duration = databaseController.getInt(resultSet, "duration");

            logger.debug(String.format("Service: [%d] %-20s - %d", id, name,
                    duration));

            if(service == null) {
                service = new Service(id, name);
            }
            else if(service.id() != id) {
                services.put(service, durations);
                service = new Service(id, name);
                durations = new ArrayList<>();
            }
            durations.add(duration);
        }
        services.put(service, durations);

        databaseController.closeResultSet(resultSet);
        return services;
    }

    @Override
    public Service getService(int id) {
        ResultSet resultSet;
        String query;

        query = "SELECT service.id, service.name " +
                "FROM service " +
                "WHERE service.id = ?";

        resultSet = databaseController.select(query,
                new ArrayList<>(Arrays.asList(id)));

        return new Service(databaseController.getInt(resultSet,"id"),
          databaseController.getString(resultSet, "name", true));
    }
    
    @Override
    public ArrayList<Service> getAllExistingServices(int businessID) {
        ResultSet resultSet;
        ArrayList<Service> serviceList = new ArrayList<Service>();
        String query;

        query = "SELECT service.id, service.name " +
                "FROM service " +
                "WHERE service.businessID = ?";

        resultSet = databaseController.select(query,
                new ArrayList<>(Arrays.asList(businessID)));
        
        while (databaseController.resultSetHasNext(resultSet)) {
            int id = databaseController.getInt(resultSet, "id");
            String name = databaseController.getString(resultSet, "name");
            Service service = new Service(id, name);
            serviceList.add(service);
        }
        databaseController.closeResultSet(resultSet);

        return serviceList;
    }

    @Override
    public List<Service> getDisallowedServices(EmployeeRecord employee) {
        ResultSet resultSet;
        String query;

        logger.debug("Querying employees disallowed services");

        query = "SELECT service.id, service.name " +
          "FROM service, employeeDisallowedServices" +
          "WHERE service.id = employeeDisallowedServices.serviceID" +
          "AND `employeeID` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>());

        ArrayList<Service> result = new ArrayList<>();
        while (databaseController.resultSetHasNext(resultSet)) {
            result.add(new Service(databaseController.getInt(resultSet,"id"),
              databaseController.getString(resultSet, "name")));
        }
        databaseController.closeResultSet(resultSet);
        return result;
    }

    @Override
    public boolean canDoService(EmployeeRecord employee, Service service) {
        return canDoService(employee, service.id());
    }

    @Override
    public boolean canDoService(EmployeeRecord employee, int serviceID) {
        ResultSet resultSet;
        String query;

        query = "SELECT count(*) " +
          "FROM employeeDisallowedServices " +
          "WHERE serviceID = ?" +
          "AND employeeID = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(serviceID, employee.id)));

        logger.debug("EmployeeID: " + employee.id);
        logger.debug("ServiceID: " + serviceID);

        int results = databaseController.getInt(resultSet, "count(*)",
          true);

        logger.debug("Results: "+results);
        return results == 0;
    }

    @Override
    public boolean toggleService(EmployeeRecord employee, int serviceID) {
        String query;

       if(canDoService(employee, serviceID)) {
           query = "INSERT INTO `employeeDisallowedServices`" +
             "(`employeeID`, `serviceID`) VALUES (?, ?)";
       }
       else {
           query = "DELETE FROM `employeeDisallowedServices`" +
             "WHERE `employeeID` = ?" +
             "AND `serviceID` = ?";
       }

        return databaseController.insert(query,
          new ArrayList<>(Arrays.asList(employee.id, serviceID))) > 0;
    }
    
    @Override
    public boolean toggleServiceTime(int serviceID, int minuteDuration) {
        String query;

       if(!serviceTimeExists(serviceID, minuteDuration)) {
           query = "INSERT INTO `serviceDuration`" +
             "(`serviceID`, `duration`) VALUES (?, ?)";
       }
       else {
           query = "DELETE FROM `serviceDuration`" +
             "WHERE `serviceID` = ?" +
             "AND `duration` = ?";
       }

        return databaseController.insert(query,
          new ArrayList<>(Arrays.asList(serviceID, minuteDuration))) > 0;
    }
    
    /**
     * Used to test if a service duration exists with the provided service id and time.
     * @param serviceName The name of the service to find.
     * @param businessID The business ID of the business to search in
     * @return Returns true if a service is found with service name or
     * false otherwise.
     */
    @Override
    public boolean serviceTimeExists(int serviceID, int minuteDuration) {

        ResultSet resultSet;
        String query;

        logger.debug(String.format("Checking if service time: %d for id %d exists", 
                minuteDuration, serviceID));

        query = "SELECT COUNT(*) FROM `serviceDuration` WHERE `serviceID` = ?" +
          "AND `duration` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(serviceID, minuteDuration)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /**
     * Used to test if a service exists with the provided service name.
     * @param serviceName The name of the service to find.
     * @param businessID The business ID of the business to search in
     * @return Returns true if a service is found with service name or
     * false otherwise.
     */
    @Override
    public boolean serviceExists(String serviceName, int businessID) {

        ResultSet resultSet;
        String query;

        logger.debug(String.format("Checking if service exists: %n", serviceName));

        query = "SELECT COUNT(*) FROM `service` WHERE lower(`name`) = lower" +
          "(?) AND `businessID` = ?";

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(serviceName, businessID)));

        return databaseController.getInt(resultSet, "COUNT(*)", true) > 0;
    }

    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query to insert the provided service into the database
     * @param serviceName The name of the service to create
     * @param businessID the ID of the business to create the service for
     * @return Returns true if a service is inserted or
     * false otherwise.
     */
    @Override
    public boolean createService(String serviceName, int businessID) {
        boolean result = false;
        String query;

        logger.debug(String.format("Adding service: %s to business %d",
          serviceName, businessID));

        query = "INSERT INTO `service` (`name`, `businessID`) VALUES (?, ?)";
        if (databaseController.insert(query,
          new ArrayList<>(Arrays.asList(serviceName, businessID))) > 0) {
            result = true;
        }
        return result;
    }
    
    /**
     * Uses the DatabaseController (SQLiteDatabaseController) to connect to a
     * database, then runs a query to remove the specified service from the database
     * @param serviceID The id of the service to delete
     * @return Returns true if a service is inserted or
     * false otherwise.
     */
    @Override
    public boolean deleteService(int serviceID) {
        boolean result = false;

        logger.debug(String.format("Deleting service: %d",
          serviceID));

        List<String> queries = new ArrayList<>();
        List<List<?>> args = new ArrayList<>();

        queries.add("DELETE FROM `serviceDuration` WHERE `serviceID` = ?");
        args.add(new ArrayList<>(Arrays.asList(serviceID)));
        queries.add("DELETE FROM `service` WHERE `id` = ?");
        args.add(new ArrayList<>(Arrays.asList(serviceID)));
        
        return databaseController.insertMultiple(queries, args) > 0;
    }
}

