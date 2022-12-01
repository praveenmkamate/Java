package view;

import board.Player;
//import board.Score;
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
import java.util.List;

public class WinController {
    @FXML
    Text winText;

    @FXML
    Text scoreText;

    @FXML
    AnchorPane winPane;

    @FXML
    ImageView winnerImage;

    @FXML
    Button btnPlayAgain;
    public void receiveData(Player currentPlayer, List<Player> playerList) throws IOException {
        winText.setText("Congratulations "+currentPlayer.getName()+"! You won!");
        winText.setTextAlignment(TextAlignment.CENTER);

        scoreText.setTextAlignment(TextAlignment.CENTER);
        scoreText.setText("Your Score is "+currentPlayer.getScore());

        /*Score score = new Score();
        score.writeScore(playerList);*/
    }

    public void bPlayAgain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
        Parent welcomeScreen = fxmlLoader.load();

        Scene scene = new Scene(welcomeScreen);

        Stage stage = (Stage) btnPlayAgain.getScene().getWindow();
        stage.setTitle("Pk's Game");
        stage.setScene(scene);
        stage.setX(200);
        stage.setY(50);
        stage.show();
    }
}
