package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WeatherLogger implements FileConsoleLogger{

    private Logger logger;
    private FileWriter fileWriter;

    public WeatherLogger () {
        this.logger = Logger.getLogger("weatherLogger");
        try {
            this.fileWriter = new FileWriter("./log.txt");
        } catch (IOException e) {
            this.error("Logger create failed!");
            this.error(e.getMessage());
        }
    }

    @Override
    public void info(String message) {
        String msg = getCurrentDate() + " " + message;
        logger.log(Level.INFO, msg);
        try {
            fileWriter.write(msg);
        } catch (IOException e) {
            this.error(e.getMessage());
        }
    }

    @Override
    public void warn(String message) {
        String msg = getCurrentDate() + " " + message;
        logger.log(Level.WARNING, msg);
        try {
            fileWriter.write(msg);
        } catch (IOException e) {
            this.error(e.getMessage());
        }
    }

    @Override
    public void error(String error) {
        String msg = getCurrentDate() + " " + error;
        logger.log(Level.SEVERE, msg);
        try {
            fileWriter.write(msg);
        } catch (IOException e) {
            this.error(e.getMessage());
        }
    }

    private String getCurrentDate () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime today = LocalDateTime.now();
        return formatter.format(today);
    }

    public static WeatherLogger createWeatherLogger () {
        return new WeatherLogger();
    }
}
