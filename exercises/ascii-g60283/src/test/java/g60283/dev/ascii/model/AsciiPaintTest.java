package g60283.dev.ascii.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AsciiPaintTest {

    private AsciiPaint paint;

    @BeforeEach
    public void setUp() {
        paint = new AsciiPaint(40, 20); // Grille de 40x20
    }

    @Test
    public void testAddCircle() {
        paint.addCircle(10, 5, 3, '*');
        char colorAtCenter = paint.getColorAt(10, 5);
        assertEquals('*', colorAtCenter);
    }

    @Test
    public void testAddRectangle() {
        paint.addRectangle(2, 2, 6, 4, '#');
        char colorAtUpperLeft = paint.getColorAt(2, 2);
        assertEquals('#', colorAtUpperLeft);
    }

    @Test
    public void testAddSquare() {
        paint.addSquare(20, 10, 5, '+');
        char colorAtUpperLeft = paint.getColorAt(20, 10);
        assertEquals('+', colorAtUpperLeft);
    }

    @Test
    public void testGetColorAt() {
        paint.addCircle(15, 10, 3, 'o');
        char colorAtCenter = paint.getColorAt(15, 10);
        assertEquals('o', colorAtCenter);

        char emptySpace = paint.getColorAt(0, 0);  // Should return ' ' if no shape is there
        assertEquals(' ', emptySpace);
    }

    @Test
    public void testGetWidth() {
        assertEquals(40, paint.getWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(20, paint.getHeight());
    }

    @Test
    public void testSetColor() {
        paint.addRectangle(5, 5, 4, 2, '@');
        paint.setColor(0, '&');
        char newColorAtUpperLeft = paint.getColorAt(5, 5);
        assertEquals('&', newColorAtUpperLeft);
    }

    @Test
    public void testMoveShape() {
        paint.addRectangle(10, 10, 4, 2, '#');
        paint.moveShape(0, 5, 5);
        char newColorAtNewPosition = paint.getColorAt(15, 15);
        assertEquals('#', newColorAtNewPosition);
    }

    @Test
    public void testRemoveShape() {
        paint.addCircle(10, 5, 3, '*');
        paint.removeShape(0);
        char colorAtCenter = paint.getColorAt(10, 5);
        assertEquals(' ', colorAtCenter);
    }
    @Test
    public void testAddMultipleShapesUsingLoop() {
        // Ajout de 5 cercles avec un caractère différent pour chaque cercle.
        char[] symbols = {'*', '#', '@', '%', 'o'};
        int baseX = 5;
        int baseY = 5;

        for (int i = 0; i < symbols.length; i++) {
            paint.addCircle(baseX + i * 5, baseY + i * 2, 2, symbols[i]);
        }

        // Vérification que chaque cercle a bien été ajouté avec le bon caractère
        for (int i = 0; i < symbols.length; i++) {
            char colorAtCenter = paint.getColorAt(baseX + i * 5, baseY + i * 2);
            assertEquals(symbols[i], colorAtCenter, "Le symbole au centre du cercle " + i + " ne correspond pas");
        }
    }
    @Test
    public void testAddMultipleRectanglesUsingLoop() {
        char[] symbols = {'+', '-', '=', '~', '^'};
        int baseX = 1;
        int baseY = 1;

        for (int i = 0; i < symbols.length; i++) {
            paint.addRectangle(baseX + i * 6, baseY + i * 3, 3, 2, symbols[i]);
        }

        // Vérification que chaque rectangle a bien été ajouté avec le bon caractère
        for (int i = 0; i < symbols.length; i++) {
            char colorAtUpperLeft = paint.getColorAt(baseX + i * 6, baseY + i * 3);
            assertEquals(symbols[i], colorAtUpperLeft, "Le symbole en haut à gauche du rectangle " + i + " ne correspond pas");
        }
    }
    @Test
    public void testFillEntireDrawingWithShapes() {
        int width = paint.getWidth();
        int height = paint.getHeight();
        char fillChar = '*';

        // Remplir chaque emplacement de la grille avec un carré 1x1 avec le caractère '*'
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                paint.addSquare(x, y, 1, fillChar);
            }
        }

        // Vérifier que chaque position contient bien le caractère '*'
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                char colorAtPosition = paint.getColorAt(x, y);
                assertEquals(fillChar, colorAtPosition, "La position (" + x + ", " + y + ") ne contient pas le bon caractère.");
            }
        }
    }
    @Test
    public void testMoveOneShapeWhenTwoShapesOverlap() {
        // Ajouter un rectangle et un cercle à la même position
        paint.addRectangle(10, 10, 3, 2, '#');  // Forme 1 : rectangle
        paint.addCircle(10, 10, 1, '*');        // Forme 2 : cercle, superposé au rectangle

        // Vérifier que les deux formes sont à la même position

        // Déplacer seulement le rectangle
        paint.moveShape(0, 5, 5); // Déplacer la forme 0 (le rectangle) de +5 en x et +5 en y

        // Vérifier que le rectangle a été déplacé
        assertEquals('#', paint.getColorAt(15, 15), "Le rectangle devrait être déplacé en position (15, 15).");

        // Vérifier que le cercle est toujours à sa position initiale
        assertEquals('*', paint.getColorAt(10, 10), "Le cercle devrait être resté en position (10, 10).");

    }

}