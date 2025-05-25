package edu.neu.csye7374;

public interface Game {
    void initialize();
    boolean makeMove(String move);
    boolean isGameOver();
    boolean isWinner();
    String getGameDisplay();
    String getGameType();
    Memento createMemento();
    void restoreFromMemento(Memento memento);
}
