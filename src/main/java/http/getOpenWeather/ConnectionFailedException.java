package http.getOpenWeather;

public class ConnectionFailedException extends Exception {
    public ConnectionFailedException(String message) {
        super(message);
    }
}
