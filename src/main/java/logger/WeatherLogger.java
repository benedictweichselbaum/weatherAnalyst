package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WeatherLogger implements FileConsoleLogger {

    private static final String FILE_NAME = "weatherApplicationLog_";
    private static final String FILE_FORMAT_ENDING = ".txt";

    private Logger logger;
    private FileWriter fileWriter;

    public WeatherLogger () {
        this.logger = Logger.getLogger("weatherLogger");
        try {
            String fileName = FILE_NAME + getCurrentDate() + FILE_FORMAT_ENDING;
            this.fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            this.error("Logger create failed!");
            this.error(e.getMessage());
        }
    }

    @Override
    public void info(String message) {
        String msg = getCurrentDate() + " " + message;
        logger.log(Level.INFO, msg);
        writeIntoFile(msg);
    }

    @Override
    public void warn(String message) {
        String msg = getCurrentDate() + " " + message;
        logger.log(Level.WARNING, msg);
        writeIntoFile(msg);
    }

    @Override
    public void error(String error) {
        String msg = getCurrentDate() + " " + error;
        logger.log(Level.SEVERE, msg);
        writeIntoFile(msg);
    }

    private String getCurrentDate () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime today = LocalDateTime.now();
        return formatter.format(today);
    }

    private void writeIntoFile (String msg) {
        try {
            fileWriter.write(msg);
            fileWriter.flush();
        } catch (IOException e) {
            this.error(e.getMessage());
        }
    }

    public static WeatherLogger createWeatherLogger () {
        return new WeatherLogger();
    }
}
