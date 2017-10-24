package controller;

import controller.protocols.EmployeeDao;
import model.EmployeeRecord;

import java.util.ArrayList;
import java.util.List;

public class DummyEmployeeDao implements EmployeeDao {

    public DummyEmployeeDao() { }

    public boolean addEmployee(String firstName, String lastName, int
      businessID) {
        return !employeeExists(firstName, lastName, 1);
    }

    public boolean employeeExists(String firstName, String lastName, int
      businessID) {
        if(firstName.equals("testemployeefirstname") &&
          lastName.equals("testemployeelastname"))
            return true;
        else
            return false;
    }

    @Override
    public ArrayList<EmployeeRecord> getAllEmployees(int businessID) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<EmployeeRecord> getAllWorkingEmployees(int businessID) {
        return null;
    }
}
