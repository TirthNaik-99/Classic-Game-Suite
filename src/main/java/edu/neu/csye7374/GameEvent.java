package edu.neu.csye7374;

public class GameEvent {
    private final GameEventType type;
    private final String data;

    public GameEvent(GameEventType type, String data) {
        this.type = type;
        this.data = data;
    }

    public GameEventType getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
enum GameEventType {
    GAME_STARTED,
    BOARD_UPDATED,
    INVALID_MOVE,
    GAME_WON,
    GAME_LOST,
    GAME_PAUSED,
    GAME_RESUMED,
    GAME_EXITED,
    SCORE_UPDATED,
    HINT_PROVIDED
}