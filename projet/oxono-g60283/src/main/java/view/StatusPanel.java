package view;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Game;

public class StatusPanel {
    private final HBox pane;
    private final Label statusLabel;
    private final Label currentPlayerLabel;
    private final Label tokensLabel;
    private final Label emptyCellsLabel;

    public StatusPanel(Controller controller) {
        pane = new HBox(10); // Espace entre les éléments
        pane.getStyleClass().add("status-panel"); // Ajout de la classe CSS pour le conteneur

        statusLabel = new Label("Welcome to Totem Game!");
        statusLabel.getStyleClass().add("status-label"); // Classe CSS pour le label de statut

        currentPlayerLabel = new Label("Current Player: ");
        currentPlayerLabel.getStyleClass().add("current-player-label"); // Classe CSS pour le joueur actuel

        tokensLabel = new Label("Tokens Remaining: ");
        tokensLabel.getStyleClass().add("tokens-label"); // Classe CSS pour les jetons restants

        emptyCellsLabel = new Label("Empty Cells: ");
        emptyCellsLabel.getStyleClass().add("empty-cells-label"); // Classe CSS pour les cases vides

        // Bouton "Surrender"
        Button surrenderButton = new Button("Surrender");
        surrenderButton.setOnAction(_ -> controller.handleQuit()); // Appelle la méthode du contrôleur
        surrenderButton.getStyleClass().add("surrender-button"); // Classe CSS pour styliser le bouton

        pane.getChildren().addAll(statusLabel, currentPlayerLabel, tokensLabel, emptyCellsLabel, surrenderButton);
    }

    public void updateMessage(String message) {
        statusLabel.setText(message);
    }

    public HBox getPane() {
        return pane;
    }

    public void update(Game game) {
        // Mettre à jour le joueur actuel
        currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayersColor());

        // Mettre à jour le nombre de jetons restants pour le joueur actuel
        int remainingXPawns = game.getCurrentPlayerXPawns();
        int remainingOPawns = game.getCurrentPlayerOPawns();
        tokensLabel.setText("X : " + remainingXPawns + "\n" + "O : " + remainingOPawns);


        // Mettre à jour le nombre de cases vides sur le plateau
        int emptyCells = game.emptyCells();
        emptyCellsLabel.setText("Empty Cells: " + emptyCells);
    }
}
