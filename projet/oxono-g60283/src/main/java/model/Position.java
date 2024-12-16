package model;

/**
 * Represents a position on a 2D grid with x and y coordinates.
 * This class is immutable and provides access to the x and y values.
 * <p>
 * The `Position` class is used to store the coordinates of a point on the game board.
 * It is particularly useful for tracking locations of game elements such as pawns or totems.
 *
 * @param x the x-coordinate of the position.
 * @param y the y-coordinate of the position.
 */
public record Position(int x, int y) {

}
