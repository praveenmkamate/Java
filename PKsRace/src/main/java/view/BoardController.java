package view;

import board.Board;
import board.Dice;
import board.Dice.Directions;
import board.Player;
import controller.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static board.Common.ObstacleType.PILLAR;
import static board.Dice.Directions.FORWARD;
import static controller.Game.makeAMove;

/**
 * This class is used to display and control the movements on the Java FX front end of the board.
 */
public class BoardController {

    /**
     * This is used to create two separate panes on the board.
     */
    @FXML
    SplitPane splitPane;
    /**
     * This is used to place various elements on the JavaFX front end pane.
     */
    @FXML
    AnchorPane anchorPane;

    /**
     * This is used to create the two-dimensional board on the JavaFX front end.
     */
    @FXML
    GridPane boardGridPane;

    /**
     * This is used to display the values on the dice.
     */
    @FXML
    Text diceDisplay;

    /**
     * This is used to display the player name.
     */
    @FXML
    Text playerName;

    /**
     * This button is used to roll the dice.
     */
    @FXML
    Button rollDice;

    /**
     * This button is used to take in direction input from the user.
     */
    @FXML
    Button leftButton;

    /**
     * This button is used to take in direction input from the user.
     */
    @FXML
    Button forwardButton;

    /**
     * This button is used to take in direction input from the user.
     */
    @FXML
    Button rightButton;

    /**
     * This button is used to take in direction input from the user.
     */
    @FXML
    Button backButton;

    /**
     * This button is used to take in direction input from the user.
     */
    @FXML
    Button missButton;

    /**
     * This displays the current player who has to roll the dice.
     */
    @FXML
    Text playerTurn;

    /**
     * This is used to display various information to user while game is in progress.
     */
    @FXML
    TextArea displayInformation;

    /**
     * This is used to display the current scores of all the players in the current game.
     */
    @FXML
    TextArea scoreArea;
    /**
     * This is used to store the list of players playing the game.
     */
    public static List<Player> playerList = new ArrayList<>();

    /**
     * This is used when we take the direction input from the user.
     */
    Directions returnDirection = null;
    /**
     * This is used to store the grid size of the board.
     */
    int gridSize;


