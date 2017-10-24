package controller;

import controller.protocols.DatabaseController;

import java.sql.ResultSet;
import java.util.List;

public class DummyDatabaseController implements DatabaseController {

    private static final String IS_VALID_LOGIN_QUERY =
      "SELECT COUNT(*) FROM `user` WHERE `username` = ? AND `password` = ?";
    private static final String ADD_CUSTOMER_QUERY =
      "INSERT INTO `user` (`username`, `password`, `userTypeID`, " +
      "`phonenumber`, `address`) VALUES (?, ?, (SELECT `id` FROM " +
      "`userType` WHERE `name` = 'customer'), ?, ?)";
    private static final String USER_EXISTS_QUERY =
      "SELECT COUNT(*) FROM `user` WHERE lower(`username`) = lower(?)";
    private static final String GET_USER_TYPE_QUERY =
      "SELECT `name` FROM `userType` " +
      "WHERE `id` = (SELECT `userTypeID` FROM `user` WHERE `id` = ?)";
    private static final String CHECK_KEY_BY_USERNAME_QUERY =
      "SELECT `value` FROM `permission` WHERE `permissionKeyID` = " +
      "(SELECT `id` FROM `permissionKey` WHERE `key` = ?)" +
      "AND `userTypeID` = " +
      "(SELECT `userTypeID` FROM `user` WHERE `username` = ?)";

      private String query;
      private List<?> args;
      private boolean hasNext;

    public DummyDatabaseController(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int insert(String query, List<?> args) {
        if(ADD_CUSTOMER_QUERY.equals(query)) {
            if(args.get(0) != null && args.get(1) != null)
                return 1;
        }
        return 0;
    }

    public ResultSet select(String query, List<?> args) {
        this.query = query;
        this.args = args;
        return null;
    }

    public void closeResultSet(ResultSet resultSet) {

    }

    public boolean resultSetHasNext(ResultSet resultSet) {
        return hasNext;
    }

    public String getString(ResultSet resultSet, String value) {
        if(GET_USER_TYPE_QUERY.equals(query))
            return getUserType();
        return null;
    }

    public String getString(ResultSet resultSet, String value, boolean close) {
        if(GET_USER_TYPE_QUERY.equals(query))
            return getUserType();
        return null;
    }

    private String getUserType() {
        Integer id = (Integer) args.get(0);
        if(1 == id)
            return "customer";
        if(2 == id)
            return "businessOwner";
        return null;
    }

    public int getInt(ResultSet resultSet, String value) {
        return getUserID();
    }

    public int getInt(ResultSet resultSet, String value, boolean close) {
        return getUserID();
    }

    private int getUserID() {
        if (hasNext) {
            if (IS_VALID_LOGIN_QUERY.equals(query)) {
                if ("testcustomer".equals(args.get(0)) ||
                  "testbusinessowner".equals(args.get(0))) {
                    if ("password".equals(args.get(1)))
                        return 1;
                }
            }
            else if (USER_EXISTS_QUERY.equals(query)) {
                if ("testcustomer".equals(args.get(0)) ||
                  "testbusinessowner".equals(args.get(0))) {
                    return 1;
                }
            }
            else if (CHECK_KEY_BY_USERNAME_QUERY.equals(query)) {
                if ("testcustomer".equals(args.get(1))) {
                    if ("canGoToLogin".equals(args.get(0)))
                        return 0;
                    if ("canGoToRegister".equals(args.get(0)))
                        return 0;
                    if ("canGoToAddEmployee".equals(args.get(0)))
                        return 0;
                }
                if ("testbusinessowner".equals(args.get(1))) {
                    if ("canGoToAddEmployee".equals(args.get(0)))
                        return 1;
                }
            }
        }
        return 0;
    }

    public int insertMultiple(List<String> queries, List<List<?>> args) {
        return 2;
    }

    @Override
    public long getLong(ResultSet resultSet, String value) {
        // TODO Auto-generated method stub
        return 0;
    }
}
