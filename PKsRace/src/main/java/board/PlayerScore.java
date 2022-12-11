package board;

import java.io.Serializable;
import java.util.List;

/**
 * PlayerScore is used to store the list of players on the score board text file.
 */
public class PlayerScore implements Serializable {
    /**
     * List of Players whose data has to be written to the scoreboard.
     */
    List<Player> players;

    /**
     * @param playerList Used to initialize the values for the list of players.
     *
     */
    public PlayerScore(List<Player> playerList) {
        this.players = playerList;
    }

    /**
     * @return Used to get the list of players
     * This method is the getter for the list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param players Used to assign the list of players.
     *                This method is the setter of the list of players.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
