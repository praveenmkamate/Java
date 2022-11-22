package Controller;

import Board.*;
import Board.Dice.*;
import view.BoardController;
import Board.Obstacle.*;

import java.net.URISyntaxException;
import java.util.*;

import static Board.Dice.Directions.*;

public class Game {
    public static int gridSize;
    public static boolean isWin = false;

    static List<Player> playerList = new ArrayList<>();

    static Map<Player, Integer> startCell = new HashMap<Player, Integer>();
    static Board board;

    public static List<Player> InitializeBoard(int gSize, int noOfPlayers, List<String> playerNames, BoardController boardController) throws URISyntaxException {

        gridSize = gSize;
        board = new Board(gridSize);

        Player player1 = new Player("RockyBhai", "R");
        Player player2 = new Player("MunnaBhai", "M");

        InitializePlayer(board, player1, gridSize, 2);
        startCell.put(player1, 2);
        InitializePlayer(board, player2, gridSize, 4);
        startCell.put(player2, 4);
        boardController.setObject(player1.getRowLocation(),player1.getColLocation(),"/Images/redPawn.png");
        boardController.setObject(player2.getRowLocation(),player2.getColLocation(),"/Images/grnPawn.png");


        Obstacle fire1 = new Obstacle("FIRE", "F", ObstacleType.FIRE);
        Obstacle fire2 = new Obstacle("FIRE", "F", ObstacleType.FIRE);
        Obstacle fire3 = new Obstacle("FIRE", "F", ObstacleType.FIRE);

        InitializeObstacle(board, fire1, 3, 4);
        InitializeObstacle(board, fire2, 2, 3);
        InitializeObstacle(board, fire3, 5, 2);

        boardController.setObject(fire1.getRowLocation(),fire1.getColLocation(),"/Images/fire.png");
        boardController.setObject(fire2.getRowLocation(),fire2.getColLocation(),"/Images/fire.png");
        boardController.setObject(fire3.getRowLocation(),fire3.getColLocation(),"/Images/fire.png");

        Obstacle pillar1 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle pillar2 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle pillar3 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);
        Obstacle pillar4 = new Obstacle("Pillar", "P", ObstacleType.PILLAR);

        InitializeObstacle(board, pillar1, 2, 4);
        InitializeObstacle(board, pillar2, 3, 3);
        InitializeObstacle(board, pillar3, 2, 2);
        InitializeObstacle(board, pillar4, 2, 5);

        boardController.setObject(pillar1.getRowLocation(),pillar1.getColLocation(),"/Images/pillar.png");
        boardController.setObject(pillar2.getRowLocation(),pillar2.getColLocation(),"/Images/pillar.png");
        boardController.setObject(pillar3.getRowLocation(),pillar3.getColLocation(),"/Images/pillar.png");
        boardController.setObject(pillar4.getRowLocation(),pillar4.getColLocation(),"/Images/pillar.png");

        Obstacle ice1 = new Obstacle("ICE", "I", ObstacleType.ICE);
        Obstacle ice2 = new Obstacle("ICE", "I", ObstacleType.ICE);

        InitializeObstacle(board, ice1, 3, 2);
        InitializeObstacle(board, ice2, 4, 4);

        boardController.setObject(ice1.getRowLocation(),ice1.getColLocation(),"/Images/ice.png");
        boardController.setObject(ice2.getRowLocation(),ice2.getColLocation(),"/Images/ice.png");

