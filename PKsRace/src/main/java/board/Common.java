package board;
import board.Dice.*;

public class Common {
    public enum ObstacleType {
        PILLAR,
        ICE,
        DANGER,
        SANTA,
        PIRATE
    }

    public static int getRowValue(int row, Directions direction){
        switch (direction){
            case FORWARD:
                return row-1;
            case BACKWARD:
                return row+1;
            default:
                return -1;
        }
    }

    public static int getColValue(int col, Directions direction){
        switch (direction){
            case LEFT:
                return col-1;
            case RIGHT:
                return col+1;
            default:
                return -1;
        }
    }

    public static int getDifficultyValue(String difficulty){
        if(difficulty.equalsIgnoreCase("Easy")){
            return 4;
        } else if(difficulty.equalsIgnoreCase("Medium")){
            return 3;
        } else if(difficulty.equalsIgnoreCase("Hard")){
            return 2;
        } else {
            throw new RuntimeException("Not a Valid Difficulty");
        }
    }

    public static String getPlayerIconPath(String color) {
        switch (color) {
            case "Black":
                return "/Images/Player/black.png";
            case "Blue":
                return "/Images/Player/blue.png";
            case "Green":
                return "/Images/Player/green.png";
            case "Neon":
                return "/Images/Player/neon.png";
            case "Orange":
                return "/Images/Player/orange.png";
            case "Pink":
                return "/Images/Player/pink.png";
            case "Purple":
                return "/Images/Player/purple.png";
            case "Red":
                return "/Images/Player/red.png";
            case "White":
                return "/Images/Player/white.png";
            case "Yellow":
                return "/Images/Player/yellow.png";
        }
        return null;
    }

    public static String getObstacleIconPath(ObstacleType obstacleType){
        switch (obstacleType) {
            case PILLAR:
                return "/Images/Obstacle/pillar.png";
            case DANGER:
                return "/Images/Obstacle/danger.png";
            case ICE:
                return "/Images/Obstacle/ice.png";
            case SANTA:
                return "/Images/Obstacle/santa.png";
            case PIRATE:
                return "/Images/Obstacle/pirate.png";
            default:
                return "";
        }
    }

    public static String getObstacleChangeIconPath(ObstacleType obstacleType){
        switch (obstacleType) {
            case ICE:
                return "/Images/Obstacle/playerIce.png";
            case SANTA:
                return "/Images/Obstacle/happy.png";
            case PIRATE:
                return "/Images/Obstacle/sad.png";
            default:
                return null;
        }
    }




}
