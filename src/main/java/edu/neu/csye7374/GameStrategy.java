package edu.neu.csye7374;

public interface GameStrategy {
    void initializeGame();
    boolean processMove(String move);
    boolean checkGameOver();
    boolean determineWinner();
    String renderGameState();
    Memento saveState();
    void restoreState(Memento memento);
    void setDifficulty(String level);
    String getHint();
}
