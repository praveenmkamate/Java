package Controller;

import Board.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public static int rows = 8;
    public static int columns = 9;

    public static boolean isWin = false;

    static List<Player> playerList = new ArrayList<>();

    static Board board = new Board(rows, columns);

    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        int checkCount;
        boolean edgeCase;

        Player player1 = new Player("Bharath", "B");
        Player player2 = new Player("Nithin", "N");

        Obstacle pillar1 = new Obstacle("Pillar", "P");
        Obstacle pillar2 = new Obstacle("Pillar", "P");

        InitializePlayer(board, player1, rows, 1);
        //InitializeObstacle(board, pillar1, 3, 1);
        InitializePlayer(board, player2, rows, 3);
        //InitializeObstacle(board, pillar1, 5, 3);

        board.printBoard();
        System.out.println("Starting the Game!");

        Dice dice = new Dice();
        while (!isWin) {
            for (int i = 0; i < playerList.size(); i++) {
                if (isWin) {
                    break;
                } else {
                    Player currentPlayer = playerList.get(i);
                    System.out.println("Turn: " + currentPlayer.getName());
                    int count = dice.generateCount();
                    String direction = dice.generateDirection();
                    System.out.println("Count:" + count + " Direction:" + direction);
                    checkCount = count;
                    edgeCase = false;

                    while (checkCount > 0 && edgeCase == false) {
                        switch (direction) {
                            case "FORWARD":
                                if (currentPlayer.getRowLocation() - checkCount  < 0) {
                                    System.out.println("Almost there. Try Again!");
                                    edgeCase = true;
                                    break;
                                } else {
                                    if (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setRowLocation(currentPlayer.getRowLocation() - 1);
                                        board.setBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), new BoardCell(currentPlayer));
                                        checkCount--;
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }
                                    }
                                }
                                break;
                            case "BACKWARD":
                                if (currentPlayer.getRowLocation() + checkCount > rows - 1) {
                                    System.out.println("Sorry, You cannot go outside the board!");
                                    edgeCase = true;
                                    break;
                                } else {
                                    if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setRowLocation(currentPlayer.getRowLocation() + 1);
                                        board.setBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), new BoardCell(currentPlayer));
                                        checkCount--;
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }
                                    }

                                }
                                break;
                            case "RIGHT":
                                if (currentPlayer.getColLocation() + count > columns - 1) {
                                    System.out.println("Sorry, You cannot go outside the board!");
                                    edgeCase = true;
                                } else {

                                }
                                break;
                            case "LEFT":
                                if (currentPlayer.getColLocation() - count > columns - 1) {
                                    System.out.println("Sorry, You cannot go outside the board!");
                                    edgeCase = true;
                                } else {

                                }
                                break;
                            case "MISS":
                                edgeCase = true;
                                break;
                        }
                    }
                    board.printBoard();

                }

            }
        }

    }

    public static boolean checkWinningCondition(Player currentPlayer) {
        if (currentPlayer.getRowLocation() == 0) {
            System.out.println(currentPlayer.getName() + " won!");
            return true;
        } else {
            return false;
        }
    }

    public static void InitializePlayer(Board board, Player player, int row, int column) {
        board.setBoardCell(row - 1, column - 1, new BoardCell(player));
        player.setLocation(row - 1, column - 1);
        Game.playerList.add(player);
    }

    public static void InitializeObstacle(Board board, Obstacle obstacle, int row, int column) {
        board.setBoardCell(row - 1, column - 1, new BoardCell(obstacle));
        obstacle.setLocation(row - 1, column - 1);
    }


}
