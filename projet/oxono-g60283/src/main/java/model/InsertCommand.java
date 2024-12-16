package model;

import model.command.Command;

/**
 * The InsertCommand class represents a command that inserts a pawn into the board at a specified position.
 * It encapsulates the action of inserting a pawn, allowing it to be executed and undone.
 */
public class InsertCommand implements Command {
    private final Game game;
    private final Board board;
    private final Player player;
    private final Position position;
    private final Pawn pawn;

    /**
     * Constructs an InsertCommand to insert a pawn at a specified position.
     *
     * @param game The current game instance, needed to switch players.
     * @param board The board where the pawn will be inserted.
     * @param currentPlayer The player who is inserting the pawn.
     * @param position The position on the board where the pawn will be placed.
     * @param p The pawn to be inserted.
     */
    public InsertCommand(Game game, Board board, Player currentPlayer, Position position, Pawn p) {
        this.game = game;
        this.board = board;
        this.player = currentPlayer;
        this.position = position;
        this.pawn = p;
    }

    /**
     * Executes the command, inserting the pawn at the specified position and switching to the next player.
     */
    @Override
    public void execute() {
        // Remove the pawn from the player's hand
        player.removePawn(pawn.getS());

        // Insert the pawn into the board at the given position
        board.insertPawn(position, pawn);

        // Switch to the next player
        game.switchPlayer();
    }

    /**
     * Unexecutes the command, removing the pawn from the board and restoring it to the player's hand.
     * It also switches to the previous player.
     */
    @Override
    public void unexecute() {
        // Retrieve the pawn from the board at the position
        Pawn p = (Pawn) board.getTokenAt(position);

        // Remove the pawn from the board
        board.removePawn(position);

        // Switch to the previous player
        game.switchPlayer();

        // Add the pawn back to the player's hand
        player.addPawn(p);
    }
}
