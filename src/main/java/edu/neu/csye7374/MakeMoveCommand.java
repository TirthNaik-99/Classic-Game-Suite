package edu.neu.csye7374;

public class MakeMoveCommand implements Command {
    private final GameEngine gameEngine;
    private final String move;

    public MakeMoveCommand(GameEngine gameEngine, String move) {
        this.gameEngine = gameEngine;
        this.move = move;
    }

    @Override
    public boolean execute() {
        gameEngine.makeMove(move);
        return true;
    }
}
