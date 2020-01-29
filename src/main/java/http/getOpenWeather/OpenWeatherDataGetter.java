package main.java.http.getOpenWeather;

import main.java.http.consts.HttpConnectionConstants;
import main.java.logger.FileConsoleLogger;
import main.java.logger.WeatherLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherDataGetter {

    private static final FileConsoleLogger LOGGER = WeatherLogger.createWeatherLogger();

    public static String getResponseString () {

        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(HttpConnectionConstants.OPEN_WEATHER_CURRENT_WEATHER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
