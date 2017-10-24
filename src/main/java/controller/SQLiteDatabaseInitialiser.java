package controller;

import com.google.common.io.CharStreams;
import controller.protocols.DatabaseController;
import controller.protocols.DatabaseInitialiser;
import controller.protocols.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Handles initialising the SQLite Database file, validating tables within the
 * database and provides methods to create/close a connection to the database
 * with exception handling.
 *
 * The only purpose for other classes to utilise this will be to create/close a
 * connection.
 *
 * Uses resources/config/sqlite.properties file to determine database and table
 * names.
 */
public class SQLiteDatabaseInitialiser implements DatabaseInitialiser {

    private static final Logger logger =
            LogManager.getLogger(SQLiteDatabaseInitialiser.class);
    private static final String DATA_DIR = "data/";
    private static final String SQL_RESOURCE_DIR = "/sql/";

    private DatabaseController databaseController;
    private String databaseName;
    private String[] tableNames;

    /**
     * Reads the sqlite.properties file then determines if the database has
     * already been created or not. If no database file exists it will
     * initialise a new one, otherwise it will use the one provided.
     *
     * If a database file is found validation will be run on the database to
     * confirm it's schema. If the database fails validation a new database
     * will be created.
     */
    public SQLiteDatabaseInitialiser(DatabaseManager databaseManager,
      DatabaseController databaseController) {
        this.databaseController = databaseController;
        databaseName = databaseManager.getDatabaseName();
        tableNames = databaseManager.getTableNames();
    }

    @Override
    public void init() {
        if(!verifyDatabase()) {
            backupDatabase();
            initDatabase();
        }
    }

    /**
     * Begins the process of initialising all tables provided in the
     * sqlite.properties file.
     */
    private void initDatabase() {
        for(String tableName : tableNames) {
            if(initTable(tableName))
                logger.info("Successfully added table: " + tableName);
            if(initTable(tableName + "-insert"))
                logger.info("Successfully inserted data into: " + tableName);
        }
    }

    /**
     * Initialises a single table run by the initDatabase() method. This is
     * also used to insert base data into the tables after creation.
     */
    private boolean initTable(String tableName) {
        InputStream inputStream;
        String file;
        String[] queries;

        inputStream = getClass().getResourceAsStream(SQL_RESOURCE_DIR +
          tableName + ".sql");
        if(inputStream != null) {
            try (
                InputStreamReader isReader =
                  new InputStreamReader(inputStream, StandardCharsets.UTF_8)
            ) {
                file = CharStreams.toString(isReader);
                queries = file.split(";");
                for(String query : queries)
                    databaseController.insert(query, null);
                inputStream.close();
                return true;
            }
            catch(IOException e) {
                logger.fatal(e.getMessage(), e);
            }
        }
        else
            logger.info(String.format("SQL File Missing: %s.sql ",tableName));
        return false;
    }

    private boolean verifyDatabase() {
        for(String tableName : tableNames) {
            if(verifyTable(tableName)) {
                logger.info("Successfully verified table: " + tableName);
            }
            else {
                logger.info("Failed verifying table: " + tableName);
                return false;
            }
        }
        return true;
    }

    private boolean verifyTable(String tableName) {
        InputStream inputStream;
        ResultSet resultSet;
        String query;
        String sql;

        inputStream = getClass().getResourceAsStream(SQL_RESOURCE_DIR +
          tableName + ".sql");
        query = "SELECT `sql` FROM `sqlite_master` WHERE `name` = ?";
        try (
            InputStreamReader inputStreamReader =
              new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        ) {
            resultSet = databaseController.select(query,
              new ArrayList<>(Arrays.asList(tableName)));
            sql = databaseController.getString(resultSet, "sql", true);
            query = CharStreams.toString(inputStreamReader);
            inputStream.close();
            if(sql != null)
                if(sql.equals(query))
                    return true;
                else {
                    logger.info("Replace SQL:" + sql);
                }
        }
        catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    private void backupDatabase() {
        File database;
        File backupDatabase;
        String timestamp;
        String newFilename;
        String currentFilename;

        timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        newFilename = DATA_DIR + databaseName +"-"+ timestamp + ".bak";
        currentFilename = DATA_DIR + databaseName + ".db";

        database = new File(currentFilename);
        backupDatabase = new File(newFilename);

        if(database.length() != 0) {
            if(database.renameTo(backupDatabase))
                logger.info("Backed up old database");
            else
                logger.error("Failure backing up old database");
        }
        else
            logger.info("Database is empty: Skipping backup");
    }
}
