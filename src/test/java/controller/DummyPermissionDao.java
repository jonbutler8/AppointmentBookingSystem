package controller;

import controller.protocols.PermissionDao;

public class DummyPermissionDao implements PermissionDao {

    DummyPermissionDao() { }

    @Override
    public boolean checkKeyByUsername(String key, String username) {
        switch(key) {
            case "canGoToLogin":
            case "canGoToRegister":
                return username == null;
            case "canGoToAddEmployee":
            case "canGoToBOHome":
            case "canGoToEmployeeTimes":
            case "canGoToEmployeeService":
                return username.equals("testbusinessowner");
            case "canGoToCustomerHome":
                return username.equals("testcustomer");
        }
        return false;
    }
}