        //boardController.setDashboardNotifyText("Starting the Game!");
        return playerList;

    }

    public static void makeAMove(int count, Directions direction, BoardController boardController, Player currentPlayer){
        Scanner input = new Scanner(System.in);
        int checkCount;
        boolean edgeCase;



        /*if(isWin){
            return true;
        } else {*/
            checkCount = count;
            edgeCase = false;
            String getDirection;

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
                                board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                currentPlayer.setRowLocation(currentPlayer.getRowLocation() - 1);
                                board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
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
                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    //board.setBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),new BoardCell(ice));
                                    currentPlayer.setRowLocation(currentPlayer.getRowLocation() - 1);
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
                                    checkCount--;

                                    if (checkCount == 0) {
                                        currentPlayer.setMissNextTurn(true);
                                    }
                                    break;
                                } else if (checkCount == 1 && (board.getBoardCell(currentPlayer.getRowLocation() - 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.FIRE)) {

                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    currentPlayer.setLocation(gridSize-1, startCell.get(currentPlayer));
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
                                    checkCount--;
                                    break;

                                }

                            }
                        }

                    case BACKWARD:
                        // Edge Case: if the players tries to go out of the board
                        if (currentPlayer.getRowLocation() + 1 > gridSize - 1) {
                            System.out.println("Sorry, You cannot go outside the board!");
                            edgeCase = true;
                            break;
                        } else {
                            // Check if the next cell is null before moving
                            if (board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()) == null) {
                                board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                currentPlayer.setRowLocation(currentPlayer.getRowLocation() + 1);
                                board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
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
                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    currentPlayer.setRowLocation(currentPlayer.getRowLocation() + 1);
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
                                    checkCount--;

                                    if (checkCount == 0) {
                                        currentPlayer.setMissNextTurn(true);
                                    }
                                    break;

                                } else if (checkCount == 1 && board.getBoardCell(currentPlayer.getRowLocation() + 1, currentPlayer.getColLocation()).getObstacle().getType() == ObstacleType.FIRE) {
                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    currentPlayer.setLocation(gridSize-1,startCell.get(currentPlayer));
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
                                    checkCount--;
                                    break;
                                }
                            }

                        }
                    case RIGHT:
                        // Edge Case: Check if the player is on right edge of the board
                        if (currentPlayer.getColLocation() + 1 > gridSize - 1) {
                            System.out.println("End of Board! Select FORWARD or BACKWARD or MISS");
                            getDirection = input.nextLine();
                            direction = getDirectionValue(getDirection);
                            break;
                        } else {
                            // Check if the next cell is null before moving
                            if (board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1) == null) {
                                board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                currentPlayer.setColLocation(currentPlayer.getColLocation() + 1);
                                board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
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
                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    currentPlayer.setColLocation(currentPlayer.getColLocation() + 1);
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
                                    checkCount--;

                                    if (checkCount == 0) {
                                        currentPlayer.setMissNextTurn(true);
                                    }
                                    break;
                                } else if (checkCount == 1 && board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() + 1).getObstacle().getType() == ObstacleType.FIRE) {
                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    currentPlayer.setLocation(gridSize-1,startCell.get(currentPlayer));
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
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
                                board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                currentPlayer.setColLocation(currentPlayer.getColLocation() - 1);
                                board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
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
                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    currentPlayer.setColLocation(currentPlayer.getColLocation() - 1);
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
                                    checkCount--;

                                    if (checkCount == 0) {
                                        currentPlayer.setMissNextTurn(true);
                                    }
                                    break;
                                } else if ( checkCount == 1 && board.getBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation() - 1).getObstacle().getType() == ObstacleType.FIRE) {
                                    board.clearBoardCell(currentPlayer.getRowLocation(), currentPlayer.getColLocation(),boardController);
                                    currentPlayer.setLocation(gridSize-1,startCell.get(currentPlayer));
                                    board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer,boardController);
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
        /*}
        return false;*/
    }

    /*public static void startAGame(BoardController boardController){

        while (!isWin) {
            for (int i = 0; i < playerList.size(); i++) {
                // Check for win
                if (isWin) {
                    break;
                } else {
                    Player currentPlayer = playerList.get(i);
                    boardController.setDashboardNotifyText("Turn: " + currentPlayer.getName());

                    // Generate the Dice values
                    int count = dice.generateCount();
                    Directions direction = dice.generateDirection();
                    boardController.setDashboardNotifyText("DICE - Count:" + count + " Direction:" + direction);

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
                                            currentPlayer.setLocation(gridSize-1, startCell.get(currentPlayer));
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;
                                            break;

                                        }

                                    }
                                }

                            case BACKWARD:
                                // Edge Case: if the players tries to go out of the board
                                if (currentPlayer.getRowLocation() + 1 > gridSize - 1) {
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
                                            currentPlayer.setLocation(gridSize-1,startCell.get(currentPlayer));
                                            board.movePlayerOnBoard(currentPlayer.getRowLocation(), currentPlayer.getColLocation(), currentPlayer);
                                            checkCount--;
                                            break;
                                        }
                                    }

                                }
                            case RIGHT:
                                // Edge Case: Check if the player is on right edge of the board
                                if (currentPlayer.getColLocation() + 1 > gridSize - 1) {
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
                                            currentPlayer.setLocation(gridSize-1,startCell.get(currentPlayer));
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
                                            currentPlayer.setLocation(gridSize-1,startCell.get(currentPlayer));
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

    }*/

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
                } else if (player.getColLocation() + 1 > gridSize - 1) {
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
