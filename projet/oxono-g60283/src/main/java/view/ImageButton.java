package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {
    private final ImageView imageView;

    public ImageButton() {
        super();  // Appelle le constructeur de Button
        this.imageView = new ImageView();
        this.imageView.getStyleClass().add("image");
        this.setGraphic(imageView);
        this.getStyleClass().add("grid-button");// Définit l'ImageView comme graphique du bouton
    }

    // Méthode pour ajouter une image au bouton
    public void setImage(String imagePath) {
        Image image = new Image(imagePath); // Charge l'image
        imageView.setImage(image);          // Associe l'image à l'ImageView
        imageView.setPreserveRatio(true);   // Préserve le ratio de l'image
    }

    // Méthode pour supprimer l'image
    public void clearImage() {
        imageView.setImage(null);  // Supprime l'image de l'ImageView
    }

    // Méthode pour appliquer une classe CSS
    public void setCSSClass(String className) {
        this.getStyleClass().add(className);  // Ajoute une classe CSS au bouton
    }
}
