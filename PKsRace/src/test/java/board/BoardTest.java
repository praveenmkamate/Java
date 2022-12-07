package board;

import obstacle.*;
import obstacle.Obstacle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import view.BoardController;

import static board.Common.ObstacleType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BoardTest {

    private Board board;
    @BeforeEach
    public void setup(){
        board = new Board(5);
    }


    @Test
    void setObstacleOnBoard() {
        board.setObstacleOnBoard(3,1, ICE);
        assertTrue(board.getBoardCell(3, 1).getObstacle() instanceof Ice);

        board.setObstacleOnBoard(3,2, DANGER);
        assertTrue(board.getBoardCell(3, 2).getObstacle() instanceof Danger);

        board.setObstacleOnBoard(3,3,PILLAR);
        assertTrue(board.getBoardCell(3, 3).getObstacle() instanceof Pillar);

        board.setObstacleOnBoard(3,4,PIRATE);
        assertTrue(board.getBoardCell(3, 4).getObstacle() instanceof Pirate);

        board.setObstacleOnBoard(4,1,SANTA);
        assertTrue(board.getBoardCell(4, 1).getObstacle() instanceof Santa);
    }

    @Test
    void setPlayerOnBoardTest() {
        board.setPlayerOnBoard(4, 3, new Player("TestPlayer"));
        assertTrue(board.getBoardCell(4, 3).getPlayer() instanceof Player);
        assertTrue(board.getBoardCell(4, 3).getPlayer().getName() == "TestPlayer");
    }


    @Test
    void clearBoardCellTest() {
        BoardController boardController = mock(BoardController.class);
        board.setPlayerOnBoard(4, 3, new Player("TestPlayer"));
        assertTrue(board.getBoardCell(4, 3).getPlayer() instanceof  Player);
        board.clearBoardCell(4,3,boardController);
        assertTrue(board.getBoardCell(4, 3) == null);
    }
}