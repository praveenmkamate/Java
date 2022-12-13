package controller;

import board.Board;
import board.Common;
import board.Common.ObstacleType;
import board.Dice.Directions;
import board.Player;
import board.Player.*;
import obstacle.Obstacle;
import obstacle.Pillar;
import view.BoardController;

import java.net.URISyntaxException;
import java.util.*;

import static board.Common.*;
import static board.Common.ObstacleType.*;
import static board.Dice.Directions.*;


/** **/
public class Game {
    /**
     * This variable is used to store the board size.
     */
    public static int gridSize;

    /**
     * This variable is used to store the difficulty set by user.
     */
    public static String difficulty;

    /**
     * This is used in cases of miss a turn or when user reaches end of the board.
     */
    public static boolean edgeCase;

    /**
     * This is used to store the player list in the current game.
     */
    public static List<Player> playerList = new ArrayList<>();

    /**
     * This is used to store the starting cell of the user. This will be used in case of danger obstacle.
     */
    public static Map<Player, Integer> startCell = new HashMap<Player, Integer>();
    /**
     * This is used to test the obstacle placement.
     */
    public static Map<Integer,Integer> obstacleLocationTest = new HashMap<>();
    /**
     * This is used to create a board.
     */
    public static Board board;
    /**
     * This is used to store the count value of the dice. It gets updated with each step of user movement.
     */
    public static int count;
    /**
     * This is used to store the count value of the dice. It doesn't get updated with each step of user movement.
     */
    public static int duplicateCount;


    /**
     * This method is used to place players on the board.
     * @param boardController This is used to changes on the front end of the board.
     * @param playerNames This is used to store the player names of the current game.
     * @param playerColor This is used to store the player color of the current game.
     * @param playerLane This is used to store the player lane of the current game.
     */
    public static void placePlayersOnBoard(BoardController boardController, List<String> playerNames, Map<String, String> playerColor, Map<String, Integer> playerLane) {
        for (int i = 0; i < playerNames.size(); i++) {
            playerList.add(new Player(playerNames.get(i), playerColor.get(playerNames.get(i))));
        }

        for (int i = 0; i < playerList.size(); i++) {
            initializePlayer(board, playerList.get(i), gridSize, playerLane.get(playerList.get(i).getName()));
            startCell.put(playerList.get(i), playerLane.get(playerList.get(i).getName()));
            boardController.setObject(playerList.get(i).getRowLocation(), playerList.get(i).getColLocation(), getPlayerIconPath(playerColor.get(playerList.get(i).getName())));
        }
    }

    /**
     * This is used to place the obstacles on the board.
     * @param boardController This is used to changes on the front end of the board.
     */
    public static void placeObstaclesOnBoard(BoardController boardController) {

        List<String> obstacleLocation = new ArrayList<>();
        List<ObstacleType> obstacles = new ArrayList<>();
        obstacleLocationTest.clear();

        for (ObstacleType obstacleType : ObstacleType.values()) {
            obstacles.add(obstacleType);
        }

        int validCells = (gridSize * gridSize) - (3 * gridSize);
        int noOfObstacles = validCells / getDifficultyValue(difficulty);

        Random random = new Random();

        int rowMinValue = 2;
        int maxValue = gridSize - 2;
        int colMinValue = 1;
        int row = 0;
        int col = 0;


        for (int i = 0; i < noOfObstacles; i++) {
            boolean containsLocation = true;
            int obstacleNumber = i % obstacles.size();
            while (containsLocation){
                row = (int) ((Math.random() * (maxValue - rowMinValue + 1)) + rowMinValue);
                col = (int) ((Math.random() * (maxValue - colMinValue + 1)) + colMinValue);
                String loc = row+Integer.toString(col);
                if(!obstacleLocation.contains(loc)){
                    obstacleLocation.add(loc);
                    obstacleLocationTest.put(row,col);
                    containsLocation = false;
                }
            }

            initializeObstacle(board, obstacles.get(obstacleNumber), row, col);
            boardController.setObject(row - 1, col - 1, getObstacleIconPath(obstacles.get(obstacleNumber)));

        }

    }

    /**
     * This method is to initialize the board when the game is started.
     * @param gSize This variable is used to store the board size.
     * @param noOfPlayers This variable is used to store the number of players.
     * @param playerNames This is used to store the player names of the current game.
     * @param boardController This is used to changes on the front end of the board.
     * @param playerColor This is used to store the player color of the current game.
     * @param playerLane This is used to store the player lane of the current game.
     * @return This method returns the list of players.
     */
    public static List<Player> initializeBoard(int gSize, int noOfPlayers, List<String> playerNames, BoardController boardController, Map<String, String> playerColor, Map<String, Integer> playerLane){

        playerList.clear();
        gridSize = gSize;
        board = new Board(gridSize);

        placePlayersOnBoard(boardController, playerNames, playerColor, playerLane);
        placeObstaclesOnBoard(boardController);

        return playerList;

    }

