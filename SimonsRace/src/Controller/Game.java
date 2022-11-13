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

    public static void main(String args[]) {

        Board board = new Board(rows, columns);

        Player player1 = new Player("Bharath", "B");
        Player player2 = new Player("Nithin", "N");

        Obstacle obstacle1 = new Obstacle("Pit");

        InitializePlayer(board, player1, rows, 1);
        InitializePlayer(board, player2, rows, 3);

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
                    int checkCount;

                    switch (direction) {
                        case "FORWARD":
                            checkCount = 1;

                            if (currentPlayer.getRowLocation() - count < 0) {
                                System.out.println("Almost there. Try Again!");
                                break;
                            } else {
                                while (checkCount <= count) {
                                    if (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setRowLocation(currentPlayer.getRowLocation() - 1);
                                        board.setBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), new BoardCell(currentPlayer));
                                        checkCount++;
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                board.printBoard();
                                break;
                            }


                        case "BACKWARD":
                            if (currentPlayer.getRowLocation() + count > rows - 1) {
                                System.out.println("Sorry, You cannot go outside the board!");
                                break;
                            } else {
                                checkCount = 1;
                                while (checkCount <= count) {
                                    if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setRowLocation(currentPlayer.getRowLocation() + 1);
                                        board.setBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), new BoardCell(currentPlayer));
                                        checkCount++;
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                board.printBoard();
                                break;
                            }
                        case "MISS":
                            board.printBoard();
                            break;
                    }
                }

            }
        }

    }

    public static boolean checkWinningCondition(Player currentPlayer) {
        if (currentPlayer.getRowLocation() == 0) {
            System.out.println(currentPlayer.getName()+" won!");
            return true;
        } else {
            return false;
        }
    }

    public static void InitializePlayer(Board board, Player player, int row, int column) {
        board.setBoardCell(row-1,column-1,new BoardCell(player));
        player.setLocation(row-1,column-1);
        Game.playerList.add(player);
    }

    public static void InitializeObstacle(Board board, Obstacle obstacle, int row, int column) {
        board.setBoardCell(row-1,column-1,new BoardCell(obstacle));
        obstacle.setLocation(row-1,column-1);
    }


}
