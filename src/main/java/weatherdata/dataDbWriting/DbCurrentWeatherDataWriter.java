package weatherdata.dataDbWriting;

import db.DatabaseConnection;
import db.PostgreSQLJDBC;
import http.getOpenWeather.OpenWeatherDataGetter;
import logger.FileConsoleLogger;
import logger.WeatherLogger;
import weatherdata.dataobject.CurrentWeather;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DbCurrentWeatherDataWriter {

    public static final FileConsoleLogger LOGGER = WeatherLogger.getWeatherLogger();
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final OpenWeatherDataGetter openWeatherDataGetter = new OpenWeatherDataGetter();
    private final DatabaseConnection databaseConnection = new PostgreSQLJDBC();


    public void writeCurrentWeatherIntoDb () {
        LOGGER.info("get weather info from REST api");
        CurrentWeather currentWeather = openWeatherDataGetter.getCurrentWeatherObject();
        LOGGER.info("transform into value object");
        ValueObject valueObject = createCorrespondingDbValueObject(currentWeather);
        LOGGER.info("make update in data base");
        databaseConnection.makeUpdate(createSqlStatementForValueInsertion(valueObject), true);
    }

    private ValueObject createCorrespondingDbValueObject (CurrentWeather currentWeather) {
        return new ValueObject(
            currentWeather.getMain().getTemp(),
            currentWeather.getMain().getTemp_max(),
            currentWeather.getMain().getTemp_min(),
            currentWeather.getMain().getHumidity(),
            currentWeather.getWeather()[0].getDescription(),
            currentWeather.getWind().getSpeed(),
            getCurrentDateTime()
        );
    }

    @SuppressWarnings(value = "all")
    private String createSqlStatementForValueInsertion (ValueObject valueObject) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO public.currentweather")
            .append("(tempCurrent, tempmax, tempmin, humidity, description, v_wind, timestamp) VALUES")
            .append("(").append(valueObject.getTempCurrent()).append(",")
            .append(valueObject.getTempMax()).append(",")
            .append(valueObject.getTempMin()).append(",")
            .append(valueObject.getHumidity()).append(",")
            .append("'").append(valueObject.getDescription()).append("'").append(",")
            .append(valueObject.getVWind()).append(",")
            .append("'").append(valueObject.getTimestamp()).append("'")
            .append(");");
        return sqlBuilder.toString();
    }

    private String getCurrentDateTime () {
        return new SimpleDateFormat(DATE_PATTERN).format(new Date());
    }
}
