package Board;

public class Player {

    private String name;
    private String color;

    private int rowLocation;
    private int colLocation;

    private int score;

    private boolean missNextTurn = false;


    public Player(String name, String color) {
        this.score = 0;
        this.name = name;
        this.color = color;
    }

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public boolean isMissNextTurn() {
        return missNextTurn;
    }

    public void setMissNextTurn(boolean missNextTurn) {
        this.missNextTurn = missNextTurn;
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public void setRowLocation(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    public void setLocation(int rowLocation,int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
