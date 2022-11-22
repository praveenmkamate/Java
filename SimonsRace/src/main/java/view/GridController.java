package view;

import Board.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GridController {
    @FXML
    Button btnNext = new Button();

    @FXML
    TextField textGridSize = new TextField();

    @FXML
    TextField textNoOfPlayers = new TextField();

    @FXML
    public  void onBtnClickNext() throws IOException {
        Alert alert = new Alert(Alert.AlertType.NONE);

        int gridSize =  Integer.valueOf(textGridSize.getText());
        int noPlayers = Integer.valueOf(textNoOfPlayers.getText());

        //System.out.println(gridSize);
        if(gridSize<4 || gridSize>10){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setHeaderText("Warning:");
            alert.setContentText("Grid Out of Range!");
            alert.show();
        } else if(noPlayers>gridSize){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("Error:");
            alert.setContentText("Number of Players cannot be greater than Grid Size!");
            alert.show();
        } else{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playerScreen.fxml"));
            Parent playerScreen = fxmlLoader.load();

            PlayerController playerController = fxmlLoader.getController();
            playerController.receiveData(gridSize,noPlayers);

            Scene scene = new Scene(playerScreen);

            Stage stage = (Stage) btnNext.getScene().getWindow();
            stage.setTitle("Player Information");
            stage.setUserData(gridSize);
            stage.setScene(scene);
            stage.show();
        }

    }

    /*public static void main(String args[]){

        try {
            Properties properties = new Properties();
            InputStream inputStream = GridController.class.getResourceAsStream("grid.properties");
            properties.load(inputStream);
            properties.setProperty("gridSize", textGridSize.getText());
            properties.store(new FileOutputStream("xyz.properties"), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }*/



}
