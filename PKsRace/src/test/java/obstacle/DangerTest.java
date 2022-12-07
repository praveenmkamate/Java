package obstacle;

import board.Board;
import board.Dice.*;
import board.Player;
import controller.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import view.BoardController;


import static board.Dice.Directions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class DangerTest {

    private BoardController boardController;
    private Player testPlayer;
    private Board board;
    private Obstacle obstacle;

    @BeforeEach
    public void setup() {
        boardController = mock(BoardController.class);
        board = new Board(5);
        testPlayer = new Player("TestPlayer", "Red");
        testPlayer.setLocation(2, 3);
        board.setPlayerOnBoard(2, 3, testPlayer);

        obstacle = new Danger();

        Game.count = 1;
        Game.startCell.put(testPlayer, 3);
        Game.gridSize = 5;
    }

    @Test
    void obstacleConditionTestForward() {
        int count = 1;
        Directions direction = FORWARD;

        board.printBoard();

        System.out.println(" ");

        obstacle.obstacleCondition(board, testPlayer, boardController, direction);


        assertTrue(testPlayer.getRowLocation() == 4);
        assertTrue(testPlayer.getColLocation() == 2);
        assertTrue(board.getBoardCell(2, 3) == null);

        board.printBoard();
    }
}