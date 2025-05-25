package edu.neu.csye7374;

public interface GameEventPublisher {
    void registerObserver(GameObserver observer);
    void removeObserver(GameObserver observer);
    void notifyObservers(GameEvent event);
}