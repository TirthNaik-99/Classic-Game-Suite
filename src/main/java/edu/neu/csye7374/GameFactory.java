package edu.neu.csye7374;

public class GameFactory {
    public Game createGame(String gameType) {
        return switch (gameType.toLowerCase()) {
            case "tictactoe" -> new TicTacToeGame(new TicTacToeStrategy());
            case "hangman" -> new HangmanGame(new HangmanStrategy());
            case "minesweeper" -> new MinesweeperGame(new MinesweeperStrategy());
            default -> throw new IllegalArgumentException("Unknown game type: " + gameType);
        };
    }
}
