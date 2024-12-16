package model;

import model.strategy.Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ConsoleView;

import static org.junit.jupiter.api.Assertions.*;

class PositionsValidatorTest {
    private Game game;
    private Board board;
    private Strategy strategy;

    @BeforeEach
    void setUp() {
        board = new Board(6);
        game = new Game(board); // Crée un tableau de taille 6x6
        this.strategy=new RandomStrategy(game,game.getCurrentPlayer(),board);
    }

    @Test
    void testStrategy() {
        for (int i = 0; i < 15; i++) {
            int emptycells = 34;
            strategy.execute();
            emptycells--;
            ConsoleView.displayBoard(game);
            assertEquals(emptycells, game.emptyCells());

            Strategy strategy1 = new RandomStrategy(game, game.getCurrentPlayer(), board);
            strategy1.execute();
            emptycells--;
            assertEquals(emptycells, game.emptyCells());

        }

    }

}