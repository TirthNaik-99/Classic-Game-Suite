package edu.neu.csye7374;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

class MinesweeperStrategy implements GameStrategy {
    private static final int BEGINNER_SIZE = 9;
    private static final int BEGINNER_MINES = 10;
    private static final int INTERMEDIATE_SIZE = 16;
    private static final int INTERMEDIATE_MINES = 40;
    private static final int EXPERT_ROWS = 16;
    private static final int EXPERT_COLS = 30;
    private static final int EXPERT_MINES = 99;

    private Cell[][] board;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int rows;
    private int cols;
    private int totalMines;
    private int revealedCount;
    private boolean gameOver;
    private boolean winner;
    private final CellFlyweightFactory cellFactory;
    private String difficulty = "NORMAL"; // Default difficulty

    public MinesweeperStrategy() {
        this.cellFactory = new CellFlyweightFactory();
    }

    @Override
    public void initializeGame() {
        // Set dimensions and mines based on difficulty
        setDimensionsForDifficulty();

        board = new Cell[rows][cols];
        mines = new boolean[rows][cols];
        revealed = new boolean[rows][cols];
        flagged = new boolean[rows][cols];
        revealedCount = 0;
        gameOver = false;
        winner = false;

        // Place mines randomly
        int minesPlaced = 0;
        Random random = new Random();
        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            if (!mines[row][col]) {
                mines[row][col] = true;
                minesPlaced++;
            }
        }

