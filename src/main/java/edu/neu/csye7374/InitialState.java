package edu.neu.csye7374;

public class InitialState implements GameState {
    private final GameEngine context;

    public InitialState(GameEngine context) {
        this.context = context;
    }

    @Override
    public void handleInput(String input) {
        // Handle commands in initial state
    }
}

