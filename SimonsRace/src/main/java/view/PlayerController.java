package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Button btnStartGame;
    @FXML
    VBox vBoxID = new VBox();

    private int gridSize;
    private int noOfPlayers;

    private List<String> playerNames = new ArrayList<>();
    public void receiveData(int gridSize, int noOfPlayers) {

        this.gridSize = gridSize;
        this.noOfPlayers = noOfPlayers;

        TextField[] textFields = new TextField[noOfPlayers];

        for (int i = 1; i <= noOfPlayers; i++) {
            TextField tf = new TextField();
            //--------------------------------------------------NAME SHOULD ALIGN PROPERLY
            Text name = new Text("Name of Player " + i);
            vBoxID.getChildren().add(name);
            vBoxID.getChildren().add(tf);
            textFields[i - 1] = tf;
        }
        vBoxID.setLayoutX(275);
        vBoxID.setLayoutY(200);
        anchorPane.getChildren().add(vBoxID);
    }

    @FXML
    protected void onClickStartGame() throws IOException, NoSuchMethodException, URISyntaxException {
        ObservableList<Node> children = vBoxID.getChildren();
        for (Node node : children) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                playerNames.add(textField.getText());
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("boardScreen.fxml"));
        Parent boardScreen = fxmlLoader.load();

        BoardController boardController = fxmlLoader.getController();
        boardController.initializeBoard(gridSize,noOfPlayers,playerNames);

        Scene scene = new Scene(boardScreen);

        Stage stage = (Stage) btnStartGame.getScene().getWindow();
        stage.setTitle("Pk's Game");
        stage.setUserData(gridSize);
        stage.setScene(scene);
        stage.setX(200);
        stage.setY(50);
        stage.show();
    }

}
