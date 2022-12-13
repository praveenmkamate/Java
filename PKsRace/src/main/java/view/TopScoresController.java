package view;

import board.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static board.Score.finalPlayerList;

/**
 * This is controller for the top score.
 */
public class TopScoresController {

    /**
     * The button to play the game again.
     */
    @FXML
    Button btnPlayAgain;

    /**
     * VBOX to display the player names.
     */
    @FXML
    VBox vBoxName;

    /**
     * VBOX to display the player scores.
     */
    @FXML
    VBox vBoxScore;

    /**
     * This method is called when the play again button is clicked.
     */
    @FXML
    public void btnPlayAgainClicked() {
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

    /**
     * This method is used to display the top 10 scores of players.
     * @param topScores The list of players to display the top 10 scores.
     */
    public void displayTopScores(List<Player> topScores) {
        int count = 0;

        Comparator<Player> c = (p1, p2) -> ((Integer) p2.getScore()).compareTo(p1.getScore());
        topScores.sort(c);

        Text nameHeader = new Text("Player Name");
        Text scoreHeader = new Text("Top Score");

        vBoxName.getChildren().add(nameHeader);
        vBoxScore.getChildren().add(scoreHeader);

        while (count < 10 && count < topScores.size()) {

            Text name = new Text(topScores.get(count).getName());
            Text score = new Text(Integer.toString(topScores.get(count).getScore()));

            vBoxName.getChildren().add(name);
            vBoxScore.getChildren().add(score);

            count++;
        }
    }
}
