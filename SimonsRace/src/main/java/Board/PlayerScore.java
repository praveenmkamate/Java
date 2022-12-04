package board;

import java.io.Serializable;
import java.util.List;

public class PlayerScore implements Serializable {
    List<Player> players;

    public PlayerScore(List<Player> playerList) {
        this.players = playerList;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
