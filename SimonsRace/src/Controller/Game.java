package Controller;

import Board.*;
import Board.Dice.*;
import Board.Obstacle.*;
import Board.Player.*;

import java.io.*;
import java.util.*;

import static Board.Dice.Directions.*;

public class Game {

    public static int rows = 8;
    public static int columns = 9;

    public static boolean isWin = false;

    static List<Player> playerList = new ArrayList<>();
    static Map<String, Integer> score = new HashMap<>();

    static Board board;

    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        int checkCount;
        boolean edgeCase;

        board = new Board(rows, columns);


        // Creating Player objects
        Player player1 = new Player("Bharath", "B");
        Player player2 = new Player("Nithin", "N");
        Player player3 = new Player("PillarPlayer", "PP");

//        score.put(player1.getName(),100);
//        score.put(player2.getName(),5);
//
//        UpdateScoreboardToFile();

        // Creating Pillar obstacle objects
        //Obstacle pillar = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle ice = new Obstacle("ICE", "I", ObstacleType.ICE);

        // Initialize the Obstacles
        for (int row = 3; row <= 6; row++) {
            for (int col = 1; col <= 9; col++) {
                InitializeObstacle(board, ice, row, col);
            }
        }

        // Initialize the Players
        InitializePlayer(board, player1, rows, 2);
        InitializePlayer(board, player2, rows, 3);

        playerList.remove(player3);
        board.printBoard();
        System.out.println("Starting the Game!");

        Dice dice = new Dice();

