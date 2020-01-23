import db.DatabaseConnection;
import db.PostgreSQLJDBC;
import db.table.DbTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main (String... args) throws SQLException {
        DatabaseConnection databaseConnection = new PostgreSQLJDBC();
        String s;
        Scanner scanner = new Scanner(System.in);
        s = scanner.nextLine();
        if (s.startsWith("select")) {
            try {
                DbTable result = databaseConnection.makeSelect(s);
                System.out.println(result.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            databaseConnection.makeUpdate(s, false);
        }
    }
}
