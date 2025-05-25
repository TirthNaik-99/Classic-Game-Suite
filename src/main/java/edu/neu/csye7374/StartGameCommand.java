package edu.neu.csye7374;

public class StartGameCommand implements Command {
    private final GameEngine gameEngine;
    private final String gameType;
    private final GameBuilder.Difficulty difficulty;
    private final boolean withHints;
    private final boolean multiplayer;

    public StartGameCommand(GameEngine gameEngine, String gameType, GameBuilder.Difficulty difficulty, boolean withHints) {
        this(gameEngine, gameType, difficulty, withHints, false);
    }

    public StartGameCommand(GameEngine gameEngine, String gameType, GameBuilder.Difficulty difficulty,
                            boolean withHints, boolean multiplayer) {
        this.gameEngine = gameEngine;
        this.gameType = gameType;
        this.difficulty = difficulty;
        this.withHints = withHints;
        this.multiplayer = multiplayer;
    }

    @Override
    public boolean execute() {
        gameEngine.startGame(gameType, difficulty, withHints, multiplayer);
        return true;
    }
}