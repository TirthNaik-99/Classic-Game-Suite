package edu.neu.csye7374;

public class GameSession {
    private final String gameType;
    private final GameBuilder.Difficulty difficulty;
    private final boolean withHints;

    public GameSession(String gameType, GameBuilder.Difficulty difficulty, boolean withHints) {
        this.gameType = gameType;
        this.difficulty = difficulty;
        this.withHints = withHints;
    }

    // Getters
    public String getGameType() {
        return gameType;
    }

    public GameBuilder.Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean isWithHints() {
        return withHints;
    }
}
