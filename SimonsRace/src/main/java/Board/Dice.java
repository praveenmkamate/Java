package Board;

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

    //private String[] direct = {"FORWARD","FORWARD","BACKWARD","MISS"};

    private Directions[] directions = new Directions[] {Directions.FORWARD,Directions.FORWARD,Directions.BACKWARD,Directions.MISS};

    public Dice() {
        maxValue = 4;
        minValue = 1;
    }

    public Dice(int minValue, int maxValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public int getCount() {
        return count;
    }

    public Directions getDirection() {
        return direction;
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

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }
}
