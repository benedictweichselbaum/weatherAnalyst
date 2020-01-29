package main.java;

import main.java.http.getOpenWeather.OpenWeatherDataGetter;

public class Main {

    public static void main (String... args) {

        System.out.println(OpenWeatherDataGetter.getResponseString());

        /*DatabaseConnection databaseConnection = new PostgreSQLJDBC();
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
