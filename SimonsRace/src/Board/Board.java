package Board;

public class Board {

    private int length;
    private int breadth;

    private BoardCell[][] board;

    public void setBoardCell(int length, int breadth, BoardCell boardCell){
        board[length][breadth] = boardCell;
    }

    public void clearBoardCell(int length, int breadth){
        board[length][breadth] = null;
    }

    public BoardCell getBoardCell(int length, int breadth){
        return board[length][breadth];
    }

    public void printBoard(){
        for(int i=0;i<length;i++){
            for(int j=0;j<breadth;j++){
                if(board[i][j] == null){
                    System.out.print("_ ");
                } else if((board[i][j].getObstacle()) instanceof Obstacle){
                    //System.out.print(board[i][j].getObstacle().getName()+" ");
                    System.out.print("X ");
                } else if((board[i][j].getPlayer()) instanceof Player){
                    System.out.print(board[i][j].getPlayer().getInitial()+" ");
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
