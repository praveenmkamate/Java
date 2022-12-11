package board;

import static board.Dice.Directions.*;

/**
 * This class is used to create the Dice. It can generate the numerical value of 1,2,3,4 and Directions of FORWARD,BACKWARD,LEFT,RIGHT and MISS.
 */
public class Dice {

    /**
     * The maximum numerical value dice can have.
     */
    private int maxValue;
    /**
     * The minimum numerical value dice can have.
     */
    private int minValue;

    /**
     * The value of count generated randomly on the dice.
     */
    private int count;
    /**
     * The value of direction generated randomly on the dice.
     */
    private Directions direction;

    /**
     * The valid value the directions can have.
     */
    public enum Directions {
        FORWARD,
        BACKWARD,
        RIGHT,
        LEFT,
        MISS
    }

    /**
     * Used to generate direction FORWARD with 50% probability, BACKWARD with 25% probability and MISS with 25% probability.
     */
    private Directions[] directions = new Directions[]{FORWARD, FORWARD, BACKWARD, MISS};

    /**
     * Constructor used to set the maximum value to 4 and minimum value to 1.
     */
    public Dice() {
        maxValue = 4;
        minValue = 1;
    }

    /**
     * Constructor used to set the desired maximum value and the desired minimum value.
     */
    public Dice(int minValue, int maxValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    /**
     * @return Returns the generated random number.
     * This method is used to generate the random number.
     */
    private int generateRandomNumber() {
        return (int) ((Math.random() * (maxValue - minValue + 1)) + minValue);
    }

    /**
     * @return Returns the count value
     * This method is used to generate the numerical value of the Dice.
     */
    public int generateCount() {
        count = generateRandomNumber();
        return count;
    }

    /**
     * @return Returns the direction value.
     * This method is used to generate the direction of the Dice.
     */
    public Directions generateDirection() {
        direction = directions[generateRandomNumber() - 1];
        return direction;
    }

}
