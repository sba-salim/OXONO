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
        this.strategy = new RandomStrategy(game, game.getCurrentPlayer(), board);
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
    //Test result if you run this test notice that it is not always the same assertion that gets a wring result
    //Sadly enough I didn't have time to debug. But I think the problem comes from two things, th moves and inserts
    //might not be perfect in the board and the PositionsValidator doesn't always get the same result, I would've added
    // more tests to confirm that the deadline is int 15 minutes
}
