package model;

import model.command.CommandManager;
import model.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * The `Game` class manages the state of the game, player turns, totem movements, and pawn insertions.
 * It implements the `Observable` interface to allow observers (like the graphical interface) to track game changes.
 */
public class Game implements Observable {
    private final Board board;
    private Player currentPlayer;
    private final Player pinkPlayer;
    private final Player blackPlayer;
    private boolean hasToMove; // Indicates if the current player must move a totem first
    private boolean gameOver;
    private final List<Observer> observers;
    private final CommandManager cmdMgr;
    private Strategy strategy;

    /**
     * Gets the player's number of pawns with the X Symbol
     *
     * @return The player's number of pawns with the X Symbol
     */
    public int getCurrentPlayerXPawns() {
        return currentPlayer.XPawns();
    }

    /**
     * Gets the player's number of pawns with the O Symbol
     *
     * @return The player's number of pawns with the O Symbol
     */
    public int getCurrentPlayerOPawns() {
        return currentPlayer.OPawns();
    }

    /**
     * Strictly for tests !
     * @return the currentPlayer of the game
     */
    Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Constructor for tests
     *
     * @param board
     */
    public Game(Board board) {
        this.board = board;
        this.pinkPlayer = new Player(Color.PINK, board.getSize() * board.getSize() - 4);
        this.blackPlayer = new Player(Color.BLACK, board.getSize() * board.getSize() - 4);
        this.currentPlayer = pinkPlayer; // Pink player starts as per the rules
        this.hasToMove = true; // The player must move a totem first
        this.gameOver = false;
        this.observers = new ArrayList<>();
        this.cmdMgr = new CommandManager();
    }

    /**
     * Default constructor to initialize the game.
     * It initializes a default-sized board and calculates the number of pawns for each player.
     */
    public Game() {
        this.board = new Board();
        this.pinkPlayer = new Player(Color.PINK, board.getSize() * board.getSize() - 4);
        this.blackPlayer = new Player(Color.BLACK, board.getSize() * board.getSize() - 4);
        this.currentPlayer = pinkPlayer; // Pink player starts as per the rules
        this.hasToMove = true; // The player must move a totem first
        this.gameOver = false;
        this.observers = new ArrayList<>();
        this.cmdMgr = new CommandManager();
    }

    /**
     * Constructor that allows customization of board size and activates a game strategy.
     *
     * @param size     The size of the board.
     * @param strategy If true, activates a strategy (RandomStrategy for the black player).
     */
    public Game(int size, boolean strategy) {
        this.board = new Board(size);
        this.pinkPlayer = new Player(Color.PINK, board.getSize() * board.getSize() - 4);
        this.blackPlayer = new Player(Color.BLACK, board.getSize() * board.getSize() - 4);
        this.currentPlayer = pinkPlayer;
        this.hasToMove = true;
        this.gameOver = false;
        this.observers = new ArrayList<>();
        this.cmdMgr = new CommandManager();
        if (strategy)
            this.strategy = new RandomStrategy(this, blackPlayer, board);
    }

    /**
     * Switches to the next player and notifies observers.
     * If a strategy is defined for the black player, it will be executed.
     */
    void switchPlayer() {
        currentPlayer = (currentPlayer == pinkPlayer) ? blackPlayer : pinkPlayer;
        notifyObservers();
        if (strategy != null && currentPlayer == blackPlayer) {
            strategy.execute(); // If defined, the strategy for the black player is executed
        }
    }

    /**
     * Moves the totem of the current player to a target position.
     *
     * @param targetPosition The target position for the move.
     * @param totemSymbol    The symbol of the totem to be moved.
     * @return true if the move is successful, false otherwise.
     */
    public boolean moveTotem(Position targetPosition, Symbol totemSymbol) {
        Position sourcePosition = board.getTotemPos(totemSymbol);
        if (hasToMove
                && currentPlayer.hasPawn(totemSymbol)
                && board.moveTotem(targetPosition, totemSymbol)) {
            cmdMgr.addCommand(new MoveCommand(board, totemSymbol, targetPosition, sourcePosition));
            hasToMove = false; // After moving the totem, the player can insert a pawn
            notifyObservers();
            return true;
        }
        notifyObservers();
        return false;
    }

    /**
     * Inserts a pawn from the current player into the board.
     *
     * @param position The position where the pawn should be inserted.
     * @return true if the insertion is successful, false otherwise.
     */
    public boolean insertPawn(Position position) {
        if (hasToMove) {
            return false; // The player must first move a totem
        }
        Symbol symbol = board.getLastTouchedSymbol();
        Pawn pawn = currentPlayer.removePawn(symbol); // Remove the pawn from the player's hand
        if (!board.insertPawn(position, pawn)) {
            currentPlayer.addPawn(pawn);
            notifyObservers();
            return false; // The insertion failed
        }
        if (board.checkWin(position)) {
            gameOver = true;
            cmdMgr.addCommand(new InsertCommand(this, board, currentPlayer, position, pawn));
            notifyObservers();
            return true; // The game is won
        }
        cmdMgr.addCommand(new InsertCommand(this, board, currentPlayer, position, pawn));
        hasToMove = true; // The player must now move a totem
        notifyObservers();
        this.switchPlayer(); // Switch player
        notifyObservers();
        return true;
    }

    /**
     * The current player surrenders, and the game ends.
     */
    public void surrender() {
        this.gameOver = true;
        switchPlayer(); // Switch to the next player
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
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
        observers.remove(observer); // Fixed logic to remove observer
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    /**
     * Gets the token at a specific position on the board.
     *
     * @param p The position on the board.
     * @return The token (or null if the cell is empty).
     */
    public Token getTokenAt(Position p) {
        return board.getTokenAt(p);
    }

    /**
     * Returns the color of the current player.
     *
     * @return The color of the current player.
     */
    public Color getCurrentPlayersColor() {
        return currentPlayer.getC();
    }

    /**
     * Undoes the last command (either a move or insertion).
     * Notifies observers after undoing.
     */
    public void undo() {
        if (cmdMgr.canUndo()) {
            cmdMgr.undo();
            hasToMove = !hasToMove;
            notifyObservers();
        }
    }

    /**
     * Redoes the last undone command.
     * Notifies observers after redoing.
     */
    public void redo() {
        if (cmdMgr.canRedo()) {
            cmdMgr.redo();
            hasToMove = !hasToMove;
            notifyObservers();
        }
    }

    /**
     * Checks if the game is a draw.
     *
     * @return true if the game is a draw, false otherwise.
     */
    public boolean draw() {
        if (this.blackPlayer.isHandEmpty() && this.pinkPlayer.isHandEmpty()) {
            gameOver = true;
            return true; // If both players have no pawns left, it’s a draw
        }
        return false;
    }

    /**
     * Checks if the current player has to move a totem.
     *
     * @return true if the current player must move a totem, false otherwise.
     */
    public boolean hasToMove() {
        return hasToMove;
    }

    /**
     * Gets the size of the board.
     *
     * @return The size of the board.
     */
    public int getSize() {
        return board.getSize();
    }

    /**
     * Calculates the number of empty cells on the board.
     *
     * @return The number of empty cells.
     */
    public int emptyCells() {
        int n = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Position p = new Position(i, j);
                if (board.isEmpty(p))
                    n++;
            }
        }
        return n;
    }
}
