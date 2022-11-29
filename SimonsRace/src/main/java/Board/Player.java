package board;

import java.io.Serializable;

import static controller.Game.gridSize;

public class Player implements Serializable {

    private String name;
    private String color;

    private int rowLocation;
    private int colLocation;

    private int score;

    private boolean missNextTurn = false;


    public Player(int rowLocation, int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

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
        if(rowLocation<0)
            this.rowLocation = 0;
        else if(rowLocation >= gridSize - 1)
            this.rowLocation = gridSize - 1;
        else
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

    public String getColor() {
        return color;
    }

    public void addScore(int number){
        if(number > 0){
            this.score = this.score + number;
        } else {
            System.out.println("Illegal score");
        }
    }

    public void removeScore(int number){
        if(number > 0){
            if(this.score - number > 0){
                this.score = this.score - number;
            } else {
                this.score = 0;
            }
        } else {
            System.out.println("Illegal score");
        }
    }
}
