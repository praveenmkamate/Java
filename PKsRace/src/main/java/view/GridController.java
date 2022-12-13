package view;

import controller.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is used to take input from the user regarding the board details.
 */
public class GridController {
    /**
     * This button is used to navigate to next page.
     */
    @FXML
    Button btnNext = new Button();

    /**
     * This is used to take the size of the gird.
     */
    @FXML
    TextField textGridSize = new TextField();

    /**
     * This is used to give user to select the number of players.
     */
    ObservableList<String> playerOptions =
            FXCollections.observableArrayList(
                    "2", "3", "4", "5", "6", "7", "8", "9", "10"
            );

    /**
     * The options for the difficulty of the game.
     */
    ObservableList<String> difficultyOptions =
            FXCollections.observableArrayList(
                    "Easy", "Medium", "Hard"
            );
    /**
     * Drop down to select number of players.
     */
    @FXML
    ComboBox numberPlayers;

    /**
     * Drop down to select the difficulty of the game.
     */
    @FXML
    ComboBox difficultyBox;


    /**
     * This method is used to initialize the dropdown.
     */
    public void initializeDropdown() {
        numberPlayers.setItems(playerOptions);
        difficultyBox.setItems(difficultyOptions);
    }

    /**
     * This method is involved to navigate to next page.
     */
    @FXML
    public void onBtnClickNext() {
        Alert alert = new Alert(Alert.AlertType.NONE);

        int gridSize = Integer.valueOf(textGridSize.getText());

        int noPlayers = Integer.valueOf(numberPlayers.getValue().toString());

        String gameDifficulty = difficultyBox.getValue().toString();

        Game.difficulty = gameDifficulty;

        if (noPlayers > gridSize) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Number of Players cannot be greater than Grid Size!");
            alert.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playerScreen.fxml"));
            Parent playerScreen = null;
            try {
                playerScreen = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PlayerController playerController = fxmlLoader.getController();
            playerController.receiveData(gridSize, noPlayers);

            Scene scene = new Scene(playerScreen);

            Stage stage = (Stage) btnNext.getScene().getWindow();
            stage.setTitle("Player Information");
            stage.setUserData(gridSize);
            stage.setScene(scene);
            stage.show();
        }

    }

}
