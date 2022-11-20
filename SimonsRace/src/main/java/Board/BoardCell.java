package Board;

public class BoardCell {
    Player player;
    Obstacle obstacle;

    public BoardCell(Player player) {
        this.player = player;
    }

    public BoardCell(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public BoardCell(Player player, Obstacle obstacle) {
        this.player = player;
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        if (this.obstacle == null)
            return null;
        else
            return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Player getPlayer() {
        if (this.player == null)
            return null;
        else
            return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
