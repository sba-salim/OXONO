package view;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.*;

public class BoardPanel extends GridPane {
    private final Controller controller;

    public BoardPanel(Controller controller) {
        this.controller = controller;
        this.setGridLinesVisible(true);
        this.getStyleClass().add("grid");
    }

    public void drawBoard(int size) {
        this.getChildren().clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageButton cell = new ImageButton();  // Créez un ImageButton au lieu d'un Button
                cell.setPrefSize(50, 50);  // Taille par défaut du bouton

                // Définir un comportement pour chaque bouton
                final int x = i;
                final int y = j;

                // Quand le bouton est cliqué, appelez la méthode de gestion du clic
                cell.setOnMouseClicked(_ -> controller.handleCellClick(x, y));

                // Ajoutez le bouton à la grille
                this.add(cell, j, i);
            }
        }
    }

    public void update(Game game) {
        this.getChildren().forEach(node -> {
            ImageButton cell = (ImageButton) node;
            int x = GridPane.getRowIndex(cell);
            int y = GridPane.getColumnIndex(cell);

            Token token = game.getTokenAt(new Position(x, y));
            cell.setGraphic(null); // Supprime l'image actuelle

            if (token != null) {
                String color = (token instanceof Pawn) ? ((Pawn) token).getC().toString().toLowerCase() : "blue";
                String symbol = token.getS() == Symbol.X ? "cross" : "circle";
                String imagePath = "pictures/" + color + "-" + symbol + ".png";
                ImageView imageView = new ImageView(new Image(imagePath));
                imageView.setFitWidth(35); // Largeur fixée
                imageView.setFitHeight(40); // Hauteur fixée
                cell.setGraphic(imageView); // Applique l'image au bouton
            }
        });
    }



    public GridPane getPane() {
        return this;
    }
}
