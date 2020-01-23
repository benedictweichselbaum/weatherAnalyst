package db;

import db.table.DbTable;
import logger.FileConsoleLogger;
import logger.WeatherLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostgreSQLJDBC implements DatabaseConnection {

    private final static FileConsoleLogger logger = WeatherLogger.createWeatherLogger();

    public PostgreSQLJDBC () {
    }

    @Override
    public void makeUpdate(String sqlStatement, boolean withCommit) throws SQLException {
        List<String> sqlStmt = Collections.singletonList(sqlStatement);
        this.makeMultiUpdates(sqlStmt, withCommit);
    }

    @Override
    public void makeMultiUpdates(List<String> sqlStatements, boolean withCommit) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection  = DriverManager.getConnection("jdbc:postgresql://192.168.178.37:5432/postgres",
                    "pi", "giraffe");
            statement = connection.createStatement();
            for (String sqlStatement : sqlStatements) {
                statement.executeUpdate(sqlStatement);
                logger.info("Executed update: " + sqlStatement);
            }
            if (withCommit) {
                connection.commit();
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        } finally {
            if (statement != null) statement.close();
            if (statement != null) connection.close();
        }
    }

    @Override
    public DbTable makeSelect(String sqlStatement) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://192.168.178.37:5432/postgres",
                    "pi", "giraffe");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            logger.info("Executed query: " + sqlStatement);
            return convertResultSetToDbTable(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private DbTable convertResultSetToDbTable (ResultSet resultSet) throws SQLException{
        if (resultSet != null) {
            DbTable dbTable = new DbTable();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int columnIterator = 1; columnIterator <= metaData.getColumnCount(); ++columnIterator) {
                dbTable.addColumn(metaData.getColumnName(columnIterator));
            }
            while (resultSet.next()) {
                List<String> tuple = new ArrayList<>();
                for (int valueCounter = 1; valueCounter <= metaData.getColumnCount(); ++valueCounter) {
                    tuple.add(resultSet.getString(valueCounter));
                }
                dbTable.addTuples(tuple);
            }
            return dbTable;
        } else {
            return null;
        }
    }
}
