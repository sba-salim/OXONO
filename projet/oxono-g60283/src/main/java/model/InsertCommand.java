package model;

import model.command.Command;

public class InsertCommand implements Command {
    private final Game game;
    private final Board board;
    private final Player player;
    private final Position position;
    private final Pawn pawn;

    public InsertCommand(Game game, Board board, Player currentPlayer, Position position, Pawn p) {
        this.game = game;
        this.board = board;
        this.player = currentPlayer;
        this.position = position;
        this.pawn = p;
    }

    @Override
    public void execute() {
        player.removePawn(pawn.getS());
        board.insertPawn(position, pawn);
        game.switchPlayer();
    }

    @Override
    public void unexecute() {
        Pawn p = (Pawn) board.getTokenAt(position);
        board.removePawn(position);
        game.switchPlayer();
        player.addPawn(p);
    }
}
