package Board;

public class Test {
    public static void main(String args[]){
        Board board = new Board();

        Player nithin = new Player("Nithin","N");
        Player bharath = new Player("Bharath","B");

        Obstacle pit = new Obstacle("X");

        board.setBoardCell(0,2,new BoardCell(nithin));
        board.setBoardCell(2,1,new BoardCell(bharath));

        board.setBoardCell(1,3,new BoardCell(pit));

        board.printBoard();
    }
}
