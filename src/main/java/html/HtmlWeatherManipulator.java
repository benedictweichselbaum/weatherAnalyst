package html;

import logger.FileConsoleLogger;
import logger.WeatherLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pathConstants.GlobalFilePathConstants;
import weatherdata.dataDbWriting.ValueObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HtmlWeatherManipulator {

    private static final FileConsoleLogger LOGGER = WeatherLogger.getWeatherLogger();

    public static void manipulateHtmlWithCurrentWeatherData (String htmlFile, ValueObject currentWeather) {
        try {
            Document html = Jsoup.parse(getHtmlFileAsString(htmlFile));
            setCurrentTemp(currentWeather, html);
            setMinMaxTemp(currentWeather, html);
            setDescription(currentWeather, html);
            Files.write(Paths.get(
                    GlobalFilePathConstants.BASE_PATH_DELIVERY_HTML + "/index.html"),
                    html.html().getBytes());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void setDescription(ValueObject currentWeather, Document html) {
        Element descText = html.getElementById("descriptionText");
        Element descPic = html.getElementById("descriptionPic");
        WeatherCondition weatherCondition = getCorrectWeatherCondition(currentWeather.getDescription());
        descText.text(weatherCondition.getGermanDescription());
        descPic.attr("src", GlobalFilePathConstants.BASE_PATH_DELIVERY_HTML + weatherCondition.getRelativePathToPic());
    }

    private static void setMinMaxTemp (ValueObject currentWeather, Document html) {
        Element minmax = html.getElementById("minmax");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Min: ")
                .append(kelvinToCelsius(currentWeather.getTempMin()))
                .append(" °C Max: ")
                .append(kelvinToCelsius(currentWeather.getTempMax()))
                .append(" °C");
        minmax.text(stringBuilder.toString());
    }

    private static void setCurrentTemp(ValueObject currentWeather, Document html) {
        Element currentTemperature = html.getElementById("currentTemperature");
        currentTemperature.text(kelvinToCelsius(currentWeather.getTempCurrent()) + " °C");
    }

    private static String getHtmlFileAsString (String htmlFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stringStream = Files.lines(Paths.get(htmlFile), StandardCharsets.UTF_8)) {
            stringStream.forEach(s -> contentBuilder.append(s).append("\n"));
            return contentBuilder.toString();
        }
    }

    private static String kelvinToCelsius(Double kelvin) {
        String[] number = String.valueOf(kelvin - 273.15).split("[.]");
        return new StringBuilder()
                .append(number[0])
                .append(",")
                .append(String.valueOf(number[1]), 0, 1)
                .toString();
    }

    private static WeatherCondition getCorrectWeatherCondition (@NonNull String descriptionText) {
        List<WeatherCondition> weatherConditions = Arrays.asList(WeatherCondition.values());
        return weatherConditions.stream()
                .filter(weatherCondition -> descriptionText.contains(weatherCondition.getDescription()))
                .findAny()
                .orElse(WeatherCondition.DEFAULT);
    }
}
