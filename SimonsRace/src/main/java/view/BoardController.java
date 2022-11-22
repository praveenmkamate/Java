package view;

import Board.Dice;
import Board.Dice.*;
import Board.Player;
import Controller.Game;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Player> playerList = new ArrayList<>();

    int gridSize;
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

    }

    static int playerCount = 0;
    public void rollDice(){
        Dice dice = new Dice();
        Player currentPlayer;

        int count;
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


    }

    public void setObject(int row, int col, String path) throws URISyntaxException {
        StackPane square = (StackPane) boardGridPane.lookup("#"+String.valueOf(row)+String.valueOf(col));
        Image image = new Image(getClass().getResourceAsStream(path),square.getPrefHeight(),square.getPrefWidth(),false,false);
        square.getChildren().add(new ImageView(image));
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

}
