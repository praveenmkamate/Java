package controller;

import board.Board;
import board.Dice.*;
import board.Player;
import org.junit.jupiter.api.Test;
import view.BoardController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static board.Dice.Directions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameTest {

    @Test
    void testPlacePlayersOnBoard() throws URISyntaxException {

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

    /*@Test
    void initializeBoardTest() throws URISyntaxException {

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
    }*/

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
}