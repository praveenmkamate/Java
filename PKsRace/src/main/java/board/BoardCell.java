package board;

import obstacle.Obstacle;
import board.Common.*;
import obstacle.Ice;
import obstacle.Danger;
import obstacle.Pillar;
import obstacle.Santa;
import obstacle.Pirate;

/**
 * @author Praveen Kamate
 * This class is used to create a board cell in which player, obstacle and obstacleType are placed. Board consists of board cell on each cell.
 */
public class BoardCell {

    /**
     * player represents the Player object which has to be placed on the board cell.
     */
    private Player player;
    /**
     * obstacle represents the Obstacle object which has to be placed on the board cell.
     */
    private Obstacle obstacle;
    /**
     * obstacleType represents the type of Obstacle which has to be placed on the board cell.
     */
    private ObstacleType obstacleType;

    /**
     * This is a BoardCell constructor used to initialize the board cell.
     */
    public BoardCell() {
        this.player = null;
        this.obstacle = null;
        this.obstacleType = null;
    }

    /**
     * This is a BoardCell constructor used to initialize the obstacle type and create an obstacle of the obstacle type.
     * @param obstacleType Initialization of the obstacle type.
     */
    public BoardCell(ObstacleType obstacleType) {
        this.obstacleType = obstacleType;
        this.obstacle = createObstacle(obstacleType);
    }

    /**
     * This is a BoardCell constructor used to initialize the player.
     * @param player Initialization of the player.
     */
    public BoardCell(Player player) {
        this.player = player;
    }


    /**
     * This is a BoardCell constructor used to initialize the player, the obstacle type and create an obstacle of the obstacle type.
     * @param player Initialization of the player.
     * @param obstacleType Initialization of the obstacle type.
     */
    public BoardCell(Player player, ObstacleType obstacleType) {
        this.player = player;
        this.obstacle = createObstacle(obstacleType);
        this.obstacleType = obstacleType;
    }

    /**
     * This method is getter for the ObstacleType.
     * @return Returns the ObstacleType
     */
    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    /**
     * This method is used to create an obstacle.
     * @param obstacleType The type of obstacle to be created.
     * @return Returns the obstacle created.
     */
    public Obstacle createObstacle(ObstacleType obstacleType) {
        Obstacle obstacle;
        switch (obstacleType) {
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

    /**
     * This method is getter for the Obstacle.
     * @return Returns the obstacle.
     */
    public Obstacle getObstacle() {
        if (this.obstacle == null)
            return null;
        else
            return obstacle;
    }


    /**
     * This method is getter for the player.
     * @return Returns the player.
     */
    public Player getPlayer() {
        if (this.player == null)
            return null;
        else
            return player;
    }

    /**
     * This method is setter for the player.
     * @param player The player to be initialized to.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
