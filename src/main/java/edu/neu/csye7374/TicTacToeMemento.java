package edu.neu.csye7374;

public class TicTacToeMemento implements Memento {
    private final char[][] board;
    private final char currentPlayer;
    private final boolean gameOver;
    private final boolean winner;
    private final boolean multiplayerMode;

    public TicTacToeMemento(
            char[][] board,
            char currentPlayer,
            boolean gameOver,
            boolean winner,
            boolean multiplayerMode
    ) {
        this.board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, 3);
        }
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
        this.winner = winner;
        this.multiplayerMode = multiplayerMode;
    }

    public char[][] getBoard() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 3);
        }
        return copy;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isMultiplayerMode() {
        return multiplayerMode;
    }

    @Override
    public Object getState() {
        return this;
    }
}