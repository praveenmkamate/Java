package Board;

import Controller.Game;
import view.BoardController;
import Board.Obstacle.*;
public class Board {

    private int length;
    private int breadth;

    private BoardCell[][] board;

    public void setObstacleOnBoard(int length, int breadth, Obstacle obstacle) {
        board[length][breadth] = new BoardCell(obstacle);
    }

    public void setPlayerOnBoard(int length, int breadth, Player player) {
        board[length][breadth] = new BoardCell(player);
    }


    public void movePlayerOnBoard(int length, int breadth, Player player, BoardController boardController) {
        try {
            if (!(board[length][breadth] == null) && (board[length][breadth].getObstacle() != null)) {
                board[length][breadth] = new BoardCell(player, board[length][breadth].getObstacle());
                if(board[length][breadth].getObstacle().getType() == ObstacleType.ICE){
                    boardController.setObject(length,breadth,"/Images/playerIce.png");
                } else {
                    boardController.setObject(length,breadth,getObstaclePath(board[length][breadth].getObstacle()));
                }
            } else {
                Game game = new Game();
                board[length][breadth] = new BoardCell(player);
                boardController.setObject(length,breadth,game.getPlayerIconPath(player.getColor()));
            }
        } catch (Exception e) {
            System.out.println("Exception in movePlayerOnBoard:" + e);
        }
    }

    public void clearBoardCell(int length, int breadth, BoardController boardController) {
        try {
            if (!(board[length][breadth] == null)) {
                if (board[length][breadth].getObstacle() != null) {
                    boardController.removeObject(length,breadth);
                    board[length][breadth] = new BoardCell(board[length][breadth].getObstacle());
                    boardController.setObject(length,breadth,getObstaclePath(board[length][breadth].getObstacle()));

                } else {
                    board[length][breadth] = null;
                    boardController.removeObject(length,breadth);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception on clearBoardCell:" + e);
        }
    }

    public String getObstaclePath(Obstacle obstacle){
        if(obstacle.getType() == ObstacleType.DANGER){
            return "/Images/danger.png";
        } else if(obstacle.getType() == ObstacleType.PILLAR){
            return "/Images/pillar.png";
        } else if(obstacle.getType() == ObstacleType.ICE){
            return "/Images/ice.png";
        } else {
            throw new RuntimeException("Obstacle not found!");
        }
    }

    public String getPlayerPath(Player player){
        //Change
        if(player.getName() == "RockyBhai"){
            return "/Images/redPawn.png";
        } else if(player.getName() == "MunnaBhai"){
            return "/Images/grnPawn.png";
        } else {
            throw new RuntimeException("Obstacle not found!");
        }
    }

    public BoardCell getBoardCell(int length, int breadth) {
        return board[length][breadth];
    }

    /*public void printBoard() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < breadth; j++) {
                if (board[i][j] == null) {
                    System.out.print("_ ");
                } else if (((board[i][j].getObstacle()) instanceof Obstacle) && ((board[i][j].getPlayer()) instanceof Player)) {
                    System.out.print("(" + board[i][j].getPlayer().getInitial() + "," + board[i][j].getObstacle().getInitial() + ")");
                } else if ((board[i][j].getObstacle()) instanceof Obstacle) {
                    System.out.print(board[i][j].getObstacle().getInitial() + " ");
                } else if ((board[i][j].getPlayer()) instanceof Player) {
                    System.out.print(board[i][j].getPlayer().getInitial() + " ");
                } else {
                    throw new RuntimeException("Unknown Object on the board");
                }
            }
            System.out.println("\n");
        }
    }*/

    public Board() {
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

}
