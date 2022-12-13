package view;

import board.Player;
//import board.Score;
import board.Score;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the screen on which winner is displayed.
 */
public class WinController {
    /**
     * This is used to display the winner
     */
    @FXML
    Text winText;

    /**
     * This is used to display the scores.
     */
    @FXML
    Text scoreText;

    /**
     * This is the anchor pane on which rest of the elements are placed.
     */
    @FXML
    AnchorPane winPane;

    /**
     * The gif on the winner page.
     */
    @FXML
    ImageView winnerImage;

    /**
     * This button is used to play the game again.
     */
    @FXML
    Button btnPlayAgain;

    /**
     * This list stores the current list of players.
     */
    List<Player> topScores = new ArrayList<>();

    /**
     * This method is where initial call of the Java FX code happens.
     * @param currentPlayer The represents the winner of the game.
     * @param playerList    This variable stores the list of players.
     */
    public void receiveData(Player currentPlayer, List<Player> playerList) {
        winText.setText("Congratulations " + currentPlayer.getName() + "! You won!");
        winText.setTextAlignment(TextAlignment.CENTER);

        scoreText.setTextAlignment(TextAlignment.CENTER);
        scoreText.setText("Your Score is " + currentPlayer.getScore());

        Score score = new Score();
        topScores = score.writeScore(playerList);
    }

    /**
     * This method calls the top scores screen.
     */
    public void btnTopScoresClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("topScores.fxml"));
        Parent topScoresScreen = null;
        try {
            topScoresScreen = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TopScoresController topScoresController = fxmlLoader.getController();
        topScoresController.displayTopScores(topScores);

        Scene scene = new Scene(topScoresScreen);

        Stage stage = (Stage) btnPlayAgain.getScene().getWindow();
        stage.setTitle("Pk's Game");
        stage.setScene(scene);
        stage.setX(200);
        stage.setY(50);
        stage.show();
    }

    /**
     * This method is called to play the game again.
     */
    public void bPlayAgain() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
        Parent welcomeScreen = null;
        try {
            welcomeScreen = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Scene scene = new Scene(welcomeScreen);

        Stage stage = (Stage) btnPlayAgain.getScene().getWindow();
        stage.setTitle("Pk's Game");
        stage.setScene(scene);
        stage.setX(200);
        stage.setY(50);
        stage.show();
    }
}
