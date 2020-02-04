package mainApp;

public class Main {

    public static void main (String... args) {
        Thread currentWeatherThread = new Thread(new CurrentWeatherDataThread());
        currentWeatherThread.start();
    }
}
