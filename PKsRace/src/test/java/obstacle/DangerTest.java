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

    int gridSize = 5;


    @BeforeEach
    public void setup() {
        boardController = mock(BoardController.class);
        board = new Board(gridSize);
        testPlayer = new Player("TestPlayer", "Red");
        testPlayer.setLocation(2, 3);
        board.setPlayerOnBoard(2, 3, testPlayer);

        obstacle = new Danger();

        Game.count = 1;
        Game.startCell.put(testPlayer, 3+1);
        Game.gridSize = gridSize;
    }

    @Test
    void obstacleConditionTestForward() {
        int count = 1;
        Directions direction = FORWARD;

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestBackward() {
        int count = 1;
        Directions direction = BACKWARD;

        System.out.println("Before");
        board.printBoard();

        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestLeft() {
        int count = 1;
        Directions direction = LEFT;

        System.out.println("Before");
        board.printBoard();

        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestRight() {
        int count = 1;
        Directions direction = RIGHT;

        assertTrue(board.getBoardCell(2, 3) != null);
        System.out.println("Before");
        board.printBoard();

        System.out.println(" ");

        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        System.out.println("After");
        board.printBoard();
    }
}