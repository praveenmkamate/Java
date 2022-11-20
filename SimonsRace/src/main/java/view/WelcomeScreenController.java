package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenController extends Application {

    @FXML
    Button btnStart = new Button();

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,700, 700);

        stage.setTitle("Pk's Game");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    protected void onBtnClickStartGame() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gridScreen.fxml"));
        Parent gridScreen = fxmlLoader.load();

        //GridController gridController = fxmlLoader.getController();

        Scene scene = new Scene(gridScreen,700, 700);

        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setTitle("Grid Size");
        stage.setScene(scene);
        stage.show();

    }
}