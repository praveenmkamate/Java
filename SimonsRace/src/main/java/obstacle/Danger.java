package obstacle;

import board.Board;
import board.Dice.Directions;
import board.Player;
import view.BoardController;

import static controller.Game.*;


public class Danger implements Obstacle{
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
