package obstacle;

import board.Board;
import view.BoardController;
import board.Player;

import static controller.Game.makeAMove;
import static controller.Game.movePlayer;

import board.Dice.Directions;

import static controller.Game.*;

/**
 * This method has the internal working of the Ice obstacle.
 */
public class Ice implements Obstacle {
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
            currentPlayer.removeScore(10);
            currentPlayer.setMissNextTurn(true);
        }
        movePlayer(currentPlayer, directions, boardController);
        makeAMove(count, directions, boardController, currentPlayer);

    }
}
