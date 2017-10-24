package controller;

import controller.protocols.DatabaseController;
import controller.protocols.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.*;
import java.util.List;

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
public class SQLiteDatabaseController implements DatabaseController {

    private static final Logger logger =
            LogManager.getLogger(SQLiteDatabaseController.class);
    private static final String DATA_DIR = "data/";

    private DatabaseManager databaseManager;
    private Connection connection = null;
    private PreparedStatement statement = null;

    /**
     * Reads the sqlite.properties file then determines if the database has
     * already been created or not. If no database file exists it will
     * initialise a new one, otherwise it will use the one provided.
     *
     * If a database file is found validation will be run on the database to
     * confirm it's schema. If the database fails validation a new database
     * will be created.
     */
    public SQLiteDatabaseController(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public ResultSet select(String query, List<?> args) {
        try {
            connection = connect();
            statement = connection.prepareStatement(query);
            if(args != null)
                for(int i = 0; i < args.size(); i++)
                    statement.setObject(i+1, args.get(i));
            return statement.executeQuery();
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public int insert(String query, List<?> args) {
        int affectedRows = 0;
        try {
            connection = connect();
            statement = connection.prepareStatement(query);
            if(args != null)
                for(int i = 0; i < args.size(); i++) {
                    statement.setObject(i+1, args.get(i));
                }
            affectedRows = statement.executeUpdate();
            close();
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return affectedRows;
    }

    @Override
    public int insertMultiple(List<String> queries, List<List<?>> args) {
        int affectedRows = 0;
        try {
            connection = connect();
            connection.setAutoCommit(false);
            for(String query : queries) {
                logger.info("Query: " + query);
                statement = connection.prepareStatement(query);
                if(args != null) {
                    logger.info("Found args");
                    List<?> a = args.remove(0);
                    for(int i = 0; i < a.size(); i++) {
                        logger.info("Arg: " + a.get(i));
                        statement.setObject(i+1, a.get(i));
                    }
                }
                affectedRows += statement.executeUpdate();
            }
        }
        catch(SQLException e) {
            rollbackConnection();
            logger.error(e.getMessage(), e);
        }
        finally {
            commitConnection();
            close();
        }
        return affectedRows;
    }

    /**
     * Provides a connection to the SQLite database. This needs to be run
     * before any SQL commands can run. Creates the folder for the database to
     * be held in if it does not exist.
     * @return Returns an open connection to the database.
     */
    private Connection connect() throws SQLException {
        File database = new File(DATA_DIR);
        database.mkdirs();
        return DriverManager.getConnection("jdbc:sqlite:" + DATA_DIR
          + databaseManager.getDatabaseName() + ".db");
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void commitConnection() {
        try {
            connection.commit();
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void close() {
        try {
            statement.close();
            connection.close();
            connection = null;
            statement = null;
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Safely closes a result set - providing error handling. This MUST be used
     * after every select statement.
     * @param resultSet The ResultSet to close.
     */
    @Override
    public void closeResultSet(ResultSet resultSet) {
        try {
            close();
            resultSet.close();
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean resultSetHasNext(ResultSet resultSet) {
        try {
            if(!checkIfClosed(resultSet)) {
                if (resultSet.next())
                    return true;
            }
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public int getInt(ResultSet resultSet, String value) {
        int result = 0;
        try {
            if(!checkIfClosed(resultSet)) {
                result = resultSet.getInt(value);
            }
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public int getInt(ResultSet resultSet, String value, boolean close) {
        int result = 0;
        try {
            if(!checkIfClosed(resultSet)) {
                result = resultSet.getInt(value);
                if (close)
                    closeResultSet(resultSet);
            }
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public long getLong(ResultSet resultSet, String value) {
        try {
            if(!checkIfClosed(resultSet)) {
                return resultSet.getLong(value);
            }
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public String getString(ResultSet resultSet, String value) {
        String result = null;
        try {
            if(!checkIfClosed(resultSet)) {
                result = resultSet.getString(value);
            }
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public String getString(ResultSet resultSet, String value, boolean close) {
        String result = null;
        try {
            if(!checkIfClosed(resultSet)) {
                result = resultSet.getString(value);
                if (close)
                    closeResultSet(resultSet);
            }
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private boolean checkIfClosed(ResultSet resultSet) {
        try {
            return resultSet.isClosed();
        }
        catch(SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}
