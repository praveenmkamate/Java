package obstacle;

import board.Board;
import board.Dice;
import board.Player;
import view.BoardController;

import static controller.Game.*;
import static controller.Game.count;

import board.Dice.*;

/**
 * This method has the internal working of the Pirate obstacle.
 */
public class Pirate implements Obstacle {
    /**
     * This method is used to implement the obstacle condition for all the obstacles.
     * @param board           The board object on which obstacle is created.
     * @param currentPlayer   The player who has encountered the obstacle.
     * @param boardController This is used to do changes on the Java FX front end.
     * @param directions      The direction in which the player is currently moving.
     */
    @Override
    public void obstacleCondition(Board board, Player currentPlayer, BoardController boardController, Directions directions) {
        count--;
        if (count == 0) {
            int currentScore = currentPlayer.getScore();
            currentScore = currentScore / duplicateCount;
            currentPlayer.setScore(currentScore);
        }
        movePlayer(currentPlayer, directions, boardController);
        makeAMove(count, directions, boardController, currentPlayer);

    }
}
