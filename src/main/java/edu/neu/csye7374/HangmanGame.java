package edu.neu.csye7374;

class HangmanGame extends AbstractGame {
    public HangmanGame(GameStrategy strategy) {
        super(strategy);
    }

    @Override
    public String getGameType() {
        return "hangman";
    }
}
