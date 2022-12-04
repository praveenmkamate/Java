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

public class GridController {
    @FXML
    Button btnNext = new Button();

    @FXML
    TextField textGridSize = new TextField();

    ObservableList<String> playerOptions =
            FXCollections.observableArrayList(
                    "2", "3", "4", "5", "6", "7", "8", "9", "10"
            );

    ObservableList<String> difficultyOptions =
            FXCollections.observableArrayList(
                    "Easy","Medium","Hard"
            );
    @FXML
    ComboBox numberPlayers;

    @FXML
    ComboBox difficultyBox;


    public void initializeDropdown() {
        numberPlayers.setItems(playerOptions);
        difficultyBox.setItems(difficultyOptions);
    }

    @FXML
    public void onBtnClickNext() throws IOException {
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
            Parent playerScreen = fxmlLoader.load();

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
