package g60283.dev.meteo.model;

/**
 * A WeatherObject record representing basic weather data for a specific location.
 *
 * @param maxTemp    the maximum temperature for the day
 * @param minTemp    the minimum temperature for the day
 * @param weatherCode the weather code representing weather conditions (e.g., sunny, rainy)
 * @param city       the name of the city for which the weather data applies
 */
public record WeatherObject(double maxTemp, double minTemp, int weatherCode, String city) {
}
