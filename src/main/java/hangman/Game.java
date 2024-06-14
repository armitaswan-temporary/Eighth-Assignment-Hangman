package hangman;

import java.util.UUID;

public class Game {
    private UUID gameID;
    private String username;
    private String word;
    private int wrongGuesses;
    private int time;
    private boolean won;

    public Game(UUID gameId, String username, String word, int wrongGuesses, int time, boolean win) {
        this.gameID = gameId;
        this.username = username;
        this.word = word;
        this.wrongGuesses = wrongGuesses;
        this.time = time;
        this.won = won;
    }
}
