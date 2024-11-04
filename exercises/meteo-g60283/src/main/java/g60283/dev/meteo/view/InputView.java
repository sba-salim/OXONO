package g60283.dev.meteo.view;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

/**
 * The InputView class provides an interface for user input, allowing the user to
 * enter a city name, select a date, and submit a request to fetch weather data.
 * It extends HBox to arrange the input fields horizontally.
 */
public class InputView extends HBox {
    private final TextField cityField = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final Button submitButton = new Button("Get Weather");

    /**
     * Constructs an InputView with a text field for entering a city name,
     * a date picker for selecting a date, and a button to submit the request.
     */
    public InputView() {
        cityField.setPromptText("Enter city name");
        getChildren().addAll(cityField, datePicker, submitButton);
    }

    /**
     * Retrieves the city name entered by the user.
     *
     * @return a String representing the city name.
     */
    public String getCity() {
        return cityField.getText();
    }

    /**
     * Retrieves the date selected by the user.
     *
     * @return a LocalDate representing the selected date.
     */
    public LocalDate getDate() {
        return datePicker.getValue();
    }

    /**
     * Provides access to the submit button.
     *
     * @return the Button instance for submitting the weather request.
     */
    public Button getSubmitButton() {
        return submitButton;
    }
}
