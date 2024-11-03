package g60283.dev.meteo.controller;

import g60283.dev.meteo.model.WeatherFacade;
import g60283.dev.meteo.model.WeatherObject;
import g60283.dev.meteo.view.MainView;

import java.time.LocalDate;

public class WeatherController {
    private final WeatherFacade model;
    private final MainView view;

    public WeatherController(WeatherFacade model, MainView view) {
        this.model = model;
        this.view = view;
    }

    public void fetchWeather(String city, LocalDate date) {
        try {
            WeatherObject weatherData = model.getWeather(city, date);
            view.updateWeatherView(weatherData);
        } catch (Exception e) {
            view.showError("Error fetching weather data: " + e.getMessage());
        }
    }
}