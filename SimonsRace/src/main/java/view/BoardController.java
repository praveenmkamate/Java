package view;

import Board.*;
import Board.Dice;
import Board.Dice.*;
import Board.Player;
import Controller.Game;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static Controller.Game.checkWinningCondition;
import static Controller.Game.makeAMove;

public class BoardController {


    @FXML
    SplitPane splitPane;
    @FXML
    AnchorPane anchorPane;

    @FXML
    GridPane boardGridPane;

    @FXML
    Text diceDisplay;

    @FXML
    Text playerName;

    @FXML
    Button rollDice;

    @FXML
    Button leftButton;

    @FXML
    Button forwardButton;

    @FXML
    Button rightButton;

    @FXML
    Button backButton;

    @FXML
    Button missButton;

    @FXML
    Text playerTurn;

    @FXML
    TextArea displayInformation;
    public static List<Player> playerList = new ArrayList<>();

    Directions returnDirection = null;
    int gridSize;

    public static boolean isWin = false;
    public void initializeBoard(int gSize, int noOfPlayers, List<String> playerNames) throws NoSuchMethodException, URISyntaxException {

        gridSize = gSize;
        double gridWidth = splitPane.getPrefWidth() * 0.75;
        double gridHeight = splitPane.getPrefHeight();

        boardGridPane.setPrefWidth(gridWidth);
        boardGridPane.setPrefHeight(gridHeight);

        double stackPaneWidth = gridWidth / gridSize;
        double stackPaneHeight = gridHeight / gridSize;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col ++) {
                StackPane square = new StackPane();
                String color ;
                if(row == 0){
                    color = "#75e682";
                } else if ((row + col) % 2 == 0) {
                    color = "DeepSkyBlue";
                } else {
                    color = "MintCream";
                }
                square.setStyle("-fx-background-color: "+color+";");
                square.setId(String.valueOf(row)+String.valueOf(col));
                square.setPrefWidth(stackPaneWidth);
                square.setPrefHeight(stackPaneHeight);
                boardGridPane.add(square, col, row);
            }
        }

        playerList = Game.InitializeBoard(gridSize, noOfPlayers, playerNames, this);

        disableDirectionButtons();

        playerTurn.setText(playerList.get(0).getName());

        setDisplayInformation("Let's Start the Game. Please roll the dice.");
    }

    static int playerCount = 0;
    public void rollDice() throws IOException, InterruptedException {

        Dice dice = new Dice();
        Player currentPlayer;
        Player nextPlayer;

        int count;
        boolean result = false;
        Directions directions;


        if(playerCount >= playerList.size())
            playerCount = 0;

        currentPlayer = playerList.get(playerCount);
        playerCount++;

        count = dice.generateCount();
        directions = dice.generateDirection();

        setPlayerName("Player: "+currentPlayer.getName());
        setDiceDisplay("Count: "+count+" Direction: "+directions.toString());
        makeAMove(count, directions, this,currentPlayer);

        if(isWin == true || checkWinningCondition(currentPlayer) == true){
            //wait here
            winnerScreen(currentPlayer);
        }
        if(playerCount >= playerList.size())
            playerCount = 0;

        nextPlayer = playerList.get(playerCount);

        playerTurn.setText(nextPlayer.getName());

    }

    public void winnerScreen(Player currentPlayer) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winScreen.fxml"));
        Parent winScreen = fxmlLoader.load();

        WinController winController = fxmlLoader.getController();
        winController.receiveData(currentPlayer);

        Scene scene = new Scene(winScreen);

        Stage stage = (Stage) rollDice.getScene().getWindow();
        stage.setTitle("Winner Information");
        stage.setUserData(gridSize);
        stage.setScene(scene);
        stage.show();
    }

    public void setObject(int row, int col, String path) throws URISyntaxException {
        StackPane square = (StackPane) boardGridPane.lookup("#"+String.valueOf(row)+String.valueOf(col));
        Image image = new Image(getClass().getResourceAsStream(path),square.getPrefHeight(),square.getPrefWidth(),false,false);
        square.getChildren().add(new ImageView(image));
    }

    public void onClickForwardButton(){
        Platform.exitNestedEventLoop(returnDirection,Directions.FORWARD);
    }

    public void onClickBackwardButton(){
        Platform.exitNestedEventLoop(returnDirection,Directions.BACKWARD);
    }

    public void onClickLeftButton(){
        Platform.exitNestedEventLoop(returnDirection,Directions.LEFT);
    }

    public void onClickRightButton(){
        Platform.exitNestedEventLoop(returnDirection,Directions.RIGHT);
    }

    public void onClickMissButton(){
        Platform.exitNestedEventLoop(returnDirection,Directions.MISS);
    }

    public Directions getDirection(Player player, Board board, Directions direction){

        rollDice.setDisable(true);
        enableDirectionButtons();
        returnDirection = direction;
        switch (direction) {
            case FORWARD:
                forwardButton.setDisable(true);
                checkLeft(board,player);
                checkRight(board,player);
                checkBack(board,player);
                break;
            case BACKWARD:
                backButton.setDisable(true);
                checkFront(board,player);
                checkLeft(board,player);
                checkRight(board,player);
                break;
            case RIGHT:
                rightButton.setDisable(true);
                checkFront(board,player);
                checkLeft(board,player);
                checkBack(board,player);
                break;
            case LEFT:
                leftButton.setDisable(true);
                checkFront(board,player);
                checkRight(board,player);
                checkBack(board,player);
                break;
        }
        returnDirection = (Directions) Platform.enterNestedEventLoop(returnDirection);
        disableDirectionButtons();
        rollDice.setDisable(false);
        return returnDirection;
    }

    public void enableDirectionButtons(){
        missButton.setDisable(false);
        forwardButton.setDisable(false);
        backButton.setDisable(false);
        leftButton.setDisable(false);
        rightButton.setDisable(false);
    }

    public void disableDirectionButtons(){
        missButton.setDisable(true);
        forwardButton.setDisable(true);
        backButton.setDisable(true);
        leftButton.setDisable(true);
        rightButton.setDisable(true);
    }

    public void checkFront(Board board,Player player){

        if (board.getBoardCell(player.getRowLocation() - 1, player.getColLocation()) != null) {
            if (board.getBoardCell(player.getRowLocation() - 1, player.getColLocation()).getPlayer() != null) {
                forwardButton.setDisable(true);
            } else if ((board.getBoardCell(player.getRowLocation() - 1, player.getColLocation()).getObstacle().getType() == Obstacle.ObstacleType.PILLAR)) {
                forwardButton.setDisable(true);
            }
        }
    }

    public void checkBack(Board board,Player player){
        if(player.getRowLocation() + 1 > gridSize - 1) {
            backButton.setDisable(true);
        } else if (board.getBoardCell(player.getRowLocation() + 1, player.getColLocation()) != null) {
            if (board.getBoardCell(player.getRowLocation() + 1, player.getColLocation()).getPlayer() != null) {
                backButton.setDisable(true);

            } else if ((board.getBoardCell(player.getRowLocation() + 1, player.getColLocation()).getObstacle().getType() == Obstacle.ObstacleType.PILLAR)) {
                backButton.setDisable(true);
            }
        }
    }

    public void checkLeft(Board board,Player player){
        if (player.getColLocation() - 1 < 0){
            leftButton.setDisable(true);
        } else if (board.getBoardCell(player.getRowLocation(), player.getColLocation()-1) != null) {
            if (board.getBoardCell(player.getRowLocation(), player.getColLocation()-1).getPlayer() != null) {
                leftButton.setDisable(true);
            } else if ((board.getBoardCell(player.getRowLocation() , player.getColLocation()-1).getObstacle().getType() == Obstacle.ObstacleType.PILLAR)) {
                leftButton.setDisable(true);
            }
        }
    }

    public void checkRight(Board board,Player player){
        if (player.getColLocation() + 1 > gridSize - 1) {
            rightButton.setDisable(true);
        }else if (board.getBoardCell(player.getRowLocation(), player.getColLocation()+1) != null) {
            if (board.getBoardCell(player.getRowLocation(), player.getColLocation()+1).getPlayer() != null) {
                rightButton.setDisable(true);
            } else if ((board.getBoardCell(player.getRowLocation() , player.getColLocation()+1).getObstacle().getType() == Obstacle.ObstacleType.PILLAR)) {
                rightButton.setDisable(true);
            }
        }
    }

    public void removeObject(int row, int col) throws URISyntaxException {
        StackPane square = (StackPane) boardGridPane.lookup("#"+String.valueOf(row)+String.valueOf(col));
        square.getChildren().clear();
    }

    public void setPlayerName(String s){
        playerName.setText(s);
        playerName.setTextAlignment(TextAlignment.CENTER);
    }

    public void setDiceDisplay(String s){
        diceDisplay.setText(s);
        diceDisplay.setTextAlignment(TextAlignment.CENTER);
    }

    public void setDisplayInformation(String s){
        displayInformation.setText(s);
    }

}
