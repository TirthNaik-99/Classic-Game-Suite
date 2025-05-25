package edu.neu.csye7374;

public abstract class AbstractGame implements Game {
    protected final GameStrategy strategy;

    public AbstractGame(GameStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void initialize() {
        strategy.initializeGame();
    }

    @Override
    public boolean makeMove(String move) {
        return strategy.processMove(move);
    }

    @Override
    public boolean isGameOver() {
        return strategy.checkGameOver();
    }

    @Override
    public boolean isWinner() {
        return strategy.determineWinner();
    }

    @Override
    public String getGameDisplay() {
        return strategy.renderGameState();
    }

    @Override
    public Memento createMemento() {
        return strategy.saveState();
    }

    @Override
    public void restoreFromMemento(Memento memento) {
        strategy.restoreState(memento);
    }

    // Added getter for the strategy
    public GameStrategy getStrategy() {
        return strategy;
    }
}