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

    @FXML
    TextArea scoreArea;
    public static List<Player> playerList = new ArrayList<>();

    Directions returnDirection = null;
    int gridSize;


    public void initializeBoard(int gSize, int noOfPlayers, List<String> playerNames, Map<String, String> playerColor, Map<String, Integer> playerLane) throws URISyntaxException {

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

        playerList = Game.initializeBoard(gridSize, noOfPlayers, playerNames, this, playerColor, playerLane);

        disableDirectionButtons();

        playerTurn.setText(playerList.get(0).getName());

        setDisplayInformation("Let's Start the Game. Please roll the dice.");
    }

    static int playerCount = 0;
    public void rollDice() throws IOException, ClassNotFoundException {

        Dice dice = new Dice();
        Player currentPlayer;
        Player nextPlayer;

        int count;
        Directions directions;

        if(playerCount >= playerList.size())
            playerCount = 0;

        currentPlayer = playerList.get(playerCount);
        playerCount++;

        count = dice.generateCount();
        directions = dice.generateDirection();

        currentPlayer.setScore(calculateScore(currentPlayer.getScore(),count,directions));
        setPlayerName("Previous Player: "+currentPlayer.getName());
        setDiceDisplay("Count: "+count+" Direction: "+directions.toString());
        if (currentPlayer.isMissNextTurn()) {
            setDisplayInformation("ICE Effect! Missed Turn.");
            currentPlayer.setMissNextTurn(false);
            currentPlayer.removeScore(10);
        } else {
            Game.duplicateCount = count;
            makeAMove(count, directions, this,currentPlayer);
        }

        displayScoreArea(playerList);

        if(currentPlayer.getRowLocation() == 0){
            currentPlayer.addScore(gridSize*50);
            winnerScreen(currentPlayer);
        } else {
            if(playerCount >= playerList.size())
                playerCount = 0;

            nextPlayer = playerList.get(playerCount);

            playerTurn.setText(nextPlayer.getName());
        }


    }

    private void displayScoreArea(List<Player> playerList) {
        Player currentPlayer;
        String score="";
        for(int i=0;i<playerList.size();i++){
            currentPlayer = playerList.get(i);
            score+= currentPlayer.getName() +": "+ currentPlayer.getScore()+"\n";
        }
        scoreArea.setText(score);
    }

    private int calculateScore(int score, int count, Directions directions) {
        if(directions == FORWARD){
            score += (count*2);
        } else if(directions == Directions.BACKWARD){
            score -= (count);
        } else if(directions == Directions.LEFT){
            score += (count);
        } else if (directions == Directions.RIGHT){
            score += (count);
        } else {
            score += 0;
        }
        return score;
    }

    public void winnerScreen(Player currentPlayer) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winScreen.fxml"));
        Parent winScreen = fxmlLoader.load();

        WinController winController = fxmlLoader.getController();
        winController.receiveData(currentPlayer,playerList);

        Scene scene = new Scene(winScreen);

        Stage stage = (Stage) rollDice.getScene().getWindow();
        stage.setTitle("Winner Information");
        stage.setX(200);
        stage.setY(50);
        stage.setScene(scene);
        stage.show();
    }

    public void setObject(int row, int col, String path) {
        StackPane square = (StackPane) boardGridPane.lookup("#"+String.valueOf(row)+String.valueOf(col));
        Image image = new Image(getClass().getResourceAsStream(path),square.getPrefHeight(),square.getPrefWidth(),false,false);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(square.getPrefHeight());
        imageView.setFitWidth(square.getPrefWidth());
        square.getChildren().clear();
        square.getChildren().add(imageView);
    }

    public void onClickForwardButton(){
        Platform.exitNestedEventLoop(returnDirection, FORWARD);
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
        setDisplayInformation("Obstacle! Select which direction you want to proceed.");
        returnDirection = (Directions) Platform.enterNestedEventLoop(returnDirection);
        disableDirectionButtons();
        rollDice.setDisable(false);
        setDisplayInformation(" ");
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
            } else if ((board.getBoardCell(player.getRowLocation() - 1, player.getColLocation()).getObstacleType() == PILLAR)) {
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


            } else if ((board.getBoardCell(player.getRowLocation() + 1, player.getColLocation()).getObstacleType() == PILLAR)) {
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
            } else if ((board.getBoardCell(player.getRowLocation() , player.getColLocation()-1).getObstacleType() == PILLAR)) {
                leftButton.setDisable(true);
            }
        }
    }

    public void checkRight(Board board, Player player){
        if (player.getColLocation() + 1 > gridSize - 1) {
            rightButton.setDisable(true);
        }else if (board.getBoardCell(player.getRowLocation(), player.getColLocation()+1) != null) {
            if (board.getBoardCell(player.getRowLocation(), player.getColLocation()+1).getPlayer() != null) {
                rightButton.setDisable(true);
            } else if ((board.getBoardCell(player.getRowLocation() , player.getColLocation()+1).getObstacleType() == PILLAR)) {
                rightButton.setDisable(true);
            }
        }
    }

    public void removeObject(int row, int col) {
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
