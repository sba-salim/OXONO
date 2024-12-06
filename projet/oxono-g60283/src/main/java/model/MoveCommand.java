package model;

import model.command.Command;

public class MoveCommand implements Command {
    private final Board board;
    private final Symbol symbol;
    private final Position targetPosition;
    private final Position oldPosition;
    public MoveCommand(Board board, Symbol s, Position destination, Position source) {
        this.board=board;
        this.symbol=s;
        this.targetPosition=destination;
        this.oldPosition= source;
    }
    @Override
    public void execute() {
        board.moveTotem(targetPosition, symbol);
    }

    @Override
    public void unexecute() {
        board.moveTotem(oldPosition, symbol);
    }
}
