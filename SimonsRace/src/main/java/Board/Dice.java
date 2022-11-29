package board;

public class Dice {

    private int maxValue;
    private int minValue;
    private int count;
    private Directions direction;

    public enum Directions {
        FORWARD,
        BACKWARD,
        RIGHT,
        LEFT,
        MISS
    }

    private Directions[] directions = new Directions[] {Directions.FORWARD,Directions.FORWARD,Directions.BACKWARD,Directions.MISS};

    public Dice() {
        maxValue = 4;
        minValue = 1;
    }

    public Dice(int minValue, int maxValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    private int generateRandomNumber() {
        return (int) ((Math.random() * (maxValue - minValue + 1)) + minValue);
    }

    public int generateCount(){
        count = generateRandomNumber();
        return count;
    }

    public Directions generateDirection(){
        direction = directions[generateRandomNumber()-1];
        return direction;
    }

}
