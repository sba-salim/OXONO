package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GameAlert {

    // Méthode pour afficher une alerte "Game Over"
    public static void showGameOverAlert(String winner, Runnable onReplay, Runnable onExit) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Alerte de confirmation
        alert.setTitle("Game Over");
        alert.setHeaderText("The winner is: " + winner + "!");
        alert.setContentText("What would you like to do?");

        // Ajouter des boutons
        ButtonType replayButton = new ButtonType("Replay");
        ButtonType exitButton = new ButtonType("Exit");

        alert.getButtonTypes().setAll(replayButton, exitButton); // Ajoute les boutons à l'alerte

        // Gérer les actions selon le bouton choisi
        alert.showAndWait().ifPresent(response -> {
            if (response == replayButton) {
                onReplay.run(); // Exécuter l'action de rejouer
            } else if (response == exitButton) {
                onExit.run(); // Exécuter l'action de quitter
            }
        });

    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
