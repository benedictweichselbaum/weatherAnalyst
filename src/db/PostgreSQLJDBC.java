package db;

import logger.FileConsoleLogger;
import logger.WeatherLogger;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class PostgreSQLJDBC implements  DatabaseConnection {

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
    public ResultSet makeSelect(String sqlStatement) throws SQLException {
        Statement statement = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.178.37:5432/postgres",
                    "pi", "giraffe");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            logger.info("Executed query: " + sqlStatement);
            return resultSet;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            if (statement != null) statement.close();
        }
    }
}
