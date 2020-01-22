package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseConnection {

    void makeUpdate (String sqlStatement, boolean withCommit) throws SQLException;
    void makeMultiUpdates (List<String> sqlStatements, boolean withCommit) throws SQLException;
    ResultSet makeSelect (String sqlStatement) throws SQLException;
}
