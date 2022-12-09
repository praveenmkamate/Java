package obstacle;

import board.Board;
import board.Dice;
import board.Player;
import controller.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.BoardController;

import static board.Common.ObstacleType.ICE;
import static board.Common.ObstacleType.PILLAR;
import static board.Dice.Directions.FORWARD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PillarTest {

    private BoardController boardController;
    private Player testPlayer;
    private Board board;
    private Obstacle obstacle;

    int gridSize = 6;

    @BeforeEach
    public void setup() {
        boardController = mock(BoardController.class);
        board = new Board(gridSize);
        testPlayer = new Player("TestPlayer", "Red");
        testPlayer.setLocation(2, 3);
        board.setPlayerOnBoard(2, 3, testPlayer);

        obstacle = new Pillar();

        Game.gridSize = gridSize;
        Game.board = board;
    }

    @Test
    void testObstacleConditionLeft() {

        Game.count = 1;
        Dice.Directions direction = FORWARD;

        board.setObstacleOnBoard(1, 3, PILLAR);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        // Returning the user decision as left
        when(boardController.getDirection(any(),any(),any())).thenReturn(Dice.Directions.LEFT);

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 2);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(1, 3).getObstacle() instanceof Pillar);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(2, 2).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();

    }

    @Test
    void testObstacleConditionRight() {

        Game.count = 1;
        Dice.Directions direction = FORWARD;

        board.setObstacleOnBoard(1, 3, PILLAR);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(Dice.Directions.RIGHT);

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 4);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(1, 3).getObstacle() instanceof Pillar);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(2, 4).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();

    }

    @Test
    void testObstacleConditionMiss() {

        Game.count = 1;
        Dice.Directions direction = FORWARD;

        board.setObstacleOnBoard(1, 3, PILLAR);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(Dice.Directions.MISS);

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 3);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(1, 3).getObstacle() instanceof Pillar);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(2, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();

    }

    @Test
    void testObstacleConditionBack() {

        Game.count = 1;
        Dice.Directions direction = FORWARD;

        board.setObstacleOnBoard(1, 3, PILLAR);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(Dice.Directions.BACKWARD);

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 3);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(1, 3).getObstacle() instanceof Pillar);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(3, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();

    }
}