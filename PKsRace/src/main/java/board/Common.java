package board;

import board.Dice.*;

/**
 * This class contains methods which are commonly accessed across the other classes.
 */
public class Common {
    /**
     * This used to control the type of obstacles we can create.
     */
    public enum ObstacleType {
        /**
         * This is the pillar obstacle type.
         */
        PILLAR,
        /**
         * This is the ice obstacle type.
         */
        ICE,
        /**
         * This is the danger obstacle type.
         */
        DANGER,
        /**
         * This is the santa obstacle type.
         */
        SANTA,
        /**
         * This is the pirate obstacle type.
         */
        PIRATE
    }

    /**
     * This method is used to get the row value.
     * @param row       The row value whose next value of row has to be calculated.
     * @param direction The direction in which the player has to move.
     * @return This returns the next value of row in which the player has to be moved to.
     */
    public static int getRowValue(int row, Directions direction) {
        switch (direction) {
            case FORWARD:
                return row - 1;
            case BACKWARD:
                return row + 1;
            default:
                return -1;
        }
    }

    /**
     * This is used to get column value.
     * @param col       The col value whose next value of col has to be calculated.
     * @param direction The direction in which the player has to move.
     * @return This returns the next value of col in which the player has to be moved to.
     */
    public static int getColValue(int col, Directions direction) {
        switch (direction) {
            case LEFT:
                return col - 1;
            case RIGHT:
                return col + 1;
            default:
                return -1;
        }
    }

    /**
     * This method gets the difficulty value to place obstacles.
     * @param difficulty The difficulty of the game selected by the user.
     * @return Returns the difficulty value for corresponding difficulty.
     */
    public static int getDifficultyValue(String difficulty) {
        if (difficulty.equalsIgnoreCase("Easy")) {
            return 4;
        } else if (difficulty.equalsIgnoreCase("Medium")) {
            return 3;
        } else if (difficulty.equalsIgnoreCase("Hard")) {
            return 2;
        } else {
            throw new RuntimeException("Not a Valid Difficulty");
        }
    }


    /**
     * This method is used to get the icon path for players.
     * @param color The color selected by the Player.
     * @return This returns the icon path of the player for corresponding colour.
     */
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

    /**
     * This method is used to the obstacle icons.
     * @param obstacleType This represents the type of obstacle.
     * @return This returns the icon path for the obstacle.
     */
    public static String getObstacleIconPath(ObstacleType obstacleType) {
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

    /**
     * This method is used to get updated icon for obstacle.
     * @param obstacleType This represents the type of obstacle.
     * @return This returns the icon path for changed icon of the obstacle.
     */
    public static String getObstacleChangeIconPath(ObstacleType obstacleType) {
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
