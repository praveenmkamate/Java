package board;

import java.io.Serializable;

import static controller.Game.gridSize;

/**
 * Player class is used to create the player. It implements serializable to enable us to store the score.
 */
public class Player implements Serializable {

    /**
     * It is used to store the player name.
     */
    private String name;

    /**
     * It is used to store the color of the player.
     */
    private String color;

    /**
     * It is used to store the row location of the player on the board.
     */
    private int rowLocation;

    /**
     * It is used to store the column location of the player on the board.
     */
    private int colLocation;

    /**
     * It is used to store the score of the player.
     */
    private int score;

    /**
     * It is used in case of Ice obstacle. This next turn is missed when the player steps on the ice obstacle, it is handled by this variable.
     */
    private boolean missNextTurn = false;


    /**
     * This constructor is used initialized the player object with its location.
     * @param rowLocation The row location to be initialized.
     * @param colLocation The column location to be initialized.
     */
    public Player(int rowLocation, int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

    /**
     * This constructor is used initialized the player object with its name and color.
     * @param name  The player name to be initialized.
     * @param color The player color to be initialized.
     */
    public Player(String name, String color) {
        this.score = 0;
        this.name = name;
        this.color = color;
    }

    /**
     * This constructor is used initialized the player object with its name.
     * @param name The player name to be initialized.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * This method is getter for the missNextTurn.
     * @return This returns if the value of missNextTurn.
     */
    public boolean isMissNextTurn() {
        return missNextTurn;
    }

    /**
     * This method is setter for the missNextTurn.
     * @param missNextTurn This is the value of missNextTurn to be updated with.
     */
    public void setMissNextTurn(boolean missNextTurn) {
        this.missNextTurn = missNextTurn;
    }

    /**
     * This method is getter for the row value of the player.
     * @return This returns the row value of the player.
     */
    public int getRowLocation() {
        return rowLocation;
    }

    /**
     * This method is setter for the row value of the player.
     * @param rowLocation This assigns the row value of the player.
     */
    public void setRowLocation(int rowLocation) {
        if (rowLocation < 0)
            this.rowLocation = 0;
        else if (rowLocation >= gridSize - 1)
            this.rowLocation = gridSize - 1;
        else
            this.rowLocation = rowLocation;
    }

    /**
     * This method is getter for the col value of the player.
     * @return This returns the col value of the player.
     */
    public int getColLocation() {
        return colLocation;
    }

    /**
     * This method is setter for the col value of the player.
     * @param colLocation This assigns the col value of the player.
     */
    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    /**
     * This method is setter for the player location.
     * @param rowLocation This assigns the row value of the player.
     * @param colLocation This assigns the col value of the player.
     */
    public void setLocation(int rowLocation, int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

    /**
     * This method is getter for the player score.
     * @return This returns the score value of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * This method is setter for the player score.
     * @param score This assigns the score value of the player.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method is getter for the player name.
     * @return This returns the name of the player.
     */
    public String getName() {
        return name;
    }


    /**
     * This method is getter for the player color.
     * @return This returns the color of the player.
     */
    public String getColor() {
        return color;
    }

    /**
     * This method is used to add score to the player score.
     * @param number The value to be added to the score.
     */
    public void addScore(int number) {
        if (number > 0) {
            this.score = this.score + number;
        } else {
            System.out.println("Illegal score");
        }
    }

    /**
     * This method is used to remove score from the player score.
     * @param number The value to be removed from the score.
     */
    public void removeScore(int number) {
        if (number > 0) {
            if (this.score - number > 0) {
                this.score = this.score - number;
            } else {
                this.score = 0;
            }
        } else {
            System.out.println("Illegal score");
        }
    }
}