    /**
     * This method is used to create the initial board on Java FX front end.
     * @param gSize       This represents the grid size of the board.
     * @param noOfPlayers This represents the number of players in the current game.
     * @param playerNames This represents the player names in the current game.
     * @param playerColor This represents the player colors in the current game.
     * @param playerLane  This represents the starting lanes of the players in the current game.
     */
    public void initializeBoard(int gSize, int noOfPlayers, List<String> playerNames, Map<String, String> playerColor, Map<String, Integer> playerLane) {

        gridSize = gSize;
        double gridWidth = splitPane.getPrefWidth() * 0.75;
        double gridHeight = splitPane.getPrefHeight();

        boardGridPane.setPrefWidth(gridWidth);
        boardGridPane.setPrefHeight(gridHeight);

        double stackPaneWidth = gridWidth / gridSize;
        double stackPaneHeight = gridHeight / gridSize;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                StackPane square = new StackPane();
                String color;
                if (row == 0) {
                    color = "#75e682";
                } else if ((row + col) % 2 == 0) {
                    color = "DeepSkyBlue";
                } else {
                    color = "MintCream";
                }
                square.setStyle("-fx-background-color: " + color + ";");
                square.setId(String.valueOf(row) + String.valueOf(col));
                square.setPrefWidth(stackPaneWidth);
                square.setPrefHeight(stackPaneHeight);
                boardGridPane.add(square, col, row);
            }
        }

        try {
            playerList = Game.initializeBoard(gridSize, noOfPlayers, playerNames, this, playerColor, playerLane);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        disableDirectionButtons();

        playerTurn.setText(playerList.get(0).getName());

        setDisplayInformation("Let's Start the Game. Please roll the dice.");
    }

    /**
     * This is used to select users for each turn to roll the dice.
     */
    static int playerCount = 0;

    /**
     * This is used to roll the dice and move the player.
     *
     */
    public void rollDice() {

        Dice dice = new Dice();
        Player currentPlayer;
        Player nextPlayer;

        int count;
        Directions directions;

        if (playerCount >= playerList.size())
            playerCount = 0;

        currentPlayer = playerList.get(playerCount);
        playerCount++;

        count = dice.generateCount();
        directions = dice.generateDirection();

        currentPlayer.setScore(calculateScore(currentPlayer.getScore(), count, directions));
        setPlayerName("Previous Player: " + currentPlayer.getName());
        setDiceDisplay("Count: " + count + " Direction: " + directions.toString());
        if (currentPlayer.isMissNextTurn()) {
            setDisplayInformation("ICE Effect! Missed Turn.");
            currentPlayer.setMissNextTurn(false);
            currentPlayer.removeScore(10);
        } else {
            Game.duplicateCount = count;
            makeAMove(count, directions, this, currentPlayer);
        }

        displayScoreArea(playerList);

        if (currentPlayer.getRowLocation() == 0) {
            currentPlayer.addScore(gridSize * 50);
            winnerScreen(currentPlayer);
        } else {
            if (playerCount >= playerList.size())
                playerCount = 0;

            nextPlayer = playerList.get(playerCount);

            playerTurn.setText(nextPlayer.getName());
        }


    }

    /**
     * This method is used to display the scores on the text area.
     * @param playerList The list of players currently playing the game.
     */
    private void displayScoreArea(List<Player> playerList) {
        Player currentPlayer;
        String score = "";
        for (int i = 0; i < playerList.size(); i++) {
            currentPlayer = playerList.get(i);
            score += currentPlayer.getName() + ": " + currentPlayer.getScore() + "\n";
        }
        scoreArea.setText(score);
    }

    /**
     * This method is used to calculate the user score after each turn.
     * @param score      The current score of the player.
     * @param count      The current numerical value on dice.
     * @param directions The current directional value on the dice.
     * @return This returns the updated score.
     */
    private int calculateScore(int score, int count, Directions directions) {
        if (directions == FORWARD) {
            score += (count * 2);
        } else if (directions == Directions.BACKWARD) {
            score -= (count);
        } else if (directions == Directions.LEFT) {
            score += (count);
        } else if (directions == Directions.RIGHT) {
            score += (count);
        } else {
            score += 0;
        }
        return score;
    }

    /**
     * This method is used to navigate to the winner page.
     * @param currentPlayer The winner of the game.
     */
    public void winnerScreen(Player currentPlayer) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winScreen.fxml"));
        Parent winScreen = null;
        try {
            winScreen = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WinController winController = fxmlLoader.getController();
        winController.receiveData(currentPlayer, playerList);

        Scene scene = new Scene(winScreen);

        Stage stage = (Stage) rollDice.getScene().getWindow();
        stage.setTitle("Winner Information");
        stage.setX(200);
        stage.setY(50);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to place obstacles, players on the front end of the board.
     * @param row  The value of the row on the board.
     * @param col  The value of the col on the board.
     * @param path The path of the image to be placed on the board.
     */
    public void setObject(int row, int col, String path) {
        StackPane square = (StackPane) boardGridPane.lookup("#" + String.valueOf(row) + String.valueOf(col));
        Image image = new Image(getClass().getResourceAsStream(path), square.getPrefHeight(), square.getPrefWidth(), false, false);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(square.getPrefHeight());
        imageView.setFitWidth(square.getPrefWidth());
        square.getChildren().clear();
        square.getChildren().add(imageView);
    }

    /**
     * This method is called when the forward direction is selected by the player.
     */
    public void onClickForwardButton() {
        Platform.exitNestedEventLoop(returnDirection, FORWARD);
    }

    /**
     * This method is called when the backward direction is selected by the player.
     */
    public void onClickBackwardButton() {
        Platform.exitNestedEventLoop(returnDirection, Directions.BACKWARD);
    }

    /**
     * This method is called when the left direction is selected by the player.
     */
    public void onClickLeftButton() {
        Platform.exitNestedEventLoop(returnDirection, Directions.LEFT);
    }

    /**
     * This method is called when the right direction is selected by the player.
     */
    public void onClickRightButton() {
        Platform.exitNestedEventLoop(returnDirection, Directions.RIGHT);
    }

    /**
     * This method is called when the miss turn is selected by the player.
     */
    public void onClickMissButton() {
        Platform.exitNestedEventLoop(returnDirection, Directions.MISS);
    }

    /**
     * This method is used to get the direction to overcome the obstacle.
     * @param player    The current player playing the turn.
     * @param board     The board object to check other obstacles.
     * @param direction The direction of the player.
     * @return This returns the new direction to navigate the user.
     */
    public Directions getDirection(Player player, Board board, Directions direction) {

        rollDice.setDisable(true);
        enableDirectionButtons();
        returnDirection = direction;
        switch (direction) {
            case FORWARD:
                forwardButton.setDisable(true);
                checkLeft(board, player);
                checkRight(board, player);
                checkBack(board, player);
                break;
            case BACKWARD:
                backButton.setDisable(true);
                checkFront(board, player);
                checkLeft(board, player);
                checkRight(board, player);
                break;
            case RIGHT:
                rightButton.setDisable(true);
                checkFront(board, player);
                checkLeft(board, player);
                checkBack(board, player);
                break;
            case LEFT:
                leftButton.setDisable(true);
                checkFront(board, player);
                checkRight(board, player);
                checkBack(board, player);
                break;
        }
        setDisplayInformation("Obstacle! Select which direction you want to proceed.");
        returnDirection = (Directions) Platform.enterNestedEventLoop(returnDirection);
        disableDirectionButtons();
        rollDice.setDisable(false);
        setDisplayInformation(" ");
        return returnDirection;
    }

    /**
     * This method enables the direction buttons on the front end.
     */
    public void enableDirectionButtons() {
        missButton.setDisable(false);
        forwardButton.setDisable(false);
        backButton.setDisable(false);
        leftButton.setDisable(false);
        rightButton.setDisable(false);
    }

    /**
     * This method disables the direction buttons on the front end.
     */
    public void disableDirectionButtons() {
        missButton.setDisable(true);
        forwardButton.setDisable(true);
        backButton.setDisable(true);
        leftButton.setDisable(true);
        rightButton.setDisable(true);
    }

    /**
     * This method is used to check for obstacles on the front of the player.
     * @param board  The board object to check for obstacles.
     * @param player The player who has the current turn.
     */
    public void checkFront(Board board, Player player) {

        if (board.getBoardCell(player.getRowLocation() - 1, player.getColLocation()) != null) {
            if (board.getBoardCell(player.getRowLocation() - 1, player.getColLocation()).getPlayer() != null) {
                forwardButton.setDisable(true);
            } else if ((board.getBoardCell(player.getRowLocation() - 1, player.getColLocation()).getObstacleType() == PILLAR)) {
                forwardButton.setDisable(true);
            }
        }
    }

    /**
     * This method is used to check for obstacles on the back of the player.
     * @param board  The board object to check for obstacles.
     * @param player The player who has the current turn.
     */
    public void checkBack(Board board, Player player) {
        if (player.getRowLocation() + 1 > gridSize - 1) {
            backButton.setDisable(true);
        } else if (board.getBoardCell(player.getRowLocation() + 1, player.getColLocation()) != null) {
            if (board.getBoardCell(player.getRowLocation() + 1, player.getColLocation()).getPlayer() != null) {
                backButton.setDisable(true);


            } else if ((board.getBoardCell(player.getRowLocation() + 1, player.getColLocation()).getObstacleType() == PILLAR)) {
                backButton.setDisable(true);
            }
        }
    }

    /**
     * This method is used to check for obstacles on the left of the player.
     * @param board  The board object to check for obstacles.
     * @param player The player who has the current turn.
     */
    public void checkLeft(Board board, Player player) {
        if (player.getColLocation() - 1 < 0) {
            leftButton.setDisable(true);
        } else if (board.getBoardCell(player.getRowLocation(), player.getColLocation() - 1) != null) {
            if (board.getBoardCell(player.getRowLocation(), player.getColLocation() - 1).getPlayer() != null) {
                leftButton.setDisable(true);
            } else if ((board.getBoardCell(player.getRowLocation(), player.getColLocation() - 1).getObstacleType() == PILLAR)) {
                leftButton.setDisable(true);
            }
        }
    }

    /**
     * This method is used to check for obstacles on the right of the player.
     * @param board  The board object to check for obstacles.
     * @param player The player who has the current turn.
     */
    public void checkRight(Board board, Player player) {
        if (player.getColLocation() + 1 > gridSize - 1) {
            rightButton.setDisable(true);
        } else if (board.getBoardCell(player.getRowLocation(), player.getColLocation() + 1) != null) {
            if (board.getBoardCell(player.getRowLocation(), player.getColLocation() + 1).getPlayer() != null) {
                rightButton.setDisable(true);
            } else if ((board.getBoardCell(player.getRowLocation(), player.getColLocation() + 1).getObstacleType() == PILLAR)) {
                rightButton.setDisable(true);
            }
        }
    }

    /**
     * This method is used to remove objects on the front end of the board.
     * @param row The row value of the element.
     * @param col The col value of the element.
     */
    public void removeObject(int row, int col) {
        StackPane square = (StackPane) boardGridPane.lookup("#" + String.valueOf(row) + String.valueOf(col));
        square.getChildren().clear();
    }

    /**
     * @param s This is used to set the player name on the front end of the board.
     */
    public void setPlayerName(String s) {
        playerName.setText(s);
        playerName.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * @param s This is used to set the dice value on the front end of the board.
     */
    public void setDiceDisplay(String s) {
        diceDisplay.setText(s);
        diceDisplay.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * @param s This is used to set the information on the front end of the board.
     */
    public void setDisplayInformation(String s) {
        displayInformation.setText(s);
    }

}
