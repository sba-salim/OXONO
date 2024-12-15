package view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Game;

public class StatusPanel {
    private final HBox pane;
    private final Label statusLabel;

    public StatusPanel() {
        pane = new HBox();
        statusLabel = new Label("Welcome to Totem Game!");
        pane.getChildren().add(statusLabel);
    }

    public void updateMessage(String message) {
        statusLabel.setText(message);
    }

    public HBox getPane() {
        return pane;
    }
    public void update(Game game) {

    }
}
