package g60283.dev.ascii;

import g60283.dev.ascii.model.AsciiPaint;
import g60283.dev.ascii.view.View;

public class App {
    public static void main(String[] args) {
        AsciiPaint paint = new AsciiPaint(40, 20);  // Grille de 40x20
        View vue = new View();
        paint.addCircle(10, 5, 3, '*');  // Ajouter un cercle avec un centre (10, 5), rayon 3, couleur '*'
        paint.addRectangle(2, 2, 6, 4, '#');  // Ajouter un rectangle (2, 2), largeur 6, hauteur 4, couleur '#'
        paint.addSquare(20, 10, 5, '+');  // Ajouter un carré avec côté 5 et couleur '+'
        System.out.println("Dessin initial:");
        vue.display(paint);
        paint.moveShape(0, 5, 3);
        paint.moveShape(1, -1, 2);
        System.out.println("\nDessin après déplacement:");
        vue.display(paint);
    }
}
