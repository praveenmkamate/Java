package obstacle;

import board.Board;
import board.Dice.Directions;
import board.Player;
import controller.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.BoardController;

import static board.Common.ObstacleType.DANGER;
import static board.Dice.Directions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class DangerTest {

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

        obstacle = new Danger();

        Game.count = 0;
        Game.startCell.put(testPlayer, 3+1);
        Game.gridSize = gridSize;
        Game.board = board;
    }

    @Test
    void obstacleConditionTestOneForward () {
        Game.count = 1;
        Directions direction = FORWARD;

        board.setObstacleOnBoard(1,3, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(1, 3).getObstacle() instanceof Danger);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestTwoForward () {
        Game.count = 2;
        Directions direction = FORWARD;

        board.setObstacleOnBoard(0,3, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(0, 3).getObstacle() instanceof Danger);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestOneBackward() {
        Game.count = 1;
        Directions direction = BACKWARD;

        board.setObstacleOnBoard(3,3, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(3, 3).getObstacle() instanceof Danger);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestTwoBackward() {
        Game.count = 2;
        Directions direction = BACKWARD;

        board.setObstacleOnBoard(4,3, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(4, 3).getObstacle() instanceof Danger);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestOneLeft() {
        Game.count = 1;
        Directions direction = LEFT;

        board.setObstacleOnBoard(2,2, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(2, 2).getObstacle() instanceof Danger);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestTwoLeft() {
        Game.count = 2;
        Directions direction = LEFT;

        board.setObstacleOnBoard(2,1, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(2, 1).getObstacle() instanceof Danger);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestOneRight() {
        Game.count = 1;
        Directions direction = RIGHT;

        board.setObstacleOnBoard(2,4, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(2, 4).getObstacle() instanceof Danger);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void obstacleConditionTestTwoRight() {
        Game.count = 2;
        Directions direction = RIGHT;

        board.setObstacleOnBoard(2,5, DANGER);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == gridSize-1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if obstacle is at the right location
        assertTrue(board.getBoardCell(2, 5).getObstacle() instanceof Danger);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if player exists on right board cell
        assertTrue(board.getBoardCell(gridSize-1, 3).getPlayer().getName().equals("TestPlayer"));

        System.out.println("After");
        board.printBoard();
    }


}