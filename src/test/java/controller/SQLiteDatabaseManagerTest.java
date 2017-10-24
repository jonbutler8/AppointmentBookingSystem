package controller;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SQLiteDatabaseManagerTest {
    private SQLiteDatabaseManager sqliteDatabaseManager;

    @Before
    public void beforeEachTest() {
        sqliteDatabaseManager = new SQLiteDatabaseManager();
    }

    @Test
    public void getDatabaseName() {
        assertTrue("ABS".equals(sqliteDatabaseManager.getDatabaseName()));
    }

    @Test
    public void getTableNames() {
        String[] tableNames = sqliteDatabaseManager.getTableNames();
        if("userType".equals(tableNames[0]) &&
          "user".equals(tableNames[1]) &&
          "business".equals(tableNames[2]) &&
          "employee".equals(tableNames[3]) &&
          "permissionKey".equals(tableNames[4])) {
              assertTrue("permission".equals(tableNames[5]));
        }
    }
}
