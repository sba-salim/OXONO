package g60283.dev.meteo.view;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class InputView extends HBox {
    private final TextField cityField = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final Button submitButton = new Button("Get Weather");

    public InputView() {
        cityField.setPromptText("Enter city name");
        getChildren().addAll(cityField, datePicker, submitButton);
    }

    public String getCity() {
        return cityField.getText();
    }

    public LocalDate getDate() {
        return datePicker.getValue();
    }

    public Button getSubmitButton() {
        return submitButton;
    }
}