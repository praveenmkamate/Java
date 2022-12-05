package board;

public class Obstacle {
    String name;
    int rowLocation;
    int colLocation;

    String initial;

    ObstacleType type;

    public enum ObstacleType {
        PILLAR,
        ICE,
        DANGER
    }

    public Obstacle(String name, String initial, ObstacleType type) {
        this.name = name;
        this.initial = initial;
        this.type = type;
    }

    public ObstacleType getType() {
        return type;
    }

    public void setType(ObstacleType type) {
        this.type = type;
    }

    public Obstacle(String name) {
        this.name = name;
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public void setRowLocation(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public void setLocation(int rowLocation,int colLocation) {
        this.rowLocation = rowLocation;
        this.colLocation = colLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
