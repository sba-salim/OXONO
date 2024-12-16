package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(); // Initialisation d'une nouvelle partie avec les paramètres par défaut.
    }

    // Test de déplacement valide de totem
    @Test
    void testMoveTotemValid() {
        Position target = new Position(3, 4); // Position valide pour déplacer le totem
        Symbol totemSymbol = game.getTokenAt(new Position(3,3)).getS(); // On suppose que le totem du joueur rose est déjà en jeu

        // Vérifier que le mouvement du totem est valide
        assertTrue(game.moveTotem(target, totemSymbol));

        // Vérifier que le joueur ne doit plus déplacer un totem (il peut maintenant insérer un pion)
        assertFalse(game.hasToMove());
    }

    // Test de déplacement de totem vers une position invalide (case déjà occupée)
    @Test
    void testMoveTotemInvalidOccupied() {
        Position target = new Position(0, 0); // Position occupée par un autre élément
        Symbol totemSymbol = Symbol.X; // On suppose que le totem du joueur rose est déjà en jeu

        // Le mouvement ne devrait pas réussir car la case est occupée
        assertFalse(game.moveTotem(target, totemSymbol));

        // Vérifier que le joueur doit encore déplacer un totem (mouvement échoué)
        assertTrue(game.hasToMove());
    }

    // Test de déplacement de totem lorsqu'il n'est pas au tour du joueur
    @Test
    void testMoveTotemNotPlayerTurn() {
        // Le joueur rose commence par défaut, nous allons simuler un tour du joueur noir
        game.switchPlayer(); // Passage au joueur noir

        Position target = new Position(3, 4); // Position valide
        Symbol totemSymbol = game.getTokenAt(new Position(3, 3)).getS(); // Totem du joueur noir

        // Le mouvement devrait réussir car c'est le tour du joueur noir
        assertTrue(game.moveTotem(target, totemSymbol));

        // Vérifier que le joueur ne doit plus déplacer un totem
        assertFalse(game.hasToMove());
    }


    // Test de déplacement de totem en dehors des limites du plateau
    @Test
    void testMoveTotemOutOfBounds() {
        Position target = new Position(10, 10); // Position en dehors des limites (le plateau est plus petit)
        Symbol totemSymbol = game.getTokenAt(new Position(3,3)).getS();

        // Le mouvement devrait échouer car la position est hors du plateau
        assertFalse(game.moveTotem(target, totemSymbol));

        // Vérifier que le joueur doit encore déplacer un totem
        assertTrue(game.hasToMove());
    }

    // Test du fait qu'un joueur ne puisse pas déplacer son totem avant de le déplacer une fois
    @Test
    void testTotemMustMoveOnce() {
        Position target = new Position(3, 2);
        Symbol totemSymbol = game.getTokenAt(new Position(3, 3)).getS();

        // Le joueur doit d'abord déplacer son totem
        game.moveTotem(target, totemSymbol);

        // Vérifier que le joueur doit maintenant insérer un pion et ne plus pouvoir déplacer un totem
        assertFalse(game.hasToMove());
    }
    // Test d'insertion valide d'un pion
    @Test
    void testInsertPawnValid() {
        Position target = new Position(4, 4); // Position valide pour insérer un pion

        // On déplace d'abord le totem du joueur pour permettre l'insertion du pion
        Position totemTarget = new Position(3, 4);
        Symbol totemSymbol = game.getTokenAt(new Position(3, 3)).getS();
        game.moveTotem(totemTarget, totemSymbol);

        // L'insertion du pion doit réussir
        assertTrue(game.insertPawn(target));

        // Vérifier que le joueur ne doit plus déplacer un totem et peut insérer un pion
        assertTrue(game.hasToMove());
    }

    // Test d'insertion d'un pion dans une position invalide (case occupée)
    @Test
    void testInsertPawnInvalidOccupied() {
        Color current = game.getCurrentPlayersColor();
        Position target = new Position(3, 3); // Position occupée par un autre élément

        // Déplacer le totem du joueur pour permettre l'insertion
        Position totemTarget = new Position(2, 4);
        Symbol totemSymbol = game.getTokenAt(new Position(2, 2)).getS();
        game.moveTotem(totemTarget, totemSymbol);

        // Tentative d'insertion dans une case occupée devrait échouer
        assertFalse(game.insertPawn(target));
        assertSame(game.getCurrentPlayersColor(), current);
        // Vérifier que le joueur ne doit pas déplacer un totem
        assertFalse(game.hasToMove());
    }

    // Test d'insertion d'un pion sans avoir déplacé un totem
    @Test
    void testInsertPawnWithoutTotemMove() {
        Position target = new Position(4, 4); // Position valide pour insérer un pion

        // Tentative d'insertion sans avoir déplacé le totem
        assertFalse(game.insertPawn(target));

        // Vérifier que le joueur doit encore déplacer un totem
        assertTrue(game.hasToMove());
    }

}
