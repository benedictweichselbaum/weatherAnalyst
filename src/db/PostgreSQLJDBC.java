package db;

import db.table.DbTable;
import logger.FileConsoleLogger;
import logger.WeatherLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostgreSQLJDBC implements DatabaseConnection {

    private static final FileConsoleLogger LOGGER = WeatherLogger.createWeatherLogger();

    @Override
    public void makeUpdate(String sqlStatement, boolean withCommit) {
        List<String> sqlStmt = Collections.singletonList(sqlStatement);
        this.makeMultiUpdates(sqlStmt, withCommit);
    }

    @Override
    public void makeMultiUpdates(List<String> sqlStatements, boolean withCommit) {
        try (Connection connection = this.getDbConnection();
             Statement statement = connection.createStatement()) {
            for (String sqlStatement : sqlStatements) {
                statement.executeUpdate(sqlStatement);
                LOGGER.info("Executed update: " + sqlStatement);
            }
            if (withCommit) {
                connection.commit();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public DbTable makeSelect(String sqlStatement) {
        try (Connection connection = this.getDbConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement)) {
            LOGGER.info("Executed query: " + sqlStatement);
            return convertResultSetToDbTable(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return null;
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

    private Connection getDbConnection () throws SQLException {
        return DriverManager.getConnection(DbConnectionConstants.DB_CONNECTION_URL, DbConnectionConstants.DB_USERNAME,
                        DbConnectionConstants.DB_PASSWORD);
    }
}
