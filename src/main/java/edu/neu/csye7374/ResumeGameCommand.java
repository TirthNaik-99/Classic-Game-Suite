package edu.neu.csye7374;

public class ResumeGameCommand implements Command {
    private final GameEngine gameEngine;

    public ResumeGameCommand(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public boolean execute() {
        gameEngine.resumeGame();
        return true;
    }
}