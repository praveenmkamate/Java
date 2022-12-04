package obstacle;

import board.Board;
import board.Dice.Directions;
import board.Player;
import controller.Game;
import view.BoardController;

import static controller.Game.makeAMove;

public class Pillar implements Obstacle {

    @Override
    public void obstacleCondition(Board board, Player currentPlayer, BoardController boardController, Directions direction) {
        direction = boardController.getDirection(currentPlayer, board, direction);
        makeAMove(Game.count,direction,boardController,currentPlayer);
    }
}
