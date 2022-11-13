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

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
