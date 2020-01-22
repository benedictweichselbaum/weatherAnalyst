import db.DatabaseConnection;
import db.PostgreSQLJDBC;

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
                ResultSet rs = databaseConnection.makeSelect(s);
                if (rs != null) {
                    while (rs.next()) {
                        System.out.println("Num = " + rs.getInt("num") +
                                " Name = " + rs.getString("name") +
                                " Lebensalter = " + rs.getInt("lebensalter"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            databaseConnection.makeUpdate(s, false);
        }
    }
}
