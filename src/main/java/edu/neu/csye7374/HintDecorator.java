package edu.neu.csye7374;

public class HintDecorator extends AbstractGame {
    private final Game decoratedGame;

    public HintDecorator(Game decoratedGame) {
        super(null); // We don't use the strategy directly in this decorator
        this.decoratedGame = decoratedGame;
    }

    @Override
    public void initialize() {
        decoratedGame.initialize();
    }

    @Override
    public boolean makeMove(String move) {
        return decoratedGame.makeMove(move);
    }

    @Override
    public boolean isGameOver() {
        return decoratedGame.isGameOver();
    }

    @Override
    public boolean isWinner() {
        return decoratedGame.isWinner();
    }

    @Override
    public String getGameDisplay() {
        return decoratedGame.getGameDisplay();
    }

    @Override
    public String getGameType() {
        return decoratedGame.getGameType();
    }

    @Override
    public Memento createMemento() {
        return decoratedGame.createMemento();
    }

    @Override
    public void restoreFromMemento(Memento memento) {
        decoratedGame.restoreFromMemento(memento);
    }

    public String getHint() {
        // Get the strategy from the decorated game if it's an AbstractGame
        if (decoratedGame instanceof AbstractGame) {
            AbstractGame game = (AbstractGame) decoratedGame;
            return game.getStrategy().getHint();
        }
        return "No hint available";
    }
}