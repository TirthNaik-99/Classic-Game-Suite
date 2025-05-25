package edu.neu.csye7374;

public class CommandProcessor {
    private final GameEngine gameEngine;

    public CommandProcessor(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public Command parseCommand(String input) {
        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        if (gameEngine.getCurrentState() instanceof InitialState) {
            if (command.equals("play")) {
                if (parts.length > 1) {
                    String gameType = parts[1];

                    GameBuilder.Difficulty difficulty = GameBuilder.Difficulty.NORMAL;
                    boolean withHints = false;
                    boolean multiplayer = false;

                    // Process additional parameters
                    for (int i = 2; i < parts.length; i++) {
                        if (parts[i].equalsIgnoreCase("easy") ||
                                parts[i].equalsIgnoreCase("medium") ||
                                parts[i].equalsIgnoreCase("hard")) {
                            difficulty = GameBuilder.Difficulty.valueOf(parts[i].toUpperCase());
                        } else if (parts[i].equalsIgnoreCase("hints")) {
                            withHints = true;
                        } else if (parts[i].equalsIgnoreCase("multiplayer") && gameType.equalsIgnoreCase("tictactoe")) {
                            multiplayer = true;
                        }
                    }

                    return new StartGameCommand(gameEngine, gameType, difficulty, withHints, multiplayer);
                }
            } else if (command.equals("scores") || command.equals("scoreboard")) {
                return new ShowScoreboardCommand(gameEngine);
            } else if (command.equals("exit")) {
                return new ExitCommand(gameEngine);
            } else if (command.equals("quit")) {
                return new ExitCommand(gameEngine, true); // Exit the application
            }
        } else if (gameEngine.getCurrentState() instanceof PlayingState) {
            if (command.equals("move")) {
                if (parts.length > 1) {
                    return new MakeMoveCommand(gameEngine, parts[1]);
                }
            } else if (input.matches("\\d+[,\\s]\\d+") || input.matches("[a-zA-Z]")) {
                return new MakeMoveCommand(gameEngine, input);
            } else if (command.equals("hint")) {
                return new GetHintCommand(gameEngine);
            } else if (command.equals("pause")) {
                return new PauseGameCommand(gameEngine);
            } else if (command.equals("exit")) {
                return new ExitCommand(gameEngine);
            }
        } else if (gameEngine.getCurrentState() instanceof PausedState) {
            if (command.equals("resume")) {
                return new ResumeGameCommand(gameEngine);
            } else if (command.equals("exit")) {
                return new ExitCommand(gameEngine);
            }
        } else if (gameEngine.getCurrentState() instanceof EndedState) {
            if (command.equals("exit") || command.equals("menu") || command.equals("main")) {
                return new ExitCommand(gameEngine);
            } else if (command.equals("restart")) {
                return new StartGameCommand(gameEngine, gameEngine.getCurrentGameType(), GameBuilder.Difficulty.NORMAL, false, false);
            }
        }

        // Default command that does nothing but keeps the game running
        return () -> true;
    }
}