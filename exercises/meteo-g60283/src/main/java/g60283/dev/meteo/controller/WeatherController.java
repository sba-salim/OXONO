package g60283.dev.meteo.controller;

import g60283.dev.meteo.model.WeatherFacade;
import g60283.dev.meteo.model.WeatherObject;
import g60283.dev.meteo.view.MainView;

import java.time.LocalDate;

/**
 * The WeatherController class controls the flow of data between the view and the model
 * for weather-related information. It fetches weather data through the model and updates
 * the view accordingly.
 */
public class WeatherController {
    private final WeatherFacade model;
    private final MainView view;

    /**
     * Constructs a WeatherController with the specified model and view.
     *
     * @param model the WeatherFacade model to interact with for fetching weather data
     * @param view  the MainView view to update with weather data or error messages
     */
    public WeatherController(WeatherFacade model, MainView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Fetches weather data for a specified city and date, updating the view with
     * the data retrieved or showing an error message if fetching fails.
     *
     * @param city the name of the city for which weather data is to be retrieved
     * @param date the date for which weather data is to be retrieved
     */
    public void fetchWeather(String city, LocalDate date) {
        try {
            WeatherObject weatherData = model.getWeather(city, date);
            view.updateWeatherView(weatherData);
        } catch (Exception e) {
            view.showError("Error fetching weather data: " + e.getMessage());
        }
    }
}
