package view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {

    public ImageButton() {
        super();  // Appelle le constructeur de Button
        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("image");
        this.setGraphic(imageView);
        this.getStyleClass().add("grid-button");// Définit l'ImageView comme graphique du bouton
    }

}
