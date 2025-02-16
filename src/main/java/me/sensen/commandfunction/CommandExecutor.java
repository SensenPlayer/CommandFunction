package me.sensen.commandfunction;

/**
 * @author Sensen
 */
public interface CommandExecutor {
    void onCommand(Command command, String[] args);
}
