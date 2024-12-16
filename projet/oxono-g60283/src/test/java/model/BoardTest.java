package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(6); // Crée un tableau de taille 6x6
    }

    @Test
    void testBoardInitialization() {
        // Test de l'initialisation du tableau et placement des totems
        assertEquals(6, board.getSize());

        // Tester que les totems X et O sont bien placés dans les cases centrales
        if (board.getTokenAt(new Position(2, 2)) instanceof Totem) {
            assertEquals(Symbol.X, board.getTokenAt(new Position(2, 2)).getS());
            assertEquals(Symbol.O, board.getTokenAt(new Position(3, 3)).getS());
        } else if (board.getTokenAt(new Position(3, 3)) instanceof Totem) {
            assertEquals(Symbol.X, board.getTokenAt(new Position(3, 3)).getS());
            assertEquals(Symbol.O, board.getTokenAt(new Position(2, 2)).getS());
        }
    }

    @Test
    void testValidMove() {
        // Tester le déplacement du totem X de (2, 2) à (2, 3)
        Position posX = new Position(2, 2);
        Position newPos = new Position(2, 3);
        Symbol symbol = Symbol.X;

        // Déplacer le totem X
        boolean result = board.moveTotem(newPos, symbol);
        assertTrue(result, "Le totem devrait pouvoir se déplacer.");
        assertEquals(symbol, board.getTokenAt(newPos).getS());
    }


    @Test
    void testInsertPawnInvalidPosition() {
        // Déplacer un totem avant d'insérer un pion
        Position posX = new Position(2, 2);
        Position newPos = new Position(2, 3);
        board.moveTotem(newPos, Symbol.X); // Déplacer le totem X

        // Essayer d'insérer un pion dans une position invalide (case occupée par un totem)
        Position pos = new Position(2, 2); // Case déjà occupée par un totem
        Pawn pawn = new Pawn(Symbol.X, Color.PINK);

        boolean result = board.insertPawn(pos, pawn);
        assertFalse(result, "Le pion ne doit pas être inséré dans une case occupée.");
    }


    @Test
    void testPathClear() {
        // Déplacer un totem avant d'insérer un pion
        Position posX = new Position(2, 2);  // Position initiale du totem X
        Position newPos = new Position(2, 3); // Nouvelle position pour le totem X
        board.moveTotem(newPos, Symbol.X);  // Déplacer le totem X

        Position target = new Position(3, 2); // Position à vérifier
        boolean result = board.isPathClear(posX, Symbol.X);
        assertTrue(result, "Le chemin vers le totem X doit être dégagé.");
    }

    @Test
    void testInvalidMoveTotemEnclaved() {
        // Tester si un totem ne peut pas se déplacer lorsqu'il est enclavé
        Position pos = new Position(3, 3); // Position du totem enclavé
        Position target = new Position(4, 3); // Position cible invalide

        // Assurer qu'il y a des cases bloquées autour du totem
        board.moveTotem(target, Symbol.X);

        boolean result = board.moveTotem(target, Symbol.X);
        assertFalse(result, "Le totem ne doit pas pouvoir se déplacer si toutes les cases voisines sont occupées.");
    }

    @Test
    void testInsertPawnNotNearTotem() {
        // Déplacer un totem avant d'insérer un pion
        Position posX = new Position(2, 2);  // Position initiale du totem X
        Position newPos = new Position(2, 3); // Nouvelle position pour le totem X
        board.moveTotem(newPos, Symbol.X);  // Déplacer le totem X

        // Essayer d'insérer un pion trop éloigné du totem
        Position posPawn = new Position(5, 5); // Trop loin d'un totem
        Pawn pawn = new Pawn(Symbol.X, Color.PINK);

        boolean result = board.insertPawn(posPawn, pawn);
        assertFalse(result, "Le pion ne doit pas être inséré s'il n'est pas proche d'un totem.");
    }
}
