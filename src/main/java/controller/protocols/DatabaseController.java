package controller.protocols;

import java.sql.ResultSet;
import java.util.List;

public interface DatabaseController {
    int insert(String query, List<?> args);
    int insertMultiple(List<String> queries, List<List<?>> args);
    ResultSet select(String query, List<?> args);
    void closeResultSet(ResultSet resultSet);
    boolean resultSetHasNext(ResultSet resultSet);
    String getString(ResultSet resultSet, String value);
    String getString(ResultSet resultSet, String value, boolean close);
    int getInt(ResultSet resultSet, String value);
    int getInt(ResultSet resultSet, String value, boolean close);
    long getLong(ResultSet resultSet, String value);
}
