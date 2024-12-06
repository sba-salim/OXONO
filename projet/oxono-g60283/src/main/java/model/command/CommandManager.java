package model.command;


import java.util.Stack;

/**
 * CommandManager manages the history of commands for undo and redo operations
 * without directly executing them, reducing coupling.
 */
public class CommandManager {

    private final Stack<Command> executedCommands; // History of executed commands
    private final Stack<Command> undoneCommands;   // History of undone commands

    public CommandManager() {
        this.executedCommands = new Stack<>();
        this.undoneCommands = new Stack<>();
    }

    /**
     * Adds a command to the history and clears the redo stack.
     *
     * @param command The command to add.
     */
    public void addCommand(Command command) {
        executedCommands.push(command);
        undoneCommands.clear(); // Clear redo stack
    }

    /**
     * Undoes the last executed command, if any.
     */
    public void undo() {
        if (!executedCommands.isEmpty()) {
            Command command = executedCommands.pop();
            command.unexecute();
            undoneCommands.push(command);
        } else {
            System.out.println("No commands to undo.");
        }
    }

    /**
     * Redoes the last undone command, if any.
     */
    public void redo() {
        if (!undoneCommands.isEmpty()) {
            Command command = undoneCommands.pop();
            command.execute();
            executedCommands.push(command);
        } else {
            System.out.println("No commands to redo.");
        }
    }

    /**
     * Clears the command history.
     */
    public void clearHistory() {
        executedCommands.clear();
        undoneCommands.clear();
    }

    /**
     * Checks if undo operation is possible.
     *
     * @return True if there are commands to undo, otherwise false.
     */
    public boolean canUndo() {
        return !executedCommands.isEmpty();
    }

    /**
     * Checks if redo operation is possible.
     *
     * @return True if there are commands to redo, otherwise false.
     */
    public boolean canRedo() {
        return !undoneCommands.isEmpty();
    }
}
