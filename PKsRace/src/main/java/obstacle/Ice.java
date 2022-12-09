package obstacle;

import board.Board;
import view.BoardController;
import board.Player;

import static controller.Game.makeAMove;
import static controller.Game.movePlayer;

import board.Dice.Directions;

import static controller.Game.*;

public class Ice implements Obstacle {
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