        while (!isWin) {
            for (int i = 0; i < playerList.size(); i++) {
                // Check for win
                if (isWin) {
                    break;
                } else {
                    Player currentPlayer = playerList.get(i);
                    System.out.println("Turn: " + currentPlayer.getName());

                    // Generate the Dice values
                    int count = dice.generateCount();
                    Directions direction = dice.generateDirection();
                    System.out.println("DICE - Count:" + count + " Direction:" + direction);

                    checkCount = count;
                    edgeCase = false;
                    String getDirection;


                    // Check for the edge cases
                    while (checkCount > 0 && edgeCase == false) {
                        if (currentPlayer.isMissNextTurn()) {
                            System.out.println("ICE Effect! Missed Turn.");
                            currentPlayer.setMissNextTurn(false);
                            break;
                        }
                        switch (direction) {
                            case FORWARD:
                                // Edge Case: In last turn, if the player doesn't get correct number
                                if (currentPlayer.getRowLocation() - checkCount < 0) {
                                    System.out.println("Almost there. Try Again!");
                                    edgeCase = true;
                                    break;
                                } else {
                                    // Check if the next is null before moving
                                    if (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setRowLocation(currentPlayer.getRowLocation() - 1);
                                        board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                        checkCount--;

                                        // Check if the player won
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }
                                        break;
                                        // Check if the next cell has a Pillar or Player
                                    } else if ((board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle() != null) || (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getPlayer() != null)) {
                                        if (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getPlayer() != null) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if ((board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.PILLAR)) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if ((board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.ICE)) {
                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            //board.setBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),new BoardCell(ice));
                                            currentPlayer.setRowLocation(currentPlayer.getRowLocation() - 1);
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;

                                            if (checkCount == 0) {
                                                currentPlayer.setMissNextTurn(true);
                                            }
                                            break;
                                        }

                                    }
                                }

                            case BACKWARD:
                                // Edge Case: if the players tries to go out of the board
                                if (currentPlayer.getRowLocation() + 1 > rows - 1) {
                                    System.out.println("Sorry, You cannot go outside the board!");
                                    edgeCase = true;
                                    break;
                                } else {
                                    // Check if the next cell is null before moving
                                    if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setRowLocation(currentPlayer.getRowLocation() + 1);
                                        board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                        checkCount--;

                                        // Check if the player won
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }
                                        break;

                                        // Check if the next cell has a Pillar
                                    } else if ((board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle() != null) ||
                                            (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getPlayer() != null)) {
                                        if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getPlayer() != null) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.PILLAR) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.ICE) {
                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            currentPlayer.setRowLocation(currentPlayer.getRowLocation() + 1);
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;

                                            if (checkCount == 0) {
                                                currentPlayer.setMissNextTurn(true);
                                            }
                                            break;

                                        }
                                    }

                                }
                            case RIGHT:
                                // Edge Case: Check if the player is on right edge of the board
                                if (currentPlayer.getColLocation() + 1 > columns - 1) {
                                    System.out.println("End of Board! Select FORWARD or BACKWARD or MISS");
                                    getDirection = input.nextLine();
                                    direction = getDirectionValue(getDirection);
                                    break;
                                } else {
                                    // Check if the next cell is null before moving
                                    if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setColLocation(currentPlayer.getColLocation() + 1);
                                        board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                        checkCount--;

                                        // Check if the player won
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }
                                        break;
                                        // Check if the next cell has a Pillar
                                    } else if ((board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle() != null) ||
                                            (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getPlayer() != null)) {
                                        if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getPlayer() != null) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle().getType() == ObstacleType.PILLAR) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle().getType() == ObstacleType.ICE) {
                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            currentPlayer.setColLocation(currentPlayer.getColLocation() + 1);
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;

                                            if (checkCount == 0) {
                                                currentPlayer.setMissNextTurn(true);
                                            }
                                            break;
                                        }

                                    }

                                }
                            case LEFT:
                                if (currentPlayer.getColLocation() - 1 < 0) {
                                    System.out.println("End of Board! Select FORWARD or BACKWARD");
                                    getDirection = input.nextLine();
                                    direction = getDirectionValue(getDirection);
                                    break;
                                } else {
                                    // Check if the next cell is null before moving
                                    if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1) == null) {
                                        board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                        currentPlayer.setColLocation(currentPlayer.getColLocation() - 1);
                                        board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                        checkCount--;

                                        // Check if the player won
                                        if (checkWinningCondition(currentPlayer)) {
                                            isWin = true;
                                            break;
                                        }

                                        break;
                                        // Check if the next cell has a Pillar
                                    } else if ((board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle() != null) ||
                                            (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getPlayer() != null)) {
                                        if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getPlayer() != null) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle().getType() == ObstacleType.PILLAR) {
                                            direction = Pillar(currentPlayer, direction);
                                            break;
                                        } else if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle().getType() == ObstacleType.ICE) {
                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            currentPlayer.setColLocation(currentPlayer.getColLocation() - 1);
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;

                                            if (checkCount == 0) {
                                                currentPlayer.setMissNextTurn(true);
                                            }
                                            break;
                                        }
                                    }
                                }
                            case MISS:
                                edgeCase = true;
                                break;
                        }
                    }
                    board.printBoard();
                }

            }
        }

    }

    public static Directions Pillar(Player player, Directions direction) {
        Scanner input = new Scanner(System.in);
        String getDirection;
        switch (direction) {
            case FORWARD:
            case BACKWARD:
                if (player.getColLocation() - 1 < 0) {
                    System.out.println("End of Board! Select RIGHT(R) or MISS(M)");
                    getDirection = input.nextLine();
                    direction = getDirectionValue(getDirection);
                } else if (player.getColLocation() + 1 > columns - 1) {
                    System.out.println("End of Board! Select LEFT(L) or MISS(M)");
                    getDirection = input.nextLine();
                    direction = getDirectionValue(getDirection);
                } else {
                    System.out.println("Obstacle! Select LEFT(L) or RIGHT(R) or MISS(M)");
                    getDirection = input.nextLine();
                    direction = getDirectionValue(getDirection);
                }
                return direction;
            case RIGHT:
                System.out.println("Obstacle! Select FORWARD or BACKWARD or MISS");
                getDirection = input.nextLine();
                direction = getDirectionValue(getDirection);
                return direction;
            case LEFT:
                System.out.println("Obstacle! Select FORWARD or BACKWARD");
                getDirection = input.nextLine();
                direction = getDirectionValue(getDirection);
                return direction;
        }
        return null;
    }

    public static Directions getDirectionValue(String direction) {
        Directions tempDirection;

        switch (direction) {
            case "F":
                tempDirection = FORWARD;
                break;
            case "B":
                tempDirection = BACKWARD;
                break;
            case "R":
                tempDirection = RIGHT;
                break;
            case "L":
                tempDirection = LEFT;
                break;
            default:
                System.out.println("Illegal Character! Considering it as a MISS");
                tempDirection = MISS;
                break;
        }

        return tempDirection;
    }

    public static boolean checkWinningCondition(Player currentPlayer) {
        if (currentPlayer.getRowLocation() == 0) {
            System.out.println(currentPlayer.getName() + " won!");
            return true;
        } else {
            return false;
        }
    }

    public static void InitializePlayer(Board board, Player player, int row, int column) {
        board.setPlayerOnBoard(row - 1, column - 1, player);
        player.setLocation(row - 1, column - 1);
        Game.playerList.add(player);
    }

    public static void InitializeObstacle(Board board, Obstacle obstacle, int row, int column) {
        board.setObstacleOnBoard(row - 1, column - 1, obstacle);
        obstacle.setLocation(row - 1, column - 1);
    }

    public static void UpdateScoreboardToFile() {
        File file = new File("ScoreBoard.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Couldn't create Scorecard!");
            }
        }

        Writer output;
        try {
            output = new BufferedWriter(new FileWriter("ScoreBoard.txt"));
            for (Map.Entry<String, Integer> entry : score.entrySet()) {
                output.append(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Scoreboard updated!");


    }


}
