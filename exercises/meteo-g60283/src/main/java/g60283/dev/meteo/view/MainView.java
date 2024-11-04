package g60283.dev.meteo.view;

import g60283.dev.meteo.controller.WeatherController;
import g60283.dev.meteo.model.WeatherObject;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;

/**
 * The MainView class represents the main user interface of the weather application.
 * It combines the input view for entering city and date with the weather view for displaying
 * weather data. This class extends BorderPane for layout management.
 */
public class MainView extends BorderPane {
    private final InputView inputView = new InputView();
    private final WeatherView weatherView = new WeatherView();
    private WeatherController controller;  // Controller to handle weather data fetching

    /**
     * Constructs a MainView instance, setting up the layout with input and weather views.
     * It also sets the background color to light blue.
     */
    public MainView() {
        setBottom(inputView);
        setCenter(weatherView);
        setStyle("-fx-background-color: lightblue;"); // Set the background color to light blue

        // Button action to request weather data through the controller
        inputView.getSubmitButton().setOnAction(_ -> {
            String city = inputView.getCity();
            LocalDate date = inputView.getDate();
            if (city == null || city.isEmpty() || date == null) {
                weatherView.showError("Please enter a valid city and date.");
            } else if (controller != null) {  // Check if the controller is defined
                controller.fetchWeather(city, date);
            }
        });
    }

    /**
     * Sets the WeatherController for this view, allowing the view to request weather data.
     *
     * @param controller the WeatherController instance to be used.
     */
    public void setController(WeatherController controller) {
        this.controller = controller;
    }

    /**
     * Updates the weather view with the given weather data.
     *
     * @param weather the WeatherObject containing weather data to display.
     */
    public void updateWeatherView(WeatherObject weather) {
        weatherView.updateWeather(weather, weather.city());
    }

    /**
     * Displays an error message in the weather view.
     *
     * @param message the error message to be displayed.
     */
    public void showError(String message) {
        weatherView.showError(message);
    }
}
