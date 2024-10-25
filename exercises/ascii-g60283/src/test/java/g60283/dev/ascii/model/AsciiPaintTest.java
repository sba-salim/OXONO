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
}