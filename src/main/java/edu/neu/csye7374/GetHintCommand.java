package edu.neu.csye7374;

public class GetHintCommand implements Command {
    private final GameEngine gameEngine;

    public GetHintCommand(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public boolean execute() {
        gameEngine.showHint();
        return true;
    }
}
