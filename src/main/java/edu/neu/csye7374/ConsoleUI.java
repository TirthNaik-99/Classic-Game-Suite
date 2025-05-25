package edu.neu.csye7374;

import java.util.Scanner;

public class ConsoleUI implements GameObserver {
    private final Scanner scanner = new Scanner(System.in);

    public void displayWelcomeMessage() {
        System.out.println("===================================");
        System.out.println("Welcome to Nostalgic Terminal Games");
        System.out.println("===================================");
        System.out.println();
    }

    public void displayMainMenu() {
        System.out.println("Choose a game to play:");
        System.out.println("1. Tic Tac Toe (type: play tictactoe)");
        System.out.println("   - For multiplayer: play tictactoe multiplayer");
        System.out.println("   - Difficulty levels for single player: easy, medium, hard");
        System.out.println("2. Hangman (type: play hangman)");
        System.out.println("   - Difficulty levels: easy, medium, hard");
        System.out.println("3. Minesweeper (type: play minesweeper)");
        System.out.println("   - Difficulty levels: easy, medium, hard");
        System.out.println("Add 'hints' to get hints during gameplay (e.g., play hangman medium hints)");
        System.out.println("Type 'scoreboard' to see your game scores");
        System.out.println("Type 'exit' to exit the current game");
        System.out.println("Type 'quit' to exit the application");
        System.out.print("> ");
    }

    @Override
    public void update(GameEvent event) {
        switch (event.getType()) {
            case GAME_STARTED -> System.out.println("Starting game: " + event.getData());
            case BOARD_UPDATED -> System.out.println(event.getData());
            case INVALID_MOVE -> System.out.println("Invalid move: " + event.getData());
            case GAME_WON -> System.out.println("Congratulations! You won!");
            case GAME_LOST -> System.out.println("Game over!");
            case GAME_PAUSED -> System.out.println("Game paused. Type 'resume' to continue or 'exit' to quit.");
            case GAME_RESUMED -> System.out.println("Game resumed.");
            case GAME_EXITED -> {
                System.out.println("Exiting game.");
                displayMainMenu();
            }
            case SCORE_UPDATED -> System.out.println(event.getData());
            case HINT_PROVIDED -> System.out.println("Hint: " + event.getData());
        }

        if (event.getType() != GameEventType.BOARD_UPDATED) {
            System.out.print("> ");
        }
    }
}