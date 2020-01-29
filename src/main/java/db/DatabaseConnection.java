package main.java.db;

import main.java.db.table.DbTable;
import java.util.List;

public interface DatabaseConnection {

    void makeUpdate (String sqlStatement, boolean withCommit);
    void makeMultiUpdates (List<String> sqlStatements, boolean withCommit);
    DbTable makeSelect (String sqlStatement);
}