    /**
     * This method is used move player on the board. This handles the movement of player on the player.
     * @param countValue The remaining value to move the player.
     * @param direction The direction of the user.
     * @param boardController This is used to changes on the front end of the board.
     * @param currentPlayer This refers to the current player.
     */
    public static void makeAMove(int countValue, Directions direction, BoardController boardController, Player currentPlayer) {

        boardController.setDisplayInformation(" ");

//        boolean edgeCase;
        System.out.println("Current Player: " + currentPlayer.getName() + " Count: " + count + " Direction: " + direction.toString() + " Row: " + (currentPlayer.getRowLocation() - 1));

        count = countValue;
        edgeCase = false;

        while (count > 0 && edgeCase == false) {

            switch (direction) {
                case FORWARD:
                    if (currentPlayer.getRowLocation() == 0) {
                        count = 0;
                        break;
                    } else if (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()) == null) {
                        movePlayer(currentPlayer, direction, boardController);
                        count--;
                        break;
                    } else if (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getPlayer() != null) {
                        new Pillar().obstacleCondition(board, currentPlayer, boardController, direction);
                        break;
                    } else if (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle() != null) {
                        Obstacle obs = (Obstacle) board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle();
                        obs.obstacleCondition(board, currentPlayer, boardController, direction);
                        break;
                    }
                case BACKWARD:
                    if (currentPlayer.getRowLocation() + 1 > gridSize - 1) {
                        boardController.setDisplayInformation("Sorry, You cannot go outside the board!");
                        edgeCase = true;
                        break;
                    } else {
                        if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()) == null) {
                            movePlayer(currentPlayer, direction, boardController);
                            count--;
                            break;
                        } else if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getPlayer() != null) {
                            new Pillar().obstacleCondition(board, currentPlayer, boardController, direction);
                            break;
                        } else if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle() != null) {
                            Obstacle obs = (Obstacle) board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle();
                            obs.obstacleCondition(board, currentPlayer, boardController, direction);
                            break;
                        }
                    }
                case RIGHT:
                    if (currentPlayer.getColLocation() + 1 > gridSize - 1) {
                        direction = boardController.getDirection(currentPlayer, board, direction);
                        break;
                    } else {
                        if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1) == null) {
                            movePlayer(currentPlayer, direction, boardController);
                            count--;
                            break;
                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getPlayer() != null) {
                            new Pillar().obstacleCondition(board, currentPlayer, boardController, direction);
                            break;
                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle() != null) {
                            Obstacle obs = (Obstacle) board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle();
                            obs.obstacleCondition(board, currentPlayer, boardController, direction);
                            break;
                        }
                    }
                case LEFT:
                    if (currentPlayer.getColLocation() - 1 < 0) {
                        direction = boardController.getDirection(currentPlayer, board, direction);
                        break;
                    } else {
                        if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1) == null) {
                            movePlayer(currentPlayer, direction, boardController);
                            count--;
                            break;
                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getPlayer() != null) {
                            new Pillar().obstacleCondition(board, currentPlayer, boardController, direction);
                            break;
                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle() != null) {
                            Obstacle obs = (Obstacle) board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle();
                            obs.obstacleCondition(board, currentPlayer, boardController, direction);
                            break;
                        }
                    }
                case MISS:
                    boardController.setDisplayInformation("Missed Turn.");
                    edgeCase = true;
                    break;
            }
        }

    }

    /**
     * This method initializes player on the board.
     * @param board This refers to the current board object.
     * @param player This refers to the current player.
     * @param row This refers to the row value on the board.
     * @param column This refers to the column value on the board.
     */
    public static void initializePlayer(Board board, Player player, int row, int column) {
        board.setPlayerOnBoard(row - 1, column - 1, player);
        player.setLocation(row - 1, column - 1);
    }

    /**
     * This method initializes obstacle on the board.
     * @param board This refers to the current board object.
     * @param obstacleType This refers to the obstacle type.
     * @param row This refers to the row value on the board.
     * @param column This refers to the column value on the board.
     */
    public static void initializeObstacle(Board board, ObstacleType obstacleType, int row, int column) {
        board.setObstacleOnBoard(row, column, obstacleType);
    }

    /**
     * This method is used to make an actual move by clearing the current cell, updating player object location, moving the player to next cell.
     * @param currentPlayer This refers to the current player.
     * @param directions This refers to the direction in which player is moving.
     * @param boardController This is used to changes on the front end of the board.
     */
    public static void movePlayer(Player currentPlayer, Directions directions, BoardController boardController) {

        int tempRow = currentPlayer.getRowLocation();
        int tempCol = currentPlayer.getColLocation();

        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), boardController);

        if (directions == FORWARD || directions == BACKWARD) {
            tempRow = getRowValue(tempRow, directions);
            currentPlayer.setRowLocation(tempRow);
        } else if (directions == LEFT || directions == RIGHT) {
            tempCol = getColValue(tempCol, directions);
            currentPlayer.setColLocation(tempCol);
        }

        board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer, boardController);

    }


}
