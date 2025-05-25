package edu.neu.csye7374;

public class TicTacToeGame extends AbstractGame {
    public TicTacToeGame(GameStrategy strategy) {
        super(strategy);
    }

    @Override
    public String getGameType() {
        return "tictactoe";
    }
}
