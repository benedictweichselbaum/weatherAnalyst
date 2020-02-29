package html;

import logger.FileConsoleLogger;
import logger.WeatherLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import weatherdata.dataDbWriting.ValueObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HtmlWeatherManipulator {

    private static final FileConsoleLogger LOGGER = WeatherLogger.getWeatherLogger();

    public static void manipulateHtmlWithCurrentWeatherData (String htmlFile, ValueObject currentWeather) {
        try {
            Document html = Jsoup.parse(getHtmlFileAsString(htmlFile));
            setCurrentTemp(currentWeather, html);
            setMinMaxTemp(currentWeather, html);
            setDescriptionText(currentWeather, html);
            setDescriptionPicture(currentWeather, html);
            Files.write(Paths.get(
                    "/home/bweichselbaum/Schreibtisch/website/index.html"),
                    html.html().getBytes());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void setDescriptionPicture(ValueObject currentWeather, Document html) {
    }

    private static void setDescriptionText (ValueObject currentWeather, Document html) {
        Element descText = html.getElementById("descriptionText");
        String desc = currentWeather.getDescription();
        if (desc.contains("sun")) {

        }
    }

    private static void setMinMaxTemp (ValueObject currentWeather, Document html) {
        Element minmax = html.getElementById("minmax");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Min: ")
                .append(kelvinToCelcius(currentWeather.getTempMin()))
                .append(" C° Max: ")
                .append(kelvinToCelcius(currentWeather.getTempMax()))
                .append(" C°");
        minmax.text(stringBuilder.toString());
    }

    private static void setCurrentTemp(ValueObject currentWeather, Document html) {
        Element currentTemperature = html.getElementById("currentTemperature");
        currentTemperature.text(kelvinToCelcius(currentWeather.getTempCurrent()) + " °C");
    }

    private static String getHtmlFileAsString (String htmlFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stringStream = Files.lines(Paths.get(htmlFile), StandardCharsets.UTF_8)) {
            stringStream.forEach(s -> contentBuilder.append(s).append("\n"));
            return contentBuilder.toString();
        }
    }

    private static String kelvinToCelcius (Double kelvin) {
        String[] number = String.valueOf(kelvin - 273.15).split("[.]");
        return new StringBuilder()
                .append(number[0])
                .append(",")
                .append(String.valueOf(number[1]), 0, 1)
                .toString();
    }
}
