package edu.neu.csye7374;

public class PlayingState implements GameState {
    private final GameEngine context;

    public PlayingState(GameEngine context) {
        this.context = context;
    }

    @Override
    public void handleInput(String input) {
        if (input.equalsIgnoreCase("pause")) {
            context.pauseGame();
        } else if (input.equalsIgnoreCase("exit")) {
            context.exitGame();
        } else {
            context.makeMove(input);
        }
    }
}
