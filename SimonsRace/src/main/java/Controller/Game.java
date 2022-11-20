package Controller;

import Board.*;
import Board.Dice.*;
import Board.Obstacle.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import static Board.Dice.Directions.*;

public class Game {

    public static int rows = 8;
    public static int columns = 9;

    public static boolean isWin = false;

    static List<Player> playerList = new ArrayList<>();
    static Map<Player, Score> scoreList = new HashMap<>();

    static Map<Player, Integer> startCell = new HashMap<Player, Integer>();
    static Board board;

    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        int checkCount;
        boolean edgeCase;

        board = new Board(rows, columns);


        // Creating Player objects
        Player player1 = new Player("Bharath", "B");
        Player player2 = new Player("Nithin", "N");


//        score.put(player1.getName(),100);
//        score.put(player2.getName(),5);
//
//        UpdateScoreboardToFile();

        // Creating Obstacle objects
        Obstacle pillar = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle ice = new Obstacle("ICE", "I", ObstacleType.ICE);
        Obstacle fire = new Obstacle("FIRE", "F", ObstacleType.FIRE);

        // Initialize the Obstacles

        /*for (int col = 1; col <= 9; col++) {
            InitializeObstacle(board, ice, 3, col);
            col++;
        }*/
        for (int row = 3; row <= 6; row++) {
            for (int col = 1; col <= 9; col++) {
                InitializeObstacle(board, fire, row, col);
            }
        }


        // Initialize the Players
        InitializePlayer(board, player1, rows, 2);
        startCell.put(player1, 2);
        InitializePlayer(board, player2, rows, 3);
        startCell.put(player2, 3);

        scoreList = InitializeScore(playerList);

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

//                    scoreList.get(currentPlayer).addScore(10);
//                    scoreList.get(currentPlayer).removeScore(5);

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
                                        } else if (checkCount == 1 && (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.FIRE)) {

                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            currentPlayer.setLocation(rows-1, startCell.get(currentPlayer));
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;
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

                                        } else if (checkCount == 1 && board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.FIRE) {
                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            currentPlayer.setLocation(rows-1,startCell.get(currentPlayer));
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;
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
                                        } else if (checkCount == 1 && board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle().getType() == ObstacleType.FIRE) {
                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            currentPlayer.setLocation(rows-1,startCell.get(currentPlayer));
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;
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
                                        } else if ( checkCount == 1 && board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle().getType() == ObstacleType.FIRE) {
                                            board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation());
                                            currentPlayer.setLocation(rows-1,startCell.get(currentPlayer));
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;
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

    public static Map<Player, Score> InitializeScore(List<Player> playerList) {
        Map<Player, Score> scoreList = new HashMap<>();
        for (int i = 0; i < playerList.size(); i++) {
            Score score = new Score();
            scoreList.put(playerList.get(i), score);
        }
        return scoreList;
    }

    public static void UpdateScoreboardToFile(Map<Player, Score> scoreList) {


//        try {
//            ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(new
//                    File("cashRegister.obj")));
//            objectStream.writeObject(rs);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        /*File file = new File("ScoreBoard.txt");

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

        System.out.println("Scoreboard updated!");*/


    }


}
