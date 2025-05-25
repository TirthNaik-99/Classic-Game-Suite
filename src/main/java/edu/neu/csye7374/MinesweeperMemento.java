package edu.neu.csye7374;

class MinesweeperMemento implements Memento {
    private final Cell[][] board;
    private final boolean[][] mines;
    private final boolean[][] revealed;
    private final boolean[][] flagged;
    private final int rows;
    private final int cols;
    private final int totalMines;
    private final int revealedCount;
    private final boolean gameOver;
    private final boolean winner;
    private final String difficulty;

    public MinesweeperMemento(Cell[][] board, boolean[][] mines, boolean[][] revealed, boolean[][] flagged,
                              int rows, int cols, int totalMines, int revealedCount, boolean gameOver,
                              boolean winner, String difficulty) {
        this.board = deepCopyBoard(board);
        this.mines = deepCopyBooleanArray(mines);
        this.revealed = deepCopyBooleanArray(revealed);
        this.flagged = deepCopyBooleanArray(flagged);
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.revealedCount = revealedCount;
        this.gameOver = gameOver;
        this.winner = winner;
        this.difficulty = difficulty;
    }

    private Cell[][] deepCopyBoard(Cell[][] original) {
        if (original == null) return null;
        Cell[][] copy = new Cell[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new Cell[original[i].length];
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    private boolean[][] deepCopyBooleanArray(boolean[][] original) {
        if (original == null) return null;
        boolean[][] copy = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new boolean[original[i].length];
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    @Override
    public Object getState() {
        return this;
    }

    // Getters
    public Cell[][] getBoard() {
        return board;
    }

    public boolean[][] getMines() {
        return mines;
    }

    public boolean[][] getRevealed() {
        return revealed;
    }

    public boolean[][] getFlagged() {
        return flagged;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public int getRevealedCount() {
        return revealedCount;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWinner() {
        return winner;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
