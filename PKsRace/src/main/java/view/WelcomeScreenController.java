package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the first screen of the game.
 */
public class WelcomeScreenController extends Application {

    /**
     * This is button is to start the game.
     */
    @FXML
    Button btnStart = new Button();

    /**
     * This is initial starting of the game.
     * @param stage The stage to start the game.
     */
    @Override
    public void start(Stage stage) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 700, 700);

        stage.setTitle("Pk's Game");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This is main function.
     * @param args String arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called when start game is clicked.
     */
    @FXML
    protected void onBtnClickStartGame() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gridScreen.fxml"));

        Parent gridScreen = null;
        try {
            gridScreen = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GridController gridController = fxmlLoader.getController();
        gridController.initializeDropdown();

        Scene scene = new Scene(gridScreen);

        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setTitle("Grid Size");
        stage.setScene(scene);
        stage.show();

    }
}