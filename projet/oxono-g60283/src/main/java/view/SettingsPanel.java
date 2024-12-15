package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsPanel {
    private final Controller controller;

    public SettingsPanel(Controller controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {
        VBox root = new VBox(10);

        Label boardSizeLabel = new Label("Enter board size (minimum 4):");
        TextField boardSizeField = new TextField();

        Label modeLabel = new Label("Select game mode:");
        ComboBox<String> modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll("Single Player", "Two Players");
        modeComboBox.getSelectionModel().selectFirst();

        Button startButton = getButton(stage, boardSizeField, modeComboBox);

        root.getChildren().addAll(boardSizeLabel, boardSizeField, modeLabel, modeComboBox, startButton);
        Scene scene = new Scene(root, 400, 200);
        scene.getStylesheets().add("stylesheets/styles.css");
        stage.setScene(scene);
        stage.setTitle("Game Settings");
        stage.show();
    }

    private Button getButton(Stage stage, TextField boardSizeField, ComboBox<String> modeComboBox) {
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            try {
                int size = Integer.parseInt(boardSizeField.getText());
                boolean singlePlayer = modeComboBox.getValue().equals("Single Player");
                if (size >= 4 && size % 2 == 0) {
                    controller.setupGame(size, singlePlayer, stage);
                } else {
                    showError("Invalid board size. Must be an even number and at least 4.");
                }
            } catch (NumberFormatException ex) {
                showError("Invalid input. Please enter a valid number.");
            }
        });
        return startButton;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
