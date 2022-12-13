package obstacle;

import board.Board;
import board.Player;
import view.BoardController;
import board.Dice.*;

/**
 * This interface is used to create different types of obstacle.
 */
public interface Obstacle {

    /**
     * This method is used to implement the obstacle condition for all the obstacles.
     * @param board The board object on which obstacle is created.
     * @param currentPlayer The player who has encountered the obstacle.
     * @param boardController This is used to do changes on the Java FX front end.
     * @param direction The direction in which the player is currently moving.
     */
    public void obstacleCondition(Board board, Player currentPlayer, BoardController boardController, Directions direction);

}
