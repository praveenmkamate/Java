package obstacle;

import board.Board;
import board.Dice.Directions;
import board.Player;
import controller.Game;
import view.BoardController;

import static controller.Game.makeAMove;

/**
 * This method has the internal working of the Pillar obstacle.
 */
public class Pillar implements Obstacle {

    /**
     * This method is used to implement the obstacle condition for all the obstacles.
     * @param board           The board object on which obstacle is created.
     * @param currentPlayer   The player who has encountered the obstacle.
     * @param boardController This is used to do changes on the Java FX front end.
     * @param direction       The direction in which the player is currently moving.
     */
    @Override
    public void obstacleCondition(Board board, Player currentPlayer, BoardController boardController, Directions direction) {
        direction = boardController.getDirection(currentPlayer, board, direction);
        if(direction == Directions.MISS){
            Game.edgeCase = true;
        }
        makeAMove(Game.count,direction,boardController,currentPlayer);
    }
}
