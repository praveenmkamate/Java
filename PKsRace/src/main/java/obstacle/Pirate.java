package obstacle;

import board.Board;
import board.Dice;
import board.Player;
import view.BoardController;

import static controller.Game.*;
import static controller.Game.count;

import board.Dice.*;

public class Pirate implements Obstacle {
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
