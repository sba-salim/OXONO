package g60283.dev.meteo.view;

import g60283.dev.meteo.model.WeatherObject;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

public class WeatherView extends VBox {
    private final Label cityLabel = new Label();
    private final Label maxTempLabel = new Label();
    private final Label minTempLabel = new Label();
    private final ImageView weatherIcon = new ImageView();

    public WeatherView() {
        setAlignment(Pos.CENTER);
        getChildren().addAll(cityLabel, maxTempLabel, minTempLabel, weatherIcon);
    }

    public void updateWeather(WeatherObject weather, String city) {
        cityLabel.setText(city);
        maxTempLabel.setText("Max: " + weather.maxTemp() + "°");
        minTempLabel.setText("Min: " + weather.minTemp() + "°");
        String iconPath = getImageForWeatherCode(weather.weatherCode());
        if (iconPath != null) {
            weatherIcon.setImage(new Image(iconPath));
            weatherIcon.setFitHeight(50);
            weatherIcon.setPreserveRatio(true);
        }
    }

    private String getImageForWeatherCode(int weatherCode) {
        // Similar to the method in your App class
        switch (weatherCode) {
            case 0, 1:
                return "http://openweathermap.org/img/wn/01d@2x.png";
            case 2:
                return "http://openweathermap.org/img/wn/02d@2x.png";
            case 3:
                return "http://openweathermap.org/img/wn/03d@2x.png";
            case 48:
                return "http://openweathermap.org/img/wn/50d@2x.png";

            case 51, 53, 55:
                return "http://openweathermap.org/img/wn/09d@2x.png";
            case 56, 57:
                return "http://openweathermap.org/img/wn/09d@2x.png";

            case 61, 63, 65:
                return "http://openweathermap.org/img/wn/10d@2x.png";
            case 66, 67:
                return "http://openweathermap.org/img/wn/10d@2x.png";

            case 71, 73, 75, 77:
                return "http://openweathermap.org/img/wn/13d@2x.png";

            case 80, 81, 82:
                return "http://openweathermap.org/img/wn/09d@2x.png";
            case 85, 86:
                return "http://openweathermap.org/img/wn/13d@2x.png";
            case 95:
                return "http://openweathermap.org/img/wn/11d@2x.png";
            case 96, 99:
                return "http://openweathermap.org/img/wn/11d@2x.png";

            default:
                return null;
        }
    }

    public void showError(String message) {
        cityLabel.setText(message);
        maxTempLabel.setText("");
        minTempLabel.setText("");
        weatherIcon.setImage(null);
    }
}
