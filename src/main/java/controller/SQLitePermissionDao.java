package controller;

import controller.protocols.DatabaseController;
import controller.protocols.PermissionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLitePermissionDao implements PermissionDao {

    private static final Logger logger =
      LogManager.getLogger(SQLitePermissionDao.class);
    private DatabaseController databaseController;

    SQLitePermissionDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public boolean checkKeyByUsername(String key, String username) {
        ResultSet resultSet;
        String query;

        query = "SELECT `value` FROM `permission` WHERE `permissionKeyID` = " +
          "(SELECT `id` FROM `permissionKey` WHERE `key` = ?)" +
          "AND `userTypeID` = " +
          "(SELECT `userTypeID` FROM `user` WHERE `username` = ?)";

        logger.info(String.format("Running permission check [key,user]: " +
          "[%s,%s]", key, username));

        resultSet = databaseController.select(query,
          new ArrayList<>(Arrays.asList(key, username)));

        return databaseController.getInt(resultSet, "value", true) > 0;
    }
}
