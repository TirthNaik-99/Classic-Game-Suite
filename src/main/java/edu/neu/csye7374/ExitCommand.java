package edu.neu.csye7374;

public class ExitCommand implements Command {
    private final GameEngine gameEngine;
    private final boolean exitApplication;

    public ExitCommand(GameEngine gameEngine) {
        this(gameEngine, false);
    }

    public ExitCommand(GameEngine gameEngine, boolean exitApplication) {
        this.gameEngine = gameEngine;
        this.exitApplication = exitApplication;
    }

    @Override
    public boolean execute() {
        gameEngine.exitGame();
        return !exitApplication; // Continue running unless we want to exit the application
    }
}
