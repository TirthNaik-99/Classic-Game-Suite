package edu.neu.csye7374;

import java.util.Set;

public class HangmanMemento implements Memento {
    private final String wordToGuess;
    private final char[] currentGuess;
    private final Set<Character> guessedLetters;
    private final int remainingAttempts;
    private final boolean gameOver;
    private final boolean winner;
    private final String difficulty;

    public HangmanMemento(
            String wordToGuess,
            char[] currentGuess,
            Set<Character> guessedLetters,
            int remainingAttempts,
            boolean gameOver,
            boolean winner,
            String difficulty
    ) {
        this.wordToGuess = wordToGuess;
        this.currentGuess = currentGuess;
        this.guessedLetters = guessedLetters;
        this.remainingAttempts = remainingAttempts;
        this.gameOver = gameOver;
        this.winner = winner;
        this.difficulty = difficulty;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public char[] getCurrentGuess() {
        return currentGuess;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
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

    @Override
    public Object getState() {
        return this;
    }
}