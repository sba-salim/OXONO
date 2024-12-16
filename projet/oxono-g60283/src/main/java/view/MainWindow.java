package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Game;


public class MainWindow extends BorderPane {
    //private final SettingsPanel settingsPanel;
    private final BoardPanel boardPanel;
    private final StatusPanel statusPanel;

    public MainWindow(Stage stage, Controller controller) {
        Label title = new javafx.scene.control.Label("OXONO");
        title.getStyleClass().add("title");
        // Initialiser les panneaux
        boardPanel = new BoardPanel(controller);
        statusPanel = new StatusPanel(controller);

        // Agencement principal
        this.setTop(title);
        this.setCenter(boardPanel.getPane());
        this.setBottom(statusPanel.getPane());
        this.getStyleClass().add("window");

        // Configurer la scène
        Scene scene = new Scene(this, 800, 600);
        scene.getStylesheets().add("stylesheets/styles.css");
        stage.setScene(scene);
        stage.setTitle("Totem Game");
        stage.show();
    }

    // Méthode appelée lorsque le jeu doit être mis à jour
    public void updateView(Game game) {
        boardPanel.update(game);
        statusPanel.update(game);
    }

    public void drawBoard(int size) {
        boardPanel.drawBoard(size);
    }



    public StatusPanel getStatusPanel() {
        return statusPanel;
    }
}
