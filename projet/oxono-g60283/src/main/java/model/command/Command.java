package model.command;

/**
 * The Command interface represents a command that can be executed and undone.
 * It defines methods for executing and unexecuting a command, providing a way to encapsulate requests.
 */
public interface Command {

    /**
     * Executes the command. This method performs the actual action associated with the command.
     */
    void execute();

    /**
     * Unexecutes the command. This method reverses the effect of the command, undoing its action.
     */
    void unexecute();
}
