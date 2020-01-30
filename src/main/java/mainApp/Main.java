package mainApp;

import db.DatabaseConnection;
import db.PostgreSQLJDBC;
import http.getOpenWeather.OpenWeatherDataGetter;

import java.util.Scanner;

public class Main {

    private final DatabaseConnection databaseConnection = new PostgreSQLJDBC();

    public static void main (String... args) {
//        final DatabaseConnection databaseConnection = new PostgreSQLJDBC();
//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.nextLine();
//        databaseConnection.makeUpdate(s, true);
        Thread currentWeatherThread = new Thread(new CurrentWeatherDataThread());
        currentWeatherThread.start();

        /*
        DatabaseConnection databaseConnection = new PostgreSQLJDBC();
        String s;
        Scanner scanner = new Scanner(System.in);
        s = scanner.nextLine();
        if (s.startsWith("select")) {
            DbTable result = databaseConnection.makeSelect(s);
            System.out.println(result.toString());
        } else {
            databaseConnection.makeUpdate(s, false);
        }*/
    }
}
