package controller;

import board.Board;
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

public class Game {
    public static int gridSize;

    public static String difficulty;

    public static List<Player> playerList = new ArrayList<>();

    public static Map<Player, Integer> startCell = new HashMap<Player, Integer>();
    public static Board board;
    public static int count;
    public static int duplicateCount;

    public static void placePlayersOnBoard(BoardController boardController, List<String> playerNames, Map<String, String> playerColor, Map<String, Integer> playerLane) throws URISyntaxException {
        for (int i = 0; i < playerNames.size(); i++) {
            playerList.add(new Player(playerNames.get(i), playerColor.get(playerNames.get(i))));
        }

        for (int i = 0; i < playerList.size(); i++) {
            initializePlayer(board, playerList.get(i), gridSize, playerLane.get(playerList.get(i).getName()));
            startCell.put(playerList.get(i), playerLane.get(playerList.get(i).getName()));
            boardController.setObject(playerList.get(i).getRowLocation(), playerList.get(i).getColLocation(), getPlayerIconPath(playerColor.get(playerList.get(i).getName())));
        }
    }

    public static void placeObstaclesOnBoard(BoardController boardController) throws URISyntaxException {

        List<String> obstacleLocation = new ArrayList<>();
        List<ObstacleType> obstacles = new ArrayList<>();

        for (ObstacleType obstacleType : ObstacleType.values()) {
            obstacles.add(obstacleType);
        }

        int validCells = (gridSize * gridSize) - (3 * gridSize);
        int noOfObstacles = validCells / getDifficultyValue(difficulty);

        Random random = new Random();

        int rowMinValue = 2;
        int maxValue = gridSize - 1;
        int colMinValue = 1;
        int row = 0;
        int col = 0;


        for (int i = 0; i < noOfObstacles; i++) {
            boolean containsLocation = true;
            int obstacleNumber = i % obstacles.size();
            while (containsLocation){
                row = (int) ((Math.random() * (maxValue - rowMinValue + 1)) + rowMinValue);
                col = (int) ((Math.random() * (maxValue - colMinValue + 1)) + colMinValue);
                String loc = Integer.toString(row)+Integer.toString(col);
                if(!obstacleLocation.contains(loc)){
                    obstacleLocation.add(loc);
                    containsLocation = false;
                }
            }

            initializeObstacle(board, obstacles.get(obstacleNumber), row, col);
            boardController.setObject(row - 1, col - 1, getObstacleIconPath(obstacles.get(obstacleNumber)));

        }

    }

    public static List<Player> InitializeBoard(int gSize, int noOfPlayers, List<String> playerNames, BoardController boardController, Map<String, String> playerColor, Map<String, Integer> playerLane) throws URISyntaxException {

        playerList.clear();
        gridSize = gSize;
        board = new Board(gridSize);

        placePlayersOnBoard(boardController, playerNames, playerColor, playerLane);
        placeObstaclesOnBoard(boardController);

        return playerList;

    }

    public static void makeAMove(int countValue, Directions direction, BoardController boardController, Player currentPlayer) {

        boardController.setDisplayInformation(" ");

        boolean edgeCase;
        System.out.println("Current Player: " + currentPlayer.getName() + " Count: " + count + " Direction: " + direction.toString() + " Row: " + (currentPlayer.getRowLocation() - 1));

        count = countValue;
        duplicateCount = countValue;
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

    public static void initializePlayer(Board board, Player player, int row, int column) {
        board.setPlayerOnBoard(row - 1, column - 1, player);
        player.setLocation(row - 1, column - 1);
    }

    public static void initializeObstacle(Board board, ObstacleType obstacleType, int row, int column) {
        board.setObstacleOnBoard(row - 1, column - 1, obstacleType);
    }

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
        } else {
            throw new RuntimeException("Not a valid Direction");
        }

        board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer, boardController);

    }


}
