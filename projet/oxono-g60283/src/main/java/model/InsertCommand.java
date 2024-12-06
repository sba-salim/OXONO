package model;

import model.command.Command;

public class InsertCommand implements Command {
    private final Board board;
    private final Player player;
    private final Position position;
    private final Pawn pawn;

    public InsertCommand(Board board, Player currentPlayer, Position position, Pawn p) {
        this.board = board;
        this.player = currentPlayer;
        this.position = position;
        this.pawn = p;
    }

    @Override
    public void execute() {
        player.removePawn(pawn.getS());
        board.insertPawn(position, pawn);
    }

    @Override
    public void unexecute() {
        Pawn p = (Pawn) board.getTokenAt(position);
        board.removePawn(position);
        player.addPawn(p);
    }
}
