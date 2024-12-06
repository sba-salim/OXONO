package model;

import model.command.CommandManager;

import java.util.ArrayList;
import java.util.List;

public class Game implements Observable {
    private final Board board;
    private Player currentPlayer;
    private final Player pinkPlayer;
    private final Player blackPlayer;
    private boolean hasToMove; // Indicates whether the current player must move a totem first
    private boolean gameOver;
    private final List<Observer> observers;
    private final CommandManager cmdMgr;

    public Game() {
        this.board = new Board();
        this.pinkPlayer = new Player(Color.PINK);
        this.blackPlayer = new Player(Color.BLACK);
        this.currentPlayer = pinkPlayer; // Pink player starts as per the rules
        this.hasToMove = true; // Player must move a totem at the beginning
        this.gameOver = false;
        this.observers = new ArrayList<>();
        this.cmdMgr = new CommandManager();

    }

    // Toggle the current player
    public void switchPlayer() {
        currentPlayer = (currentPlayer == pinkPlayer) ? blackPlayer : pinkPlayer;
        hasToMove = true; // New player must move a totem
    }

    // Move a totem
    public boolean moveTotem(Position targetPosition, Symbol totemSymbol) {
        Position sourcePosition = board.getTotemPos(totemSymbol);
        if (hasToMove
                && currentPlayer.hasPawn(totemSymbol)
                && board.moveTotem(targetPosition, totemSymbol)) {// Java évalue les méthodes de manière parresseuse,
            // il n'y aura donc pas de move si l'une des deux premières conditions est fausse.
            cmdMgr.addCommand(new MoveCommand(board, totemSymbol, targetPosition, sourcePosition));
            hasToMove = false;// Moving a totem allows the player to insert a pawn
            notifyObservers();
            return true;
        }
        notifyObservers();
        return false;

    }

    // Insert a pawn
    public boolean insertPawn(Position position) {
        if (hasToMove) {
            return false;
        }
        Symbol symbol = board.getLastTouchedSymbol();

        Pawn pawn = currentPlayer.removePawn(symbol); // Get the pawn from the player's hand
        if (!board.insertPawn(position, pawn)) {
            currentPlayer.addPawn(pawn);
            notifyObservers();
            return false;
        }
        if (board.checkWin(position)) {
            gameOver = true;
            cmdMgr.addCommand(new InsertCommand(board, currentPlayer, position, pawn));
            notifyObservers();
            return true;
        }
        cmdMgr.addCommand(new InsertCommand(board, currentPlayer, position, pawn));
        hasToMove = true;
        this.switchPlayer();
        notifyObservers();
        return true;
    }

    public void surrender() {
        this.gameOver = true;
        switchPlayer();
        notifyObservers();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        notifyObservers();
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    public Token getTokenAt(Position p) {
        return board.getTokenAt(p);
    }

    public Color getCurentPlayersColor() {
        return currentPlayer.getC();
    }

    public void undo() {
        if (cmdMgr.canUndo()) {
            cmdMgr.undo();
            notifyObservers();
        }
    }

    public void redo() {
        if (cmdMgr.canRedo()) {
            cmdMgr.redo();
            notifyObservers();
        }
    }

    public boolean draw() {
        if (this.blackPlayer.isHandEmpty() && this.pinkPlayer.isHandEmpty()) {
            gameOver = true;
            return true;
        }
        return false;
    }
}
