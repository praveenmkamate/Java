package board;

import board.Common.ObstacleType;
import controller.Game;
import view.BoardController;

import static board.Common.ObstacleType.*;
import static board.Common.getPlayerIconPath;

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
                if(board[length][breadth].getObstacleType() == ICE){
                    boardController.setObject(length,breadth,"/Images/playerIce.png");
                } else {
                    boardController.setObject(length,breadth,getObstaclePath(board[length][breadth].getObstacleType()));
                }
            } else {
                Game game = new Game();
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
                    boardController.setObject(length,breadth,getObstaclePath(board[length][breadth].getObstacleType()));

                } else {
                    board[length][breadth] = null;
                    boardController.removeObject(length,breadth);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception on clearBoardCell:" + e);
        }
    }

    public String getObstaclePath(ObstacleType obstacleType){
        if(obstacleType == DANGER){
            return "/Images/danger.png";
        } else if(obstacleType == PILLAR){
            return "/Images/pillar.png";
        } else if(obstacleType == ICE){
            return "/Images/ice.png";
        } else {
            throw new RuntimeException("Obstacle not found!");
        }
    }

    public BoardCell getBoardCell(int length, int breadth) {
        if(length < 0)
            length = 0;
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

}
