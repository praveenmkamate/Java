package board;

import board.Common.ObstacleType;
import controller.Game;
import obstacle.*;
import view.BoardController;

import static board.Common.*;
import static board.Common.ObstacleType.*;


/**
 * @author Praveen Kamate
 * This class is used to create a two-dimensional board.
 * Board has multiple board cells. Each board cell can have a player object or an obstacle object or both.
 */
public class Board {

    /**
     * length variable represents the number of rows on the board.
     */
    private int length;


    /**
     * breath variable represents the number of columns on the board.
     */
    private int breadth;

    /**
     * board variable has multiple Board Cells.
     */
    private BoardCell[][] board;

    /**
     * @param length  represents on which row the player has to be placed on the board.
     * @param breadth represents on which column the player has to be placed on the board.
     * @param player  represents the player which has to be placed on the board.
     *                This method is used to place the players on the board.
     */
    public void setPlayerOnBoard(int length, int breadth, Player player) {
        board[length][breadth] = new BoardCell(player);
    }

    /**
     * @param length       represents on which row the player has to be placed on the board.
     * @param breadth      represents on which column the player has to be placed on the board.
     * @param obstacleType represents the obstacle type which has to be placed on the board.
     *                     This method is used to place the obstacles on the board.
     */
    public void setObstacleOnBoard(int length, int breadth, ObstacleType obstacleType) {
        board[length][breadth] = new BoardCell(obstacleType);
    }


    /**
     * @param length          represents on which row the player has to be moved to on the board.
     * @param breadth         represents on which column the player has to be moved to on the board.
     * @param player          represents the player who has to be moved.
     * @param boardController represents the JavaFX board controller used to display the changes on the front end of the board.
     *                        This method is used to move the player on the board.
     */
    public void movePlayerOnBoard(int length, int breadth, Player player, BoardController boardController) {
        try {
            if (!(board[length][breadth] == null) && (board[length][breadth].getObstacle() != null)) {
                board[length][breadth] = new BoardCell(player, board[length][breadth].getObstacleType());
                if (board[length][breadth].getObstacleType() == ICE || board[length][breadth].getObstacleType() == SANTA || board[length][breadth].getObstacleType() == PIRATE) {
                    boardController.setObject(length, breadth, getObstacleChangeIconPath(board[length][breadth].getObstacleType()));
                } else {
                    boardController.setObject(length, breadth, getObstacleIconPath(board[length][breadth].getObstacleType()));
                }
            } else {
                board[length][breadth] = new BoardCell(player);
                boardController.setObject(length, breadth, getPlayerIconPath(player.getColor()));
            }
        } catch (Exception e) {
            System.out.println("Exception in movePlayerOnBoard:" + e);
        }
    }

    /**
     * @param length          represents on which row the player has to be removed from the board.
     * @param breadth         represents on which column the player has to be removed from the board.
     * @param boardController represents the JavaFX board controller used to display the changes on the front end of the board.
     *                        This method is used to clear the player from the board cell of the board.
     */
    public void clearBoardCell(int length, int breadth, BoardController boardController) {
        try {
            if (!(board[length][breadth] == null)) {
                if (board[length][breadth].getObstacleType() != null) {
                    boardController.removeObject(length, breadth);
                    board[length][breadth] = new BoardCell(board[length][breadth].getObstacleType());
                    boardController.setObject(length, breadth, getObstacleIconPath(board[length][breadth].getObstacleType()));
                } else {
                    board[length][breadth] = null;
                    boardController.removeObject(length, breadth);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception on clearBoardCell:" + e);
        }
    }

    /**
     * @param length  represents the row value of the board cell to be fetched.
     * @param breadth represents the column value of the board cell to be fetched.
     * @return BoardCell which can contains a player or obstacle or both.
     * This method is used to get the board cell on specific row and column.
     */
    public BoardCell getBoardCell(int length, int breadth) {
        if (length < 0)
            length = 0;
        if (board[length][breadth] == null)
            return null;
        else
            return board[length][breadth];
    }

    /**
     * @param length is the number of rows and columns for which board is created.
     *               Board is a constructor to create a board of required rows and columns.
     */
    public Board(int length) {

        this.length = length;
        this.breadth = length;

        board = new BoardCell[length][breadth];
    }

    /**
     * @param length  is the number of rows for which board is created.
     * @param breadth is the number of columns for which board is created.
     *                Board is a constructor to create a board of required rows and columns.
     */
    public Board(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;

        board = new BoardCell[length][breadth];
    }

    /**
     * This method is used print the board and it's contents. This is very helpful during testing the backend components.
     */
    public void printBoard() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < breadth; j++) {
                if (board[i][j] == null) {
                    System.out.print("_ ");
                } else if (board[i][j].getObstacle() != null && board[i][j].getPlayer() != null) {
                    if ((board[i][j].getObstacle()) instanceof Pillar) {
                        System.out.print("(Pl" + board[i][j].getPlayer().getName().substring(0, 1) + ")");
                    } else if ((board[i][j].getObstacle()) instanceof Danger) {
                        System.out.print("(D" + board[i][j].getPlayer().getName().substring(0, 1) + ")");
                    } else if ((board[i][j].getObstacle()) instanceof Ice) {
                        System.out.print("(I" + board[i][j].getPlayer().getName().substring(0, 1) + ")");
                    } else if ((board[i][j].getObstacle()) instanceof Pirate) {
                        System.out.print("(Pr" + board[i][j].getPlayer().getName().substring(0, 1) + ")");
                    } else if ((board[i][j].getObstacle()) instanceof Santa) {
                        System.out.print("(S" + board[i][j].getPlayer().getName().substring(0, 1) + ")");
                    } else {
                        throw new RuntimeException("Unknown Object on the board");
                    }
                } else if (board[i][j].getObstacle() != null) {
                    if ((board[i][j].getObstacle()) instanceof Pillar) {
                        System.out.print("(Pl)");
                    } else if ((board[i][j].getObstacle()) instanceof Danger) {
                        System.out.print("(D)");
                    } else if ((board[i][j].getObstacle()) instanceof Ice) {
                        System.out.print("(I)");
                    } else if ((board[i][j].getObstacle()) instanceof Pirate) {
                        System.out.print("(Pr)");
                    } else if ((board[i][j].getObstacle()) instanceof Santa) {
                        System.out.print("(S)");
                    } else {
                        throw new RuntimeException("Unknown Object on the board");
                    }
                } else if (board[i][j].getPlayer() != null) {
                    System.out.print(board[i][j].getPlayer().getName().substring(0, 1) + " ");
                } else {
                    throw new RuntimeException("Unknown Object on the board");
                }
            }
            System.out.println("\n");
        }
    }

}
