package edu.neu.csye7374;

public class PausedState implements GameState {
    private final GameEngine context;

    public PausedState(GameEngine context) {
        this.context = context;
    }

    @Override
    public void handleInput(String input) {
        if (input.equalsIgnoreCase("resume")) {
            context.resumeGame();
        } else if (input.equalsIgnoreCase("exit")) {
            context.exitGame();
        }
    }
}