        // Calculate adjacent mines for each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int adjacent = countAdjacentMines(i, j);
                String value = mines[i][j] ? "*" : String.valueOf(adjacent);
                board[i][j] = cellFactory.getCell(value);
            }
        }
    }

    private void setDimensionsForDifficulty() {
        switch (difficulty) {
            case "EASY":
                rows = cols = BEGINNER_SIZE;
                totalMines = BEGINNER_MINES / 2; // Fewer mines for easy mode
                break;
            case "MEDIUM":
                rows = cols = BEGINNER_SIZE;
                totalMines = BEGINNER_MINES;
                break;
            case "HARD":
                rows = cols = INTERMEDIATE_SIZE;
                totalMines = INTERMEDIATE_MINES;
                break;
            default:
                rows = cols = BEGINNER_SIZE;
                totalMines = BEGINNER_MINES;
        }
    }

    private int countAdjacentMines(int row, int col) {
        if (mines[row][col]) {
            return -1; // This is a mine
        }

        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if (mines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public boolean processMove(String move) {
        if (gameOver) {
            return false;
        }

        try {
            String[] parts = move.split("[,\\s]+");
            if (parts.length < 2) {
                return false;
            }

            String action = parts.length > 2 ? parts[0].toLowerCase() : "reveal";
            int row = Integer.parseInt(parts[parts.length > 2 ? 1 : 0]) - 1;
            int col = Integer.parseInt(parts[parts.length > 2 ? 2 : 1]) - 1;

            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                return false;
            }

            if (action.equals("flag") || action.equals("f")) {
                if (!revealed[row][col]) {
                    flagged[row][col] = !flagged[row][col]; // Toggle flag

                    // Check if all mines are correctly flagged
                    checkWinCondition();
                }
                return true;
            } else if (action.equals("reveal") || action.equals("r")) {
                if (flagged[row][col]) {
                    return true; // Can't reveal flagged cells
                }

                if (revealed[row][col]) {
                    // If the cell is already revealed and has a number, try to reveal adjacent cells
                    if (!mines[row][col] && countAdjacentMines(row, col) > 0) {
                        int flaggedAround = countAdjacentFlags(row, col);
                        if (flaggedAround == countAdjacentMines(row, col)) {
                            revealAdjacentCells(row, col);
                        }
                    }
                    return true;
                }

                if (mines[row][col]) {
                    // Game over - hit a mine
                    revealAllMines();
                    gameOver = true;
                    winner = false; // Explicitly set winner to false
                    return true;
                }

                // Reveal the cell
                reveal(row, col);

                // Check if all non-mine cells are revealed
                checkWinCondition();

                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void reveal(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || revealed[row][col] || flagged[row][col]) {
            return;
        }

        revealed[row][col] = true;
        revealedCount++;

        // If it's a cell with no adjacent mines, reveal adjacent cells
        if (countAdjacentMines(row, col) == 0) {
            for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
                for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                    if (i != row || j != col) {
                        reveal(i, j);
                    }
                }
            }
        }
    }

    private void revealAdjacentCells(int row, int col) {
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if ((i != row || j != col) && !revealed[i][j] && !flagged[i][j]) {
                    if (mines[i][j]) {
                        // Hit a mine - game over
                        revealAllMines();
                        gameOver = true;
                        winner = false; // Explicitly set winner to false
                        return;
                    }
                    reveal(i, j);
                }
            }
        }
    }

    private int countAdjacentFlags(int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if (flagged[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void revealAllMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mines[i][j]) {
                    revealed[i][j] = true;
                }
            }
        }
    }

    private void checkWinCondition() {
        // Win if all non-mine cells are revealed
        if (revealedCount == (rows * cols - totalMines)) {
            gameOver = true;
            winner = true;
            return;
        }

        // Win if all mines are correctly flagged
        int correctlyFlagged = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mines[i][j] && flagged[i][j]) {
                    correctlyFlagged++;
                }
            }
        }

        if (correctlyFlagged == totalMines) {
            gameOver = true;
            winner = true;
        }
    }

    @Override
    public boolean checkGameOver() {
        return gameOver;
    }

    @Override
    public boolean determineWinner() {
        return winner;
    }

    @Override
    public String renderGameState() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== MINESWEEPER ===\n\n");
        sb.append("Difficulty: ").append(difficulty).append("\n\n");

        // Column numbers
        sb.append("   ");
        for (int j = 0; j < cols; j++) {
            sb.append(j + 1).append(" ");
        }
        sb.append("\n");

        // Top border
        sb.append("  +");
        for (int j = 0; j < cols; j++) {
            sb.append("-+");
        }
        sb.append("\n");

        // Board
        for (int i = 0; i < rows; i++) {
            sb.append(i + 1).append(" |");
            for (int j = 0; j < cols; j++) {
                if (flagged[i][j]) {
                    sb.append("F|");
                } else if (!revealed[i][j]) {
                    sb.append("·|");
                } else {
                    String cellValue = board[i][j].getValue();
                    if (cellValue.equals("0")) {
                        sb.append(" |");
                    } else {
                        sb.append(cellValue).append("|");
                    }
                }
            }
            sb.append(" ").append(i + 1).append("\n");

            // Row separator
            sb.append("  +");
            for (int j = 0; j < cols; j++) {
                sb.append("-+");
            }
            sb.append("\n");
        }

        // Column numbers (bottom)
        sb.append("   ");
        for (int j = 0; j < cols; j++) {
            sb.append(j + 1).append(" ");
        }
        sb.append("\n\n");

        // Game status
        sb.append("Mines: ").append(totalMines).append("\n");
        sb.append("Flags placed: ").append(countTotalFlags()).append("\n\n");

        if (gameOver) {
            if (winner) {
                sb.append("Congratulations! You won!\n");
            } else {
                sb.append("Game over! You hit a mine!\n");
            }
            sb.append("Type 'exit' or 'menu' to return to the main menu.\n");
            sb.append("Type 'restart' to play again.\n");
        } else {
            sb.append("Commands:\n");
            sb.append("- To reveal a cell: 'reveal row,col' or just 'row,col'\n");
            sb.append("- To flag/unflag a cell: 'flag row,col'\n");
            sb.append("- Type 'hint' for a hint, 'pause' to pause, or 'exit' to quit\n");
        }

        return sb.toString();
    }

    private int countTotalFlags() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (flagged[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Memento saveState() {
        return new MinesweeperMemento(
                board,
                mines,
                revealed,
                flagged,
                rows,
                cols,
                totalMines,
                revealedCount,
                gameOver,
                winner,
                difficulty
        );
    }

    @Override
    public void restoreState(Memento memento) {
        if (memento instanceof MinesweeperMemento) {
            MinesweeperMemento m = (MinesweeperMemento) memento;
            this.board = m.getBoard();
            this.mines = m.getMines();
            this.revealed = m.getRevealed();
            this.flagged = m.getFlagged();
            this.rows = m.getRows();
            this.cols = m.getCols();
            this.totalMines = m.getTotalMines();
            this.revealedCount = m.getRevealedCount();
            this.gameOver = m.isGameOver();
            this.winner = m.isWinner();
            this.difficulty = m.getDifficulty();
        }
    }

    @Override
    public void setDifficulty(String level) {
        this.difficulty = level.toUpperCase();
    }

    @Override
    public String getHint() {
        // Find a safe cell to reveal (one that doesn't have a mine)
        List<int[]> safeCells = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!mines[i][j] && !revealed[i][j] && !flagged[i][j]) {
                    // Check how many mines are adjacent to it
                    int adjacentMines = countAdjacentMines(i, j);
                    if (adjacentMines == 0 || adjacentMines == 1) {
                        // Prefer cells with low mine count
                        safeCells.add(new int[]{i, j});
                    }
                }
            }
        }

        if (!safeCells.isEmpty()) {
            Random rand = new Random();
            int[] safeCell = safeCells.get(rand.nextInt(safeCells.size()));
            return "Try revealing cell at position: " + (safeCell[0] + 1) + "," + (safeCell[1] + 1);
        }

        // If no safe cells with low count, suggest any safe cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!mines[i][j] && !revealed[i][j] && !flagged[i][j]) {
                    return "Try revealing cell at position: " + (i + 1) + "," + (j + 1);
                }
            }
        }

        return "No safe hints available";
    }
}