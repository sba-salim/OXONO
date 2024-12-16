package model;

import model.command.Command;

/**
 * The MoveCommand class represents a command that moves a totem to a specified position on the board.
 * It encapsulates the action of moving the totem, allowing it to be executed and undone.
 */
public class MoveCommand implements Command {
    private final Board board;
    private final Symbol symbol;
    private final Position targetPosition;
    private final Position oldPosition;

    /**
     * Constructs a MoveCommand to move a totem from a source position to a target position.
     *
     * @param board The board where the totem will be moved.
     * @param s The symbol representing the totem to be moved (either X or O).
     * @param destination The target position where the totem will be moved.
     * @param source The current position of the totem before moving.
     */
    public MoveCommand(Board board, Symbol s, Position destination, Position source) {
        this.board = board;
        this.symbol = s;
        this.targetPosition = destination;
        this.oldPosition = source;
    }

    /**
     * Executes the command, moving the totem to the target position.
     */
    @Override
    public void execute() {
        // Move the totem to the target position
        board.moveTotem(targetPosition, symbol);
    }

    /**
     * Unexecutes the command, moving the totem back to its original position.
     */
    @Override
    public void unexecute() {
        // Move the totem back to the old position
        board.moveTotem(oldPosition, symbol);
    }
}
