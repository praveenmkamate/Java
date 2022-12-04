package board;

import obstacle.Obstacle;
import board.Common.*;
import obstacle.Ice;
import obstacle.Danger;
import obstacle.Pillar;
import obstacle.Santa;
import obstacle.Pirate;

public class BoardCell {
    Player player;
    Obstacle obstacle;

    ObstacleType obstacleType;

    public BoardCell(Common.ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
        this.obstacle = createObstacle(obstacleType);
    }

    public BoardCell(Player player) {
        this.player = player;
    }

    public BoardCell(Player player, ObstacleType obstacleType) {
        this.player = player;
        this.obstacleType = obstacleType;
    }

    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    public Obstacle createObstacle(ObstacleType obstacleType){
        Obstacle obstacle;
        switch (obstacleType){
            case ICE:
                obstacle = new Ice();
                break;
            case DANGER:
                obstacle = new Danger();
                break;
            case PILLAR:
                obstacle = new Pillar();
                break;
            case SANTA:
                obstacle = new Santa();
                break;
            case PIRATE:
                obstacle = new Pirate();
                break;
            default:
                obstacle = null;
                break;

        }
        return obstacle;
    }

    public Obstacle getObstacle() {
        if (this.obstacle == null)
            return null;
        else
            return obstacle;
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
