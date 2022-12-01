package controller;

import board.Board;
import board.Common.ObstacleType;
import board.Dice.Directions;
import board.Player;
import obstacle.Obstacle;
import obstacle.Pillar;
import view.BoardController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static board.Common.*;
import static board.Common.ObstacleType.*;
import static board.Dice.Directions.*;

public class Game {
    public static int gridSize;

    public static List<Player> playerList = new ArrayList<>();

    public static Map<Player, Integer> startCell = new HashMap<Player, Integer>();
    public static Board board;
    public static int count;
    public static int duplicateCount;

    public static List<Player> InitializeBoard(int gSize, int noOfPlayers, List<String> playerNames, BoardController boardController, Map<String, String> playerColor, Map<String, Integer> playerLane) throws URISyntaxException {

        playerList.clear();
        gridSize = gSize;
        board = new Board(gridSize);

        for (int i = 0; i < playerNames.size(); i++) {
            playerList.add(new Player(playerNames.get(i), playerColor.get(playerNames.get(i))));
        }

        for (int i = 0; i < playerList.size(); i++) {
            InitializePlayer(board, playerList.get(i), gridSize, playerLane.get(playerList.get(i).getName()));
            startCell.put(playerList.get(i), playerLane.get(playerList.get(i).getName()));
            boardController.setObject(playerList.get(i).getRowLocation(), playerList.get(i).getColLocation(), getPlayerIconPath(playerColor.get(playerList.get(i).getName())));
        }


        //Obstacle danger1 = new Obstacle("DANGER", "F", ObstacleType.DANGER);
        //Obstacle danger2 = new Obstacle("DANGER", "F", ObstacleType.DANGER);
        //Obstacle danger3 = new Obstacle("danger", "F", ObstacleType.FIRE);

        //InitializeObstacle(board, danger1, 3, 4);
        //InitializeObstacle(board, danger2, 2, 3);
        //InitializeObstacle(board, danger3, 5, 2);

        //boardController.setObject(danger1.getRowLocation(), danger1.getColLocation(), "/Images/danger.png");
        //boardController.setObject(danger2.getRowLocation(), danger2.getColLocation(), "/Images/danger.png");
        //boardController.setObject(fire3.getRowLocation(), fire3.getColLocation(), "/Images/danger.png");

        /*Obstacle pillar1 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle pillar2 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle pillar3 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle pillar4 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);

        InitializeObstacle(board, pillar1, 2, 4);
        InitializeObstacle(board, pillar2, 3, 3);
        InitializeObstacle(board, pillar3, 2, 2);
        InitializeObstacle(board, pillar4, 2, 5);

        boardController.setObject(pillar1.getRowLocation(), pillar1.getColLocation(), "/Images/pillar.png");
        boardController.setObject(pillar2.getRowLocation(), pillar2.getColLocation(), "/Images/pillar.png");
        boardController.setObject(pillar3.getRowLocation(), pillar3.getColLocation(), "/Images/pillar.png");
        boardController.setObject(pillar4.getRowLocation(), pillar4.getColLocation(), "/Images/pillar.png");*/

        /*Obstacle ice1 = new Obstacle("ICE", "I", ObstacleType.ICE);
        Obstacle ice2 = new Obstacle("ICE", "I", ObstacleType.ICE);
        Obstacle ice3 = new Obstacle("ICE", "I", ObstacleType.ICE);
        Obstacle ice4 = new Obstacle("ICE", "I", ObstacleType.ICE);*/

        //InitializeObstacle(board, DANGER, 3, 1);
//        InitializeObstacle(board, PIRATE, 3, 2);
//        InitializeObstacle(board, PIRATE, 2, 3);
        //InitializeObstacle(board, DANGER, 3, 4);
//        InitializeObstacle(board, DANGER, 2, 1);
//        InitializeObstacle(board, DANGER, 2, 2);
//        InitializeObstacle(board, DANGER, 2, 3);
//        InitializeObstacle(board, DANGER, 2, 4);

        //boardController.setObject(3 - 1, 1 - 1, "/Images/danger.png");
//        boardController.setObject(3 - 1, 2 - 1, getObstacleIconPath(PIRATE));
//        boardController.setObject(2 - 1, 3 - 1, getObstacleIconPath(PIRATE));
//        boardController.setObject(3 - 1, 4 - 1, "/Images/danger.png");
//        boardController.setObject(2 - 1, 1 - 1, "/Images/danger.png");
//        boardController.setObject(2 - 1, 2 - 1, "/Images/danger.png");
//        boardController.setObject(2 - 1, 3 - 1, "/Images/danger.png");
//        boardController.setObject(2 - 1, 4 - 1, "/Images/danger.png");

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
                        Obstacle obs = board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle();
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
                            Obstacle obs = board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle();
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
                            Obstacle obs = board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle();
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
                            Obstacle obs = board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle();
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

    public static void InitializePlayer(Board board, Player player, int row, int column) {
        board.setPlayerOnBoard(row - 1, column - 1, player);
        player.setLocation(row - 1, column - 1);
    }

    public static void InitializeObstacle(Board board, ObstacleType obstacleType, int row, int column) {
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
