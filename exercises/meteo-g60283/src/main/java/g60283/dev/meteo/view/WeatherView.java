package g60283.dev.meteo.view;

import g60283.dev.meteo.model.WeatherObject;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

/**
 * The WeatherView class is responsible for displaying the weather information for a specific city.
 * It shows the city name, maximum and minimum temperatures, and an icon representing the weather conditions.
 * This class extends VBox to arrange the components vertically.
 */
public class WeatherView extends VBox {
    private final Label cityLabel = new Label(); // Label to display the city name
    private final Label maxTempLabel = new Label(); // Label to display maximum temperature
    private final Label minTempLabel = new Label(); // Label to display minimum temperature
    private final ImageView weatherIcon = new ImageView(); // ImageView to display the weather icon

    /**
     * Constructs a WeatherView instance and sets up the layout.
     * The components are aligned to the center.
     */
    public WeatherView() {
        setAlignment(Pos.CENTER);
        getChildren().addAll(cityLabel, maxTempLabel, minTempLabel, weatherIcon);
    }

    /**
     * Updates the weather display with the provided weather data and city name.
     *
     * @param weather the WeatherObject containing weather data to display.
     * @param city the name of the city for which the weather is displayed.
     */
    public void updateWeather(WeatherObject weather, String city) {
        cityLabel.setText(city); // Set the city name
        maxTempLabel.setText("Max: " + weather.maxTemp() + "°"); // Set max temperature
        minTempLabel.setText("Min: " + weather.minTemp() + "°"); // Set min temperature

        String iconPath = getImageForWeatherCode(weather.weatherCode()); // Get the icon path
        if (iconPath != null) {
            weatherIcon.setImage(new Image(iconPath)); // Set the icon image
            weatherIcon.setFitHeight(50); // Set the height of the icon
            weatherIcon.setPreserveRatio(true); // Preserve the aspect ratio
        } else {
            weatherIcon.setImage(new Image("https://images.app.goo.gl/BT8VEBMEHcHGXbub9")); // Default icon if no valid icon found
        }
    }

    /**
     * Returns the image URL corresponding to the provided weather code.
     *
     * @param weatherCode the weather code representing the current weather conditions.
     * @return the URL of the weather icon, or null if no icon is available for the code.
     */
    private String getImageForWeatherCode(int weatherCode) {
        return switch (weatherCode) {
            case 0, 1 -> "https://openweathermap.org/img/wn/01d@2x.png"; // Clear sky
            case 2 -> "https://openweathermap.org/img/wn/02d@2x.png"; // Few clouds
            case 3 -> "https://openweathermap.org/img/wn/03d@2x.png"; // Scattered clouds
            case 48 -> "https://openweathermap.org/img/wn/50d@2x.png"; // Mist
            case 51, 53, 55, 56, 57, 80, 81, 82 -> "https://openweathermap.org/img/wn/09d@2x.png"; // Rain
            case 61, 63, 65, 66, 67 -> "https://openweathermap.org/img/wn/10d@2x.png"; // Snow
            case 71, 73, 75, 77, 85, 86 -> "https://openweathermap.org/img/wn/13d@2x.png"; // Snow
            case 95, 96, 99 -> "https://openweathermap.org/img/wn/11d@2x.png"; // Thunderstorm
            default -> null; // No icon available
        };
    }

    /**
     * Displays an error message in the weather view.
     *
     * @param message the error message to be displayed.
     */
    public void showError(String message) {
        cityLabel.setText(message); // Set the error message as the city label
        maxTempLabel.setText(""); // Clear max temperature label
        minTempLabel.setText(""); // Clear min temperature label
        weatherIcon.setImage(null); // Clear weather icon
    }
}
