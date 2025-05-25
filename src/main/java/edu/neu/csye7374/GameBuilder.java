package edu.neu.csye7374;

public class GameBuilder {
    public enum Difficulty { EASY, NORMAL, HARD }

    private String gameType;
    private Difficulty difficulty = Difficulty.NORMAL;
    private boolean withHints = false;

    public GameBuilder setGameType(String gameType) {
        this.gameType = gameType;
        return this;
    }

    public GameBuilder setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public GameBuilder setWithHints(boolean withHints) {
        this.withHints = withHints;
        return this;
    }

    public GameSession build() {
        return new GameSession(gameType, difficulty, withHints);
    }
}
