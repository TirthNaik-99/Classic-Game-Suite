package edu.neu.csye7374;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToeStrategy implements GameStrategy {
    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;
    private boolean winner;
    private boolean multiplayerMode;
    private String difficulty = "NORMAL"; // Default difficulty

    @Override
    public void initializeGame() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        currentPlayer = 'X';
        gameOver = false;
        winner = false;
        multiplayerMode = false; // Default to single player
    }

    @Override
    public boolean processMove(String move) {
        try {
            String[] coordinates = move.split("[,\\s]+");
            int row = Integer.parseInt(coordinates[0]) - 1;
            int col = Integer.parseInt(coordinates[1]) - 1;

            if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
                return false;
            }

            board[row][col] = currentPlayer;

            // Check if the current player has won
            if (checkWin(row, col)) {
                gameOver = true;
                winner = true;
                return true;
            }

            // Check if the board is full (draw)
            if (isBoardFull()) {
                gameOver = true;
                return true;
            }

            // Switch player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

            // If single player mode and it's computer's turn
            if (!multiplayerMode && currentPlayer == 'O') {
                makeComputerMove();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void makeComputerMove() {
        // Simple AI for Tic Tac Toe based on difficulty level
        if (difficulty.equals("HARD")) {
            // Hard difficulty - use a smarter strategy
            // First check if computer can win in next move
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        if (checkWin(i, j)) {
                            gameOver = true;
                            winner = true;
                            return;
                        }
                        board[i][j] = ' '; // Undo the move
                    }
                }
            }

            // Check if player can win in next move and block
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        if (checkWin(i, j)) {
                            board[i][j] = 'O'; // Block player's winning move
                            if (isBoardFull()) {
                                gameOver = true;
                            }
                            currentPlayer = 'X'; // Switch back to player
                            return;
                        }
                        board[i][j] = ' '; // Undo the move
                    }
                }
            }

            // Try to take the center
            if (board[1][1] == ' ') {
                board[1][1] = 'O';
                currentPlayer = 'X';
                return;
            }

            // Try to take a corner
            if (board[0][0] == ' ') { board[0][0] = 'O'; currentPlayer = 'X'; return; }
            if (board[0][2] == ' ') { board[0][2] = 'O'; currentPlayer = 'X'; return; }
            if (board[2][0] == ' ') { board[2][0] = 'O'; currentPlayer = 'X'; return; }
            if (board[2][2] == ' ') { board[2][2] = 'O'; currentPlayer = 'X'; return; }
        }
        else if (difficulty.equals("EASY")) {
            // Easy difficulty - make completely random moves
            List<int[]> emptySpots = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        emptySpots.add(new int[]{i, j});
                    }
                }
            }

            if (!emptySpots.isEmpty()) {
                int[] spot = emptySpots.get(ThreadLocalRandom.current().nextInt(emptySpots.size()));
                board[spot[0]][spot[1]] = 'O';
                
                // Check if computer has won
                if (checkWin(spot[0], spot[1])) {
                    gameOver = true;
                    winner = true;
                    return;
                }
                
                // Check if the board is full
                if (isBoardFull()) {
                    gameOver = true;
                }
            }
        }
        else { // MEDIUM difficulty (default)
            // Medium difficulty - mix of smart and random moves
            // 50% chance to make a smart move, 50% chance to make a random move
            if (ThreadLocalRandom.current().nextBoolean()) {
                // Smart move - same as HARD difficulty
                // First check if computer can win in next move
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == ' ') {
                            board[i][j] = 'O';
                            if (checkWin(i, j)) {
                                gameOver = true;
                                winner = true;
                                return;
                            }
                            board[i][j] = ' '; // Undo the move
                        }
                    }
                }

                // Check if player can win in next move and block
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == ' ') {
                            board[i][j] = 'X';
                            if (checkWin(i, j)) {
                                board[i][j] = 'O'; // Block player's winning move
                                if (isBoardFull()) {
                                    gameOver = true;
                                }
                                currentPlayer = 'X'; // Switch back to player
                                return;
                            }
                            board[i][j] = ' '; // Undo the move
                        }
                    }
                }
            }

            // Random move (either as fallback for smart move or as the chosen strategy)
            List<int[]> emptySpots = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        emptySpots.add(new int[]{i, j});
                    }
                }
            }

            if (!emptySpots.isEmpty()) {
                int[] spot = emptySpots.get(ThreadLocalRandom.current().nextInt(emptySpots.size()));
                board[spot[0]][spot[1]] = 'O';
                
                // Check if computer has won
                if (checkWin(spot[0], spot[1])) {
                    gameOver = true;
                    winner = true;
                    return;
                }
                
                // Check if the board is full
                if (isBoardFull()) {
                    gameOver = true;
                }
            }
        }

        currentPlayer = 'X'; // Switch back to player
    }

    private boolean checkWin(int row, int col) {
        char player = board[row][col];

        // Check row
        boolean rowWin = true;
        for (int j = 0; j < 3; j++) {
            if (board[row][j] != player) {
                rowWin = false;
                break;
            }
        }
        if (rowWin) return true;

        // Check column
        boolean colWin = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][col] != player) {
                colWin = false;
                break;
            }
        }
        if (colWin) return true;

        // Check diagonals if applicable
        if (row == col) { // Main diagonal
            boolean diagWin = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] != player) {
                    diagWin = false;
                    break;
                }
            }
            if (diagWin) return true;
        }

        if (row + col == 2) { // Anti-diagonal
            boolean antiDiagWin = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][2 - i] != player) {
                    antiDiagWin = false;
                    break;
                }
            }
            if (antiDiagWin) return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
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
        sb.append("\n=== TIC TAC TOE ===\n\n");

        String modeText = multiplayerMode ? "MULTIPLAYER" : "SINGLE PLAYER";
        sb.append("Mode: ").append(modeText).append("\n\n");

        sb.append("  1 2 3\n");
        for (int i = 0; i < 3; i++) {
            sb.append(i + 1).append(" ");
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        
        if (multiplayerMode) {
            sb.append("\nPlayer X: Player 1");
            sb.append("\nPlayer O: Player 2\n");
            sb.append("\nCurrent player: ").append(currentPlayer).append(" (Player ").append(currentPlayer == 'X' ? "1" : "2").append(")\n");
        } else {
            sb.append("\nPlayer X: You");
            sb.append("\nPlayer O: Computer\n");
            sb.append("\nCurrent player: ").append(currentPlayer).append("\n");
        }

        if (gameOver) {
            if (winner) {
                if (multiplayerMode) {
                    sb.append("Congratulations! Player ").append(currentPlayer).append(" (Player ").append(currentPlayer == 'X' ? "1" : "2").append(") wins!\n");
                } else {
                    if (currentPlayer == 'O') {
                        sb.append("Computer wins! Better luck next time.\n");
                    } else {
                        sb.append("Congratulations! You win!\n");
                    }
                }
            } else {
                sb.append("Game ended in a draw!\n");
            }
            sb.append("Type 'exit' or 'menu' to return to the main menu.\n");
            sb.append("Type 'restart' to play again.\n");
        } else {
            sb.append("Enter your move as 'row,column' (e.g., '2,3')\n");
            sb.append("Type 'hint' for a hint, 'pause' to pause, or 'exit' to quit\n");
        }

        return sb.toString();
    }

    @Override
    public Memento saveState() {
        return new TicTacToeMemento(board, currentPlayer, gameOver, winner, multiplayerMode);
    }

    @Override
    public void restoreState(Memento memento) {
        if (memento instanceof TicTacToeMemento) {
            TicTacToeMemento m = (TicTacToeMemento) memento;
            this.board = m.getBoard();
            this.currentPlayer = m.getCurrentPlayer();
            this.gameOver = m.isGameOver();
            this.winner = m.isWinner();
            this.multiplayerMode = m.isMultiplayerMode();
        }
    }

    @Override
    public void setDifficulty(String level) {
        this.difficulty = level.toUpperCase();
        // Difficulty doesn't affect gameplay for Tic Tac Toe
    }

    public void setMultiplayerMode(boolean multiplayerMode) {
        this.multiplayerMode = multiplayerMode;
    }

    public boolean isMultiplayerMode() {
        return multiplayerMode;
    }

    @Override
    public String getHint() {
        if (multiplayerMode) {
            // In multiplayer mode, provide a hint for the current player
            return getHintForPlayer(currentPlayer);
        } else {
            // In single player mode, only provide hints for the human player (X)
            return getHintForPlayer('X');
        }
    }
    
    private String getHintForPlayer(char player) {
        // First check if there's a winning move for the player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    boolean isWinningMove = checkWin(i, j);
                    board[i][j] = ' '; // Undo the test move

                    if (isWinningMove) {
                        if (multiplayerMode) {
                            return "Player " + player + " can win by placing at: " + (i + 1) + "," + (j + 1);
                        } else {
                            return "You can win by placing at: " + (i + 1) + "," + (j + 1);
                        }
                    }
                }
            }
        }

        // Check if there's a blocking move needed
        char opponent = (player == 'X') ? 'O' : 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = opponent;
                    boolean isBlockingMove = checkWin(i, j);
                    board[i][j] = ' '; // Undo the test move

                    if (isBlockingMove) {
                        if (multiplayerMode) {
                            return "Player " + player + " should block at: " + (i + 1) + "," + (j + 1);
                        } else {
                            return "Block your opponent at: " + (i + 1) + "," + (j + 1);
                        }
                    }
                }
            }
        }

        // Suggest center if available
        if (board[1][1] == ' ') {
            return "The center is a strong position: 2,2";
        }

        // Suggest a corner if available
        if (board[0][0] == ' ') return "Try the corner: 1,1";
        if (board[0][2] == ' ') return "Try the corner: 1,3";
        if (board[2][0] == ' ') return "Try the corner: 3,1";
        if (board[2][2] == ' ') return "Try the corner: 3,3";

        // Suggest any available move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return "Try: " + (i + 1) + "," + (j + 1);
                }
            }
        }

        return "No moves available";
    }
}