package me.sensen.commandfunction;

import java.util.List;

/**
 * @author Sensen
 */
public class Command {
    public String command;
    public String[] args;
    public boolean isInternalRun;
    public List<?> internalValues;

    public Command(String command, String[] args, boolean isInternalRun, List<?> internalValues) {
        this.command = command;
        this.args = args;
        this.isInternalRun = isInternalRun;
        this.internalValues = internalValues;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isInternalRun() {
        return isInternalRun;
    }

    public List<?> getInternalValues() {
        return internalValues;
    }
}
