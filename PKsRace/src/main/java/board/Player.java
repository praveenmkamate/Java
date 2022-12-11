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
     * @param rowLocation The row location to be initialized.
     * @param colLocation The column location to be initialized.
     *                    This constructor is used initialized the player object with its location.
     */
    public Player(int rowLocation, int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

    /**
     * @param name  The player name to be initialized.
     * @param color The player color to be initialized.
     *              This constructor is used initialized the player object with its name and color.
     */
    public Player(String name, String color) {
        this.score = 0;
        this.name = name;
        this.color = color;
    }

    /**
     * @param name The player name to be initialized.
     *             This constructor is used initialized the player object with its name.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * @return This returns if the value of missNextTurn.
     * This method is getter for the missNextTurn.
     */
    public boolean isMissNextTurn() {
        return missNextTurn;
    }

    /**
     * @param missNextTurn This is the value of missNextTurn to be updated with.
     *                     This method is setter for the missNextTurn.
     */
    public void setMissNextTurn(boolean missNextTurn) {
        this.missNextTurn = missNextTurn;
    }

    /**
     * @return This returns the row value of the player.
     * This method is getter for the row value of the player.
     */
    public int getRowLocation() {
        return rowLocation;
    }

    /**
     * @param rowLocation This assigns the row value of the player.
     *                    This method is setter for the row value of the player.
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
     * @return This returns the col value of the player.
     * This method is getter for the col value of the player.
     */
    public int getColLocation() {
        return colLocation;
    }

    /**
     * @param colLocation This assigns the col value of the player.
     *                    This method is setter for the col value of the player.
     */
    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    /**
     * @param rowLocation This assigns the row value of the player.
     * @param colLocation This assigns the col value of the player.
     */
    public void setLocation(int rowLocation, int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

    /**
     * @return This returns the score value of the player.
     * This method is getter for the player score.
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score This assigns the score value of the player.
     *              This method is setter for the player score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return This returns the name of the player.
     * This method is getter for the player name.
     */
    public String getName() {
        return name;
    }


    /**
     * @return This returns the color of the player.
     * This method is getter for the player color.
     */
    public String getColor() {
        return color;
    }

    /**
     * @param number The value to be added to the score.
     *               This method is used to add score to the player score.
     */
    public void addScore(int number) {
        if (number > 0) {
            this.score = this.score + number;
        } else {
            System.out.println("Illegal score");
        }
    }

    /**
     * @param number The value to be removed from the score.
     *               This method is used to remove score from the player score.
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
