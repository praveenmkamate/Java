package obstacle;

import board.Board;
import board.Dice.Directions;
import board.Player;
import view.BoardController;

import static controller.Game.*;


/**
 * This method has the internal working of the Danger obstacle.
 */
public class Danger implements Obstacle{
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
            boardController.setDisplayInformation("You stepped on the Danger Trap! Start Again");
            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), boardController);
            currentPlayer.setLocation(gridSize - 1, startCell.get(currentPlayer) - 1);
            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer, boardController);
        } else {
            movePlayer(currentPlayer,directions,boardController);
            makeAMove(count,directions,boardController,currentPlayer);
        }
    }
}
