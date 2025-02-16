package me.sensen.commandfunction;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author Sensen
 */
public class CommandFunction {

    private static ExecutorService es = Executors.newCachedThreadPool();
    private static HashMap<String, CommandExecutor> commandsExecutor = new HashMap<>();;
    private static boolean isStarted = false;

    public static void registerCommand(String commandName, CommandExecutor commandExecutor) {
        commandsExecutor.put(commandName.toLowerCase(), commandExecutor);
    }

    public static void internalRunCommand(String command, List<?> value) {
        processCommand(command.split(" "), value);
    }
    
    
    public static void startCommandFunction() {
        if (isStarted) {
            System.out.println("Command function is already running");
            return;
        }
        isStarted = true;
        Scanner sc = new Scanner(System.in);
        es.submit(() -> {
            while (isStarted) {
                if(sc.hasNext()) {
                    processCommand(sc.nextLine().split(" "), null);
                }
            }
        });
    }

    public static void stopCommandFunction() {
        if (!isStarted) {
            System.out.println("Command function is not running");
            return;
        }
        isStarted = false;
        es.shutdownNow();
    }

    private static void processCommand(String[] args, List<?> values) {
        String command = args[0].toLowerCase();
        if (commandsExecutor.containsKey(command)) {
            runCommand(args, command, values);
            return;
        }
        if (values != null) {
            System.out.println("Internal run a unknown command: " + command);
            return;
        }
        System.out.println("Unknown command: " + command);
    }

    private static void runCommand(String[] args, String command, List<?> values) {
        CommandExecutor commandExecutor = commandsExecutor.get(command);
        if (values != null) {
            commandExecutor.onCommand(new Command(command, removeFirstElement(args), true, values), removeFirstElement(args));
            return;
        }
        commandExecutor.onCommand(new Command(command, removeFirstElement(args), false, null), removeFirstElement(args));
    }

    private static String[] removeFirstElement(String[] array) {
        if (array == null || array.length <= 1) {
            return new String[0];
        }
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);
        return newArray;
    }
}
