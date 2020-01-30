package mainApp;

import logger.FileConsoleLogger;
import logger.WeatherLogger;
import weatherdata.dataDbWriting.DbCurrentWeatherDataWriter;

public class CurrentWeatherDataThread implements Runnable{

    private static final FileConsoleLogger LOGGER = WeatherLogger.createWeatherLogger();

    @Override
    public void run() {
        DbCurrentWeatherDataWriter dataWriter = new DbCurrentWeatherDataWriter();
        dataWriter.writeCurrentWeatherIntoDb();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            LOGGER.error("error in CurrentWeatherDataThread: sleep failed");
        } finally {
            run();
        }
    }
}
