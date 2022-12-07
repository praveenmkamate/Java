package board;

import board.Common.ObstacleType;
import controller.Game;
import obstacle.Danger;
import obstacle.Ice;
import obstacle.Pillar;
import obstacle.Pirate;
import view.BoardController;

import static board.Common.*;
import static board.Common.ObstacleType.*;

public class Board {

    private int length;
    private int breadth;

    private BoardCell[][] board;

    public void setObstacleOnBoard(int length, int breadth, ObstacleType obstacleType) {
        board[length][breadth] = new BoardCell(obstacleType);
    }

    public void setPlayerOnBoard(int length, int breadth, Player player) {
        board[length][breadth] = new BoardCell(player);
    }


    public void movePlayerOnBoard(int length, int breadth, Player player, BoardController boardController) {
        try {
            if (!(board[length][breadth] == null) && (board[length][breadth].getObstacle() != null)) {
                board[length][breadth] = new BoardCell(player, board[length][breadth].getObstacleType());
                if(board[length][breadth].getObstacleType() == ICE || board[length][breadth].getObstacleType() == SANTA || board[length][breadth].getObstacleType() == PIRATE){
                    boardController.setObject(length,breadth,getObstacleChangeIconPath(board[length][breadth].getObstacleType()));
                } else {
                    boardController.setObject(length,breadth,getObstacleIconPath(board[length][breadth].getObstacleType()));
                }
            } else {
                board[length][breadth] = new BoardCell(player);
                boardController.setObject(length,breadth, getPlayerIconPath(player.getColor()));
            }
        } catch (Exception e) {
            System.out.println("Exception in movePlayerOnBoard:" + e);
        }
    }

    public void clearBoardCell(int length, int breadth, BoardController boardController) {
        try {
            if (!(board[length][breadth] == null)) {
                if (board[length][breadth].getObstacleType() != null) {
                    boardController.removeObject(length,breadth);
                    board[length][breadth] = new BoardCell(board[length][breadth].getObstacleType());
                    boardController.setObject(length,breadth,getObstacleIconPath(board[length][breadth].getObstacleType()));
                } else {
                    board[length][breadth] = null;
                    boardController.removeObject(length,breadth);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception on clearBoardCell:" + e);
        }
    }

    public BoardCell getBoardCell(int length, int breadth) {
        if(length < 0)
            length = 0;
        if(board[length][breadth] == null)
            return null;
        else
            return board[length][breadth];
    }

    public Board(int length) {

        this.length = length;
        this.breadth = length;

        board = new BoardCell[length][breadth];
    }

    public Board(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;

        board = new BoardCell[length][breadth];
    }

    public void printBoard() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < breadth; j++) {
                if (board[i][j] == null) {
                    System.out.print("_ ");
                } else if (board[i][j].getObstacle() != null){
                    if((board[i][j].getObstacle()) instanceof Pillar){
                        System.out.print("(Pl)");
                    } else if ((board[i][j].getObstacle()) instanceof Danger){
                        System.out.print("(D)");
                    } else if ((board[i][j].getObstacle()) instanceof Ice){
                        System.out.print("(I)");
                    } else if ((board[i][j].getObstacle()) instanceof Pirate){
                        System.out.print("(Pr)");
                    } else if ((board[i][j].getObstacle()) instanceof Danger){
                        System.out.print("(S)");
                    } else {
                        throw new RuntimeException("Unknown Object on the board");
                    }
                } else if (board[i][j].getPlayer() != null){
                    System.out.print(board[i][j].getPlayer().getName().substring(0,1) +" ");
                } else {
                    throw new RuntimeException("Unknown Object on the board");
                }
            }
            System.out.println("\n");
        }
    }

}
