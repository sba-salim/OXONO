package g60283.dev.meteo.model;

import java.time.LocalDate;

import static g60283.dev.meteo.model.WeatherAPI.fetch;

public class WeatherFacade {

    public WeatherObject getWeather(String city, LocalDate date) {
        String key = city + date;

        WeatherObject weatherData = fetch(city, String.valueOf(date));
        return weatherData;
    }
}