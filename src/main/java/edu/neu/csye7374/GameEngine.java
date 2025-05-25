package edu.neu.csye7374;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements GameEventPublisher {
    private static GameEngine instance;
    private final List<GameObserver> observers = new ArrayList<>();
    private final GameFactory gameFactory;
    private Game currentGame;
    private GameState currentState;
    private final ScoreBoard scoreBoard;
    private final CustomLogger logger;
    private Memento savedGameState;

    private GameEngine() {
        this.gameFactory = new GameFactory();
        this.currentState = new InitialState(this);
        this.scoreBoard = new ScoreBoard();
        this.logger = CustomLogger.getInstance();
        registerObserver(scoreBoard);
        registerObserver(logger);
    }

    public static synchronized GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    public void startGame(String gameType, GameBuilder.Difficulty difficulty, boolean withHints, boolean multiplayer) {
        GameBuilder builder = new GameBuilder();
        builder.setGameType(gameType)
                .setDifficulty(difficulty)
                .setWithHints(withHints);

        Game game = gameFactory.createGame(gameType);

        // Apply difficulty to game strategy
        if (game instanceof AbstractGame) {
            AbstractGame abstractGame = (AbstractGame) game;
            GameStrategy strategy = abstractGame.getStrategy();
            strategy.setDifficulty(difficulty.toString());

            // Set multiplayer mode for TicTacToe if requested
            if (gameType.equalsIgnoreCase("tictactoe") && strategy instanceof TicTacToeStrategy) {
                ((TicTacToeStrategy) strategy).setMultiplayerMode(multiplayer);
            }
        }

        // Apply hint decorator if requested
        if (withHints) {
            game = new HintDecorator(game);
        }

        this.currentGame = game;
        this.currentState = new PlayingState(this);
        notifyObservers(new GameEvent(GameEventType.GAME_STARTED, gameType));

        if (game != null) {
            game.initialize();
            publishGameBoard();
        }
    }

    public void makeMove(String move) {
        if (currentGame != null && currentState instanceof PlayingState) {
            boolean validMove = currentGame.makeMove(move);
            if (validMove) {
                publishGameBoard();
                checkGameStatus();
            } else {
                notifyObservers(new GameEvent(GameEventType.INVALID_MOVE, "Invalid move: " + move));
            }
        }
    }

    private void checkGameStatus() {
        if (currentGame.isGameOver()) {
            if (currentGame.isWinner()) {
                notifyObservers(new GameEvent(GameEventType.GAME_WON, "Congratulations, you won!"));
                scoreBoard.addScore(currentGame.getGameType(), 1);
                notifyObservers(new GameEvent(GameEventType.SCORE_UPDATED, scoreBoard.getScoreString()));
            } else {
                notifyObservers(new GameEvent(GameEventType.GAME_LOST, "Game over!"));
                // Show scoreboard even when player loses
                notifyObservers(new GameEvent(GameEventType.SCORE_UPDATED, scoreBoard.getScoreString()));
            }
            this.currentState = new EndedState(this);
        }
    }

    public void pauseGame() {
        if (currentState instanceof PlayingState) {
            // Save current game state
            if (currentGame != null) {
                savedGameState = currentGame.createMemento();
            }

            this.currentState = new PausedState(this);
            notifyObservers(new GameEvent(GameEventType.GAME_PAUSED, "Game paused"));
        }
    }

    public void resumeGame() {
        if (currentState instanceof PausedState) {
            // Restore saved game state if any
            if (currentGame != null && savedGameState != null) {
                currentGame.restoreFromMemento(savedGameState);
            }

            this.currentState = new PlayingState(this);
            notifyObservers(new GameEvent(GameEventType.GAME_RESUMED, "Game resumed"));
            publishGameBoard();
        }
    }

    public void exitGame() {
        if (currentGame != null) {
            notifyObservers(new GameEvent(GameEventType.GAME_EXITED, "Exiting current game"));
            currentGame = null;
            this.currentState = new InitialState(this);
        }
    }

    public void showHint() {
        if (currentGame != null && currentGame instanceof HintDecorator) {
            HintDecorator hintGame = (HintDecorator) currentGame;
            String hint = hintGame.getHint();
            notifyObservers(new GameEvent(GameEventType.HINT_PROVIDED, hint));
        } else if (currentGame != null && currentGame instanceof AbstractGame) {
            AbstractGame abstractGame = (AbstractGame) currentGame;
            String hint = abstractGame.getStrategy().getHint();
            notifyObservers(new GameEvent(GameEventType.HINT_PROVIDED, hint));
        } else {
            notifyObservers(new GameEvent(GameEventType.HINT_PROVIDED, "Hints are not available for this game."));
        }
    }

    public void showScoreboard() {
        notifyObservers(new GameEvent(GameEventType.SCORE_UPDATED, scoreBoard.getScoreString()));
    }

    private void publishGameBoard() {
        if (currentGame != null) {
            notifyObservers(new GameEvent(GameEventType.BOARD_UPDATED, currentGame.getGameDisplay()));
        }
    }

    public String getCurrentGameType() {
        return currentGame != null ? currentGame.getGameType() : null;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    @Override
    public void registerObserver(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.update(event);
        }
    }
}