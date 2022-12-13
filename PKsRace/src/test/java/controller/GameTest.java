package controller;

import board.Board;
import board.Dice;
import board.Dice.Directions;
import board.Player;
import obstacle.*;
import org.junit.jupiter.api.Test;
import view.BoardController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static board.Common.ObstacleType.*;
import static board.Dice.Directions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameTest {

    @Test
    void testPlacePlayersOnBoard() {

        Game.board = new Board(5);
        Game.gridSize = 5;

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        BoardController boardController = mock(BoardController.class);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("1");
        playerNames.add("2");

        Map<String,String> playerColors = new HashMap<>();
        playerColors.put("1","Red");
        playerColors.put("2","Blue");

        Map<String, Integer> playerLane = new HashMap<>();
        playerLane.put("1", 2);
        playerLane.put("2", 4);

        Game.placePlayersOnBoard(boardController,playerNames,playerColors,playerLane);

        System.out.println("After");
        Game.board.printBoard();
        System.out.println(" ");

        assertTrue(Game.board.getBoardCell(4, 1).getPlayer().getName() == "1");
        assertTrue(Game.board.getBoardCell(4, 3).getPlayer().getName() == "2");
    }

    @Test
    void initializeBoardTest() {

        Game.board = new Board(5);
        Game.difficulty = "Easy";

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int gSize = 5;
        int noOfPlayers = 2;

        List<String> playerNames = new ArrayList<>();
        playerNames.add("1");
        playerNames.add("2");

        BoardController boardController = mock(BoardController.class);

        Map<String,String> playerColors = new HashMap<>();
        playerColors.put("1","Red");
        playerColors.put("2","Blue");

        Map<String, Integer> playerLane = new HashMap<>();
        playerLane.put("1", 2);
        playerLane.put("2", 4);

        Game.initializeBoard(gSize,noOfPlayers,playerNames,boardController,playerColors,playerLane);

        System.out.println("After");
        Game.board.printBoard();
        System.out.println(" ");

        // Checking if player exists on the Board.
        assertTrue(Game.board.getBoardCell(5-1,2-1).getPlayer().getName() == "1");
        assertTrue(Game.board.getBoardCell(5-1,4-1).getPlayer().getName() == "2");

        // Checking for Obstacles on the Board.
        for (Map.Entry<Integer, Integer> set : Game.obstacleLocationTest.entrySet()) {
            assertTrue(Game.board.getBoardCell(set.getKey(),set.getValue()).getObstacle() != null);
        }
    }

    @Test
    void testMovePlayerForward(){

        Player testPlayer = new Player("testPlayer","Red");

        Game.gridSize = 5;
        Game.board = new Board(5);

        testPlayer.setRowLocation(2);
        testPlayer.setColLocation(3);


        Directions direction = FORWARD;

        BoardController boardController = mock(BoardController.class);

        Game.movePlayer(testPlayer,direction,boardController);

        assertTrue(Game.board.getBoardCell(1,3).getPlayer().getName() == "testPlayer");

    }

    @Test
    void testMovePlayerBackward(){

        Player testPlayer = new Player("testPlayer","Red");

        Game.gridSize = 5;
        Game.board = new Board(5);

        testPlayer.setRowLocation(2);
        testPlayer.setColLocation(3);


        Directions direction = BACKWARD;

        BoardController boardController = mock(BoardController.class);

        Game.movePlayer(testPlayer,direction,boardController);

        assertTrue(Game.board.getBoardCell(3,3).getPlayer().getName() == "testPlayer");

    }

    @Test
    void testMovePlayerLeft(){

        Player testPlayer = new Player("testPlayer","Red");

        Game.gridSize = 5;
        Game.board = new Board(5);

        testPlayer.setRowLocation(2);
        testPlayer.setColLocation(3);


        Directions direction = LEFT;

        BoardController boardController = mock(BoardController.class);

        Game.movePlayer(testPlayer,direction,boardController);

        assertTrue(Game.board.getBoardCell(2,2).getPlayer().getName() == "testPlayer");

    }
    @Test
    void testMovePlayerRight(){

        Player testPlayer = new Player("testPlayer","Red");

        Game.gridSize = 5;
        Game.board = new Board(5);

        testPlayer.setRowLocation(2);
        testPlayer.setColLocation(3);


        Directions direction = RIGHT;

        BoardController boardController = mock(BoardController.class);

        Game.movePlayer(testPlayer,direction,boardController);

        assertTrue(Game.board.getBoardCell(2,4).getPlayer().getName() == "testPlayer");

    }


    @Test
    void testInitializeObstacleIce(){

        Board board = new Board(5);

        Game.initializeObstacle(board, ICE,2,3);

        assertTrue(board.getBoardCell(2,3).getObstacle() instanceof Ice);

    }

    @Test
    void testInitializeObstaclePillar(){

        Board board = new Board(5);

        Game.initializeObstacle(board, PILLAR,2,3);

        assertTrue(board.getBoardCell(2,3).getObstacle() instanceof Pillar);

    }

    @Test
    void testInitializeObstacleDanger(){

        Board board = new Board(5);

        Game.initializeObstacle(board, DANGER,2,3);

        assertTrue(board.getBoardCell(2,3).getObstacle() instanceof Danger);

    }

    @Test
    void testInitializeObstaclePirate(){

        Board board = new Board(5);

        Game.initializeObstacle(board, PIRATE,2,3);

        assertTrue(board.getBoardCell(2,3).getObstacle() instanceof Pirate);

    }

    @Test
    void testInitializeObstacleSanta(){

        Board board = new Board(5);

        Game.initializeObstacle(board, SANTA,4,3);

        assertTrue(board.getBoardCell(4,3).getObstacle() instanceof Santa);

    }

    @Test
    void testInitializePlayer(){

        Board board = new Board(5);

        Game.initializePlayer(board,new Player("testPlayer"),3,4);

        assertTrue(board.getBoardCell(2,3).getPlayer() instanceof Player);
        assertTrue(board.getBoardCell(2,3).getPlayer().getName() == "testPlayer");

    }

    @Test
    void testPlaceObstaclesOnBoard() {
        BoardController boardController = mock(BoardController.class);


        Game.gridSize = 5;
        Game.difficulty = "Medium";
        Game.board = new Board(5);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        Game.placeObstaclesOnBoard(boardController);

        System.out.println("After");
        Game.board.printBoard();

        for (Map.Entry<Integer, Integer> set : Game.obstacleLocationTest.entrySet()) {
            assertTrue(Game.board.getBoardCell(set.getKey(),set.getValue()).getObstacle() != null);
        }
    }

    @Test
    void testOneMissMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(2,3);
        Game.board.setPlayerOnBoard(2,3,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = MISS;

        assertTrue(Game.board.getBoardCell(2,3).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(2,3).getPlayer().getName() == "testPlayer");

    }

    @Test
    void testTwoMissMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(2,3);
        Game.board.setPlayerOnBoard(2,3,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 2;
        Directions direction = MISS;

        assertTrue(Game.board.getBoardCell(2,3).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(2,3).getPlayer().getName() == "testPlayer");

    }

    @Test
    void testOneForwardMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(3,3);
        Game.board.setPlayerOnBoard(3,3,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = FORWARD;

        assertTrue(Game.board.getBoardCell(3,3).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(2,3).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 3);

    }



    @Test
    void testTwoForwardMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(3,3);
        Game.board.setPlayerOnBoard(3,3,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 2;
        Directions direction = FORWARD;

        assertTrue(Game.board.getBoardCell(3,3).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(1,3).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 1);
        assertTrue(testPlayer.getColLocation() == 3);

    }

    @Test
    void testOneForwardEdgeCaseMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(0,2);
        Game.board.setPlayerOnBoard(0,2,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = FORWARD;

        assertTrue(Game.board.getBoardCell(0,2).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(0,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.count == 0);

    }

    @Test
    void testOneForwardPlayerObstacleMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer","Blue");
        testPlayer.setLocation(2,2);
        Game.board.setPlayerOnBoard(2,2,testPlayer);

        Player testPlayer1 = new Player("player1","Blue");
        testPlayer1.setLocation(1,2);
        Game.board.setPlayerOnBoard(1,2,testPlayer1);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = FORWARD;

        assertTrue(Game.board.getBoardCell(2,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(1,2).getPlayer().getName() == "player1");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(LEFT);

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(2,1).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(1,2).getPlayer().getName() == "player1");

    }

    @Test
    void testOneLeftMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer","Red");
        testPlayer.setLocation(3,3);
        Game.board.setPlayerOnBoard(3,3,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = LEFT;

        assertTrue(Game.board.getBoardCell(3,3).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(3,2).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 3);
        assertTrue(testPlayer.getColLocation() == 2);

    }

    @Test
    void testOneLeftEdgeCaseMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer","Red");
        testPlayer.setLocation(3,0);
        Game.board.setPlayerOnBoard(3,0,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = LEFT;

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(FORWARD);

        assertTrue(Game.board.getBoardCell(3,0).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(3,0) == null);
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 0);

    }

    @Test
    void testOneLeftPlayerObstacleMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer","Blue");
        testPlayer.setLocation(2,2);
        Game.board.setPlayerOnBoard(2,2,testPlayer);

        Player testPlayer1 = new Player("player1","Blue");
        testPlayer1.setLocation(2,1);
        Game.board.setPlayerOnBoard(2,1,testPlayer1);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = LEFT;

        assertTrue(Game.board.getBoardCell(2,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(2,1).getPlayer().getName() == "player1");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(FORWARD);

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(1,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(2,1).getPlayer().getName() == "player1");

    }

    @Test
    void testTwoLeftMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(3,3);
        Game.board.setPlayerOnBoard(3,3,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 2;
        Directions direction = LEFT;

        assertTrue(Game.board.getBoardCell(3,3).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(3,1).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 3);
        assertTrue(testPlayer.getColLocation() == 1);

    }

    @Test
    void testOneRightMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(3,1);
        Game.board.setPlayerOnBoard(3,1,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = RIGHT;

        assertTrue(Game.board.getBoardCell(3,1).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(3,2).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 3);
        assertTrue(testPlayer.getColLocation() == 2);

    }

    @Test
    void testOneRightPlayerObstacleMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer","Blue");
        testPlayer.setLocation(2,2);
        Game.board.setPlayerOnBoard(2,2,testPlayer);

        Player testPlayer1 = new Player("player1","Blue");
        testPlayer1.setLocation(2,3);
        Game.board.setPlayerOnBoard(2,3,testPlayer1);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = RIGHT;

        assertTrue(Game.board.getBoardCell(2,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(2,3).getPlayer().getName() == "player1");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(FORWARD);

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(1,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(2,3).getPlayer().getName() == "player1");

    }

    @Test
    void testOneRightEdgeCaseMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer","Red");
        testPlayer.setLocation(3,4);
        Game.board.setPlayerOnBoard(3,4,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = RIGHT;

        assertTrue(Game.board.getBoardCell(3,4).getPlayer().getName() == "testPlayer");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(FORWARD);

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(3,4) == null);
        assertTrue(testPlayer.getRowLocation() == 2);
        assertTrue(testPlayer.getColLocation() == 4);

    }


    @Test
    void testTwoRightMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(3,1);
        Game.board.setPlayerOnBoard(3,1,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 2;
        Directions direction = RIGHT;

        assertTrue(Game.board.getBoardCell(3,1).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(3,3).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 3);
        assertTrue(testPlayer.getColLocation() == 3);

    }

    @Test
    void testOneBackMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(2,1);
        Game.board.setPlayerOnBoard(2,1,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = BACKWARD;

        assertTrue(Game.board.getBoardCell(2,1).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(3,1).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 3);
        assertTrue(testPlayer.getColLocation() == 1);

    }

    @Test
    void testOneBackEdgeCaseMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(4,1);
        Game.board.setPlayerOnBoard(4,1,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = BACKWARD;

        assertTrue(Game.board.getBoardCell(4,1).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(4,1).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 4);
        assertTrue(testPlayer.getColLocation() == 1);

    }

    @Test
    void testOneBackPlayerObstacleMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer","Blue");
        testPlayer.setLocation(2,2);
        Game.board.setPlayerOnBoard(2,2,testPlayer);

        Player testPlayer1 = new Player("player1","Blue");
        testPlayer1.setLocation(3,2);
        Game.board.setPlayerOnBoard(3,2,testPlayer1);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 1;
        Directions direction = BACKWARD;

        assertTrue(Game.board.getBoardCell(2,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(3,2).getPlayer().getName() == "player1");

        // Returning the user decision as right
        when(boardController.getDirection(any(),any(),any())).thenReturn(MISS);

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(2,2).getPlayer().getName() == "testPlayer");
        assertTrue(Game.board.getBoardCell(3,2).getPlayer().getName() == "player1");

    }

    @Test
    void testTwoBackMakeAMove(){

        BoardController boardController = mock(BoardController.class);

        Game.board = new Board(5);
        Game.gridSize = 5;

        Player testPlayer = new Player("testPlayer");
        testPlayer.setLocation(2,1);
        Game.board.setPlayerOnBoard(2,1,testPlayer);

        System.out.println("Before");
        Game.board.printBoard();
        System.out.println(" ");

        int count = 2;
        Directions direction = BACKWARD;

        assertTrue(Game.board.getBoardCell(2,1).getPlayer().getName() == "testPlayer");

        Game.makeAMove(count,direction,boardController,testPlayer);

        System.out.println("After");
        Game.board.printBoard();

        assertTrue(Game.board.getBoardCell(4,1).getPlayer().getName() == "testPlayer");
        assertTrue(testPlayer.getRowLocation() == 4);
        assertTrue(testPlayer.getColLocation() == 1);

    }
}

