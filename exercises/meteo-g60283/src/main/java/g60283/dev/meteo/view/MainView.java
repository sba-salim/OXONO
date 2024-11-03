package g60283.dev.meteo.view;

import g60283.dev.meteo.controller.WeatherController;
import g60283.dev.meteo.model.WeatherObject;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;

public class MainView extends BorderPane {
    private final InputView inputView = new InputView();
    private final WeatherView weatherView = new WeatherView();
    private final WeatherController controller;

    public MainView(WeatherController controller) {
        this.controller = controller;

        setTop(inputView);
        setCenter(weatherView);

        // Button action to request weather data through the controller
        inputView.getSubmitButton().setOnAction(event -> {
            String city = inputView.getCity();
            LocalDate date = inputView.getDate();
            if (city == null || city.isEmpty() || date == null) {
                weatherView.showError("Please enter a valid city and date.");
            } else {
                controller.fetchWeather(city, date);
            }
        });
    }

    public void updateWeatherView(WeatherObject weather) {
        weatherView.updateWeather(weather, weather.city());
    }

    public void showError(String message) {
        weatherView.showError(message);
    }
}