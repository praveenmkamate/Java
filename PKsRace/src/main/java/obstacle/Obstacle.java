package obstacle;

import board.Board;
import board.Player;
import view.BoardController;
import board.Dice.*;

public interface Obstacle {

    public void obstacleCondition(Board board, Player currentPlayer, BoardController boardController, Directions direction);

}
