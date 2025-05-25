package edu.neu.csye7374;

public class EndedState implements GameState {
    private final GameEngine context;

    public EndedState(GameEngine context) {
        this.context = context;
    }

    @Override
    public void handleInput(String input) {
        if (input.equalsIgnoreCase("restart")) {
            context.startGame(context.getCurrentGameType(), GameBuilder.Difficulty.NORMAL, false, false);
        } else if (input.equalsIgnoreCase("menu") || input.equalsIgnoreCase("main")) {
            // Return to main menu
            context.exitGame();
        } else if (input.equalsIgnoreCase("exit")) {
            context.exitGame();
        }
    }
}