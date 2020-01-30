package weatherdata.dataobject;

import lombok.Data;

@Data
public class CurrentWeather {

    Coordinates coord;
    WeatherDescription[] weather;
    String base;
    // Temperature information
    MainInformation main;
    Integer visibility;
    Wind wind;
    Clouds clouds;
    Long dt;
    AdditionalInformation sys;
    Integer timezone;
    Long id;
    String name;
    Integer cod;

    @Data
    public class AdditionalInformation {
        Integer type;
        Integer id;
        String country;
        Long sunrise;
        Long sunset;
    }

    @Data
    public class Clouds {
        Integer all;
    }

    @Data
    public class Coordinates {
        Double lon;
        Double lat;
    }

    @Data
    public class MainInformation {
        Double temp;
        Double feelsLike;
        Double temp_min;
        Double temp_max;
        Integer pressure;
        Integer humidity;
    }

    @Data
    public class WeatherDescription {
        Integer id;
        // Main weather information like 'Clouds'
        String main;
        String description;
        String icon;
    }

    @Data
    public class Wind {
        Double speed;
        // Degree
        Integer deg;
    }

}
