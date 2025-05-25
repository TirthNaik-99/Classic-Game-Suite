package edu.neu.csye7374;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoard implements GameObserver {
    private final Map<String, Integer> scores = new HashMap<>();

    public void addScore(String gameType, int points) {
        scores.put(gameType, scores.getOrDefault(gameType, 0) + points);
    }

    public void displayScores() {
        System.out.println("===== SCOREBOARD =====");
        scores.forEach((game, score) -> System.out.println(game + ": " + score));
        System.out.println("======================");
    }

    public String getScoreString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== SCOREBOARD =====\n");

        if (scores.isEmpty()) {
            sb.append("No scores yet\n");
        } else {
            scores.forEach((game, score) -> sb.append(game).append(": ").append(score).append("\n"));
        }

        sb.append("======================");
        return sb.toString();
    }

    @Override
    public void update(GameEvent event) {
        if (event.getType() == GameEventType.GAME_WON) {
            // Score is updated in GameEngine
        }
    }
}