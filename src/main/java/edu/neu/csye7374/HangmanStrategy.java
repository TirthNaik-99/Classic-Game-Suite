package edu.neu.csye7374;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class HangmanStrategy implements GameStrategy {
    private static final String[] WORDS = {
            "ABSTRACT", "INTERFACE", "POLYMORPHISM", "ENCAPSULATION", "INHERITANCE",
            "ALGORITHM", "VARIABLE", "FUNCTION", "CLASS", "METHOD", "PATTERN", "DESIGN",
            "OBJECT", "INSTANCE", "STATIC", "DYNAMIC", "EXCEPTION", "DEBUGGING"
    };

    private String wordToGuess;
    private char[] currentGuess;
    private Set<Character> guessedLetters;
    private int remainingAttempts;
    private boolean gameOver;
    private boolean winner;
    private String difficulty = "NORMAL"; // Default difficulty

    @Override
    public void initializeGame() {
        wordToGuess = WORDS[ThreadLocalRandom.current().nextInt(WORDS.length)];
        currentGuess = new char[wordToGuess.length()];
        for (int i = 0; i < wordToGuess.length(); i++) {
            currentGuess[i] = '_';
        }
        guessedLetters = new HashSet<>();

        // Set attempts based on current difficulty
        setAttemptsForDifficulty();

        gameOver = false;
        winner = false;
    }

    private void setAttemptsForDifficulty() {
        switch (difficulty) {
            case "EASY":
                remainingAttempts = 10; // More attempts for easy mode
                break;
            case "MEDIUM":
                remainingAttempts = 6;  // Standard hangman
                break;
            case "HARD":
                remainingAttempts = 4;  // Fewer attempts for hard mode
                break;
            default:
                remainingAttempts = 6;  // Default to normal/medium
        }
    }

    @Override
    public boolean processMove(String move) {
        if (move.length() != 1) {
            return false;
        }

        char guess = move.toUpperCase().charAt(0);

        if (!Character.isLetter(guess) || guessedLetters.contains(guess)) {
            return false;
        }

        guessedLetters.add(guess);

        boolean correctGuess = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess) {
                currentGuess[i] = guess;
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            remainingAttempts--;
        }

        // Check if the player has won
        boolean allLettersGuessed = true;
        for (char c : currentGuess) {
            if (c == '_') {
                allLettersGuessed = false;
                break;
            }
        }

        if (allLettersGuessed) {
            gameOver = true;
            winner = true;
        }

        // Check if the player has lost
        if (remainingAttempts <= 0) {
            gameOver = true;
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
        sb.append("\n=== HANGMAN ===\n\n");
        sb.append("Difficulty: ").append(difficulty).append("\n\n");

        // Draw the hangman
        sb.append(drawHangman(remainingAttempts)).append("\n\n");

        // Draw the current guess
        for (char c : currentGuess) {
            sb.append(c).append(" ");
        }
        sb.append("\n\n");

        // Draw guessed letters
        sb.append("Guessed letters: ");
        List<Character> sortedGuesses = new ArrayList<>(guessedLetters);
        Collections.sort(sortedGuesses);
        for (char c : sortedGuesses) {
            sb.append(c).append(" ");
        }
        sb.append("\n\n");

        sb.append("Remaining attempts: ").append(remainingAttempts).append("\n");

        if (gameOver) {
            if (winner) {
                sb.append("Congratulations! You guessed the word: ").append(wordToGuess).append("\n");
            } else {
                sb.append("Game over! The word was: ").append(wordToGuess).append("\n");
            }
            sb.append("Type 'exit' to return to the main menu.\n");
        } else {
            sb.append("Enter a letter to guess:\n");
            sb.append("Type 'hint' for a hint, 'pause' to pause the game, or 'exit' to quit.\n");
        }

        return sb.toString();
    }

    private String drawHangman(int remainingAttempts) {
        StringBuilder sb = new StringBuilder();

        switch (remainingAttempts) {
            case 10:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 9:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 8:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append(" |   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 7:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append(" |   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 6:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append(" |   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 5:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append(" |   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 4:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|   |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 3:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|\\  |\n");
                sb.append("     |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 2:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|\\  |\n");
                sb.append("/    |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 1:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|\\  |\n");
                sb.append("/ \\  |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
            case 0:
                sb.append(" +---+\n");
                sb.append(" |   |\n");
                sb.append(" O   |\n");
                sb.append("/|\\  |\n");
                sb.append("/ \\  |\n");
                sb.append("     |\n");
                sb.append("=======");
                break;
        }

        return sb.toString();
    }

    @Override
    public Memento saveState() {
        return new HangmanMemento(
                wordToGuess,
                currentGuess.clone(),
                new HashSet<>(guessedLetters),
                remainingAttempts,
                gameOver,
                winner,
                difficulty
        );
    }

    @Override
    public void restoreState(Memento memento) {
        if (memento instanceof HangmanMemento) {
            HangmanMemento m = (HangmanMemento) memento;
            this.wordToGuess = m.getWordToGuess();
            this.currentGuess = m.getCurrentGuess().clone();
            this.guessedLetters = new HashSet<>(m.getGuessedLetters());
            this.remainingAttempts = m.getRemainingAttempts();
            this.gameOver = m.isGameOver();
            this.winner = m.isWinner();
            this.difficulty = m.getDifficulty();
        }
    }

    @Override
    public void setDifficulty(String level) {
        this.difficulty = level.toUpperCase();
        setAttemptsForDifficulty();
    }

    @Override
    public String getHint() {
        // Find an unfilled letter to reveal as a hint
        List<Character> unrevealedLetters = new ArrayList<>();

        // Create a map of positions where each letter appears
        Map<Character, List<Integer>> letterPositions = new HashMap<>();

        for (int i = 0; i < wordToGuess.length(); i++) {
            char letter = wordToGuess.charAt(i);
            if (currentGuess[i] == '_') {
                if (!letterPositions.containsKey(letter)) {
                    letterPositions.put(letter, new ArrayList<>());
                    unrevealedLetters.add(letter);
                }
                letterPositions.get(letter).add(i);
            }
        }

        if (!unrevealedLetters.isEmpty()) {
            // Choose a random unrevealed letter
            char hintLetter = unrevealedLetters.get(ThreadLocalRandom.current().nextInt(unrevealedLetters.size()));
            return "Try guessing the letter: " + hintLetter;
        }

        return "No hints available - you've almost solved it!";
    }
}