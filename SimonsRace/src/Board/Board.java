package Board;

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


    public void movePlayerOnBoard(int length, int breadth, Player player) {
        try {
            if (!(board[length][breadth] == null) && (board[length][breadth].getObstacle() != null)) {
                board[length][breadth] = new BoardCell(player, board[length][breadth].getObstacle());
            } else {
                board[length][breadth] = new BoardCell(player);
            }
        } catch (Exception e) {
            System.out.println("Exception in movePlayerOnBoard:" + e);
        }
    }

    public void clearBoardCell(int length, int breadth) {
        try {
            if (!(board[length][breadth] == null)) {
                if (board[length][breadth].getObstacle() != null) {
                    board[length][breadth] = new BoardCell(board[length][breadth].getObstacle());
                } else {
                    board[length][breadth] = null;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception on clearBoardCell:" + e);
        }
    }

    public BoardCell getBoardCell(int length, int breadth) {
        return board[length][breadth];
    }

    public void printBoard() {
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
    }

    public Board() {
        this.length = 3;
        this.breadth = 4;

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
