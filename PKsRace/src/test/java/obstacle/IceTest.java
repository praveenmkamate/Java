package obstacle;

import board.Board;
import board.Player;
import controller.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.BoardController;

import static board.Common.ObstacleType.ICE;
import static board.Dice.Directions;
import static board.Dice.Directions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class IceTest {

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

        obstacle = new Ice();

        Game.gridSize = gridSize;
        Game.board = board;
    }

    @Test
    void testObstacleConditionOneForward() {

        Game.count = 1;
        Directions direction = FORWARD;

        board.setObstacleOnBoard(1,3, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 1);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(1, 3).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(1, 3).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void testObstacleConditionTwoForward() {

        Game.count = 2;
        Directions direction = FORWARD;

        board.setObstacleOnBoard(0,3, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 0);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(0, 3).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(0, 3).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void testObstacleConditionOneLeft() {

        Game.count = 1;
        Directions direction = LEFT;

        board.setObstacleOnBoard(2,2, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 2);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(2, 2).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(2, 2).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void testObstacleConditionTwoLeft() {

        Game.count = 2;
        Directions direction = LEFT;

        board.setObstacleOnBoard(2,1, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 1);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(2, 1).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(2, 1).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void testObstacleConditionOneRight() {

        Game.count = 1;
        Directions direction = RIGHT;

        board.setObstacleOnBoard(2,4, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 4);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(2, 4).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(2, 4).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void testObstacleConditionTwoRight() {

        Game.count = 2;
        Directions direction = RIGHT;

        board.setObstacleOnBoard(2,5, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 5);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(2, 5).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(2, 5).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void testObstacleConditionOneBack() {

        Game.count = 1;
        Directions direction = BACKWARD;

        board.setObstacleOnBoard(3,3, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 3);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(3, 3).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(3, 3).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }

    @Test
    void testObstacleConditionTwoBack() {

        Game.count = 2;
        Directions direction = BACKWARD;

        board.setObstacleOnBoard(4,3, ICE);
        testPlayer.setScore(100);

        System.out.println("Before");
        board.printBoard();
        System.out.println(" ");

        assertTrue(board.getBoardCell(2, 3) != null);
        obstacle.obstacleCondition(board, testPlayer, boardController, direction);

        // Checking if row was changed to players initial location
        assertTrue(testPlayer.getRowLocation() == 4);
        assertTrue(testPlayer.getColLocation() == 3);
        assertTrue(board.getBoardCell(2, 3) == null);

        // Checking if boardcell has the player
        assertTrue(board.getBoardCell(4, 3).getPlayer().getName() == "TestPlayer");

        // Checking if boardcell has obstacle
        assertTrue(board.getBoardCell(4, 3).getObstacle() instanceof Ice);

        // Checking if score was deducted as intended.
        assertTrue(testPlayer.getScore() == 90);

        // Checking if the player miss condition has been updated
        assertTrue(testPlayer.isMissNextTurn() == true);

        System.out.println("After");
        board.printBoard();
    }
}