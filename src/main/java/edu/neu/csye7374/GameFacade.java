package edu.neu.csye7374;

import java.util.Scanner;

public class GameFacade {
    private final GameEngine gameEngine;
    private final ConsoleUI consoleUI;
    private final CommandProcessor commandProcessor;

    public GameFacade() {
        this.gameEngine = GameEngine.getInstance();
        this.consoleUI = new ConsoleUI();
        this.commandProcessor = new CommandProcessor(gameEngine);

        // Register observers
        gameEngine.registerObserver(consoleUI);
    }

    public void start() {
        consoleUI.displayWelcomeMessage();
        consoleUI.displayMainMenu();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            String input = scanner.nextLine().trim();
            Command command = commandProcessor.parseCommand(input);
            running = command.execute();
        }

        scanner.close();
    }
}
