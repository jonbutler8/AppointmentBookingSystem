package controller;

import controller.protocols.DatabaseManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SQLiteDatabaseManager implements DatabaseManager {

    private static final Logger logger =
            LogManager.getLogger(SQLiteDatabaseManager.class);
    private static final String CONFIG_RESOURCE_DIR = "/config/";
    private static final String SQLITE_CONFIG_FILENAME = "sqlite.properties";

    private String databaseName;
    private String[] tableNames;


    public SQLiteDatabaseManager() {
        getProperties();
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String[] getTableNames() {
        return tableNames;
    }

    /**
     * Reads the resources/config/sqlite.properties file to determine database
     * and table names.
     */
    private void getProperties() {
        InputStream inputStream;
        Properties properties = new Properties();
        // Get config file
        inputStream = getClass().getResourceAsStream(CONFIG_RESOURCE_DIR +
          SQLITE_CONFIG_FILENAME);
        // If config file is found, get database properties from file
        try {
            if (inputStream != null) {
                properties.load(inputStream);
                logger.info("Successfully loaded "+SQLITE_CONFIG_FILENAME);
                databaseName = properties.getProperty("database");
                logger.info("Property found: database = "+databaseName);
                tableNames = properties.getProperty("tables").split(":");
                logger.info("Property found: tables = " +
                  properties.getProperty("tables"));
                inputStream.close();
            }
            else {
                throw new FileNotFoundException();
            }
        }
        catch(FileNotFoundException e) {
            logger.fatal("Can not find config file: " +
              SQLITE_CONFIG_FILENAME, e);
        }
        catch(IOException e) {
            logger.fatal("IOException when reading " +
              SQLITE_CONFIG_FILENAME, e);
        }
    }
}
