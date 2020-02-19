package http.getOpenWeather;

import com.google.gson.Gson;
import http.consts.HttpConnectionConstants;
import logger.FileConsoleLogger;
import logger.WeatherLogger;
import weatherdata.dataobject.CurrentWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherDataGetter {

    private static final FileConsoleLogger LOGGER = WeatherLogger.getWeatherLogger();

    public CurrentWeather getCurrentWeatherObject () {
        try {
            String json = getCurrentWeatherStringJsonFromOpenWeather();
            if (json == null) {
                throw new ConnectionFailedException("connection to open weather failed");
            }
            Gson gson = new Gson();
            return gson.fromJson(json, CurrentWeather.class);
        } catch (ConnectionFailedException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private String getCurrentWeatherStringJsonFromOpenWeather() {

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
