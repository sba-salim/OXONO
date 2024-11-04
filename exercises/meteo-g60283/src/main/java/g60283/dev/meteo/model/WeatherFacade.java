package g60283.dev.meteo.model;

import java.time.LocalDate;
import static g60283.dev.meteo.model.WeatherAPI.fetch;

/**
 * The WeatherFacade class provides a simplified interface for retrieving weather data
 * based on a city and date. It acts as a facade to the WeatherAPI and encapsulates
 * the details of API calls.
 */
public class WeatherFacade {

    /**
     * Retrieves weather data for the specified city and date.
     *
     * @param city the name of the city for which weather data is requested
     * @param date the date for which weather data is requested
     * @return a WeatherObject containing weather details such as temperature and weather code
     * @throws WeatherException if there is an error in fetching the weather data
     */
    public WeatherObject getWeather(String city, LocalDate date) {
        return fetch(city, String.valueOf(date));
    }
}
