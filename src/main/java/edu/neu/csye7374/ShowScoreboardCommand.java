package edu.neu.csye7374;

public class ShowScoreboardCommand implements Command {
    private final GameEngine gameEngine;

    public ShowScoreboardCommand(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public boolean execute() {
        gameEngine.showScoreboard();
        return true;
    }
}