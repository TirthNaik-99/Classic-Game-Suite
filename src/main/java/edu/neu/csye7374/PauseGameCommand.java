package edu.neu.csye7374;

public class PauseGameCommand implements Command {
    private final GameEngine gameEngine;

    public PauseGameCommand(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public boolean execute() {
        gameEngine.pauseGame();
        return true;
    }
}