package edu.neu.csye7374;

class MinesweeperGame extends AbstractGame {
    public MinesweeperGame(GameStrategy strategy) {
        super(strategy);
    }

    @Override
    public String getGameType() {
        return "minesweeper";
    }
}
