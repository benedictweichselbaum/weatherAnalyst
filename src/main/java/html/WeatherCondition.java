package html;

import lombok.Getter;

@Getter
public enum WeatherCondition {

    SUN("sun", "Sonnig", "/sunny.gif"),

    CLOUDY("cloud", "Wolkig", "/cloudy.gif"),

    RAIN("rain", "Regen", "/rain.gif"),

    SNOW("snow", "Schnee", "/snow.gif"),

    THUNDERSTORM("thunder", "Gewitter/Sturm", "/thunderStorm"),

    DEFAULT("default", "Unbekannt", "/default.gif");

    WeatherCondition(String description, String germanDescription, String relativePathToPic) {
        this.description = description;
        this.germanDescription = germanDescription;
        this.relativePathToPic = "/weatherSymboles" + relativePathToPic;
    }

    private String description;
    private String germanDescription;
    private String relativePathToPic;
}
