package mainApp;

import logger.FileConsoleLogger;
import logger.WeatherLogger;
import weatherdata.dataDbWriting.DbCurrentWeatherDataWriter;

public class CurrentWeatherDataThread implements Runnable{

    private static final FileConsoleLogger LOGGER = WeatherLogger.getWeatherLogger();
    private int numberOfRuns = 5;

    @Override
    public void run() {
        DbCurrentWeatherDataWriter dataWriter = new DbCurrentWeatherDataWriter();
        dataWriter.writeCurrentWeatherIntoDb();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
        numberOfRuns--;
        if (numberOfRuns >= 1) {
            run();
        }
    }
}
