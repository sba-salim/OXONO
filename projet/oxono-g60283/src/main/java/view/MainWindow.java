package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Game;

public class MainWindow extends BorderPane {
    private final Controller controller;
    //private final SettingsPanel settingsPanel;
    private final BoardPanel boardPanel;
    private final StatusPanel statusPanel;

    public MainWindow(Stage stage, Controller controller) {
        this.controller = controller;

        // Initialiser les panneaux
        boardPanel = new BoardPanel(controller);
        statusPanel = new StatusPanel();

        // Agencement principal
        BorderPane root = new BorderPane();
        root.setCenter(boardPanel.getPane());
        root.setBottom(statusPanel.getPane());

        // Configurer la scène
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Totem Game");
        stage.show();
    }

    // Méthode appelée lorsque le jeu doit être mis à jour
    public void updateView(Game game) {
        boardPanel.update(game);
        statusPanel.update(game);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
    public void drawBoard(int size) {
        boardPanel.drawBoard(size);
    }



    public StatusPanel getStatusPanel() {
        return statusPanel;
    }
}
