package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerController {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Button btnStartGame;
    @FXML
    VBox vBoxLabel = new VBox(15);

    @FXML
    VBox vBoxText = new VBox(5);

    @FXML
    VBox vBoxColor = new VBox(5);

    @FXML
    VBox vBoxLocation = new VBox(5);

    private int gridSize;
    private int noOfPlayers;

    ObservableList<String> colors =
            FXCollections.observableArrayList(
                    "Black", "Blue", "Green", "Neon", "Orange", "Pink", "Purple", "Red", "White", "Yellow"
            );

    ObservableList<Integer> playerLocation = FXCollections.observableArrayList();

    private List<String> pNames = new ArrayList<>();
    private List<String> pColors = new ArrayList<>();
    private List<Integer> pLanes = new ArrayList<>();

    private Map<String, String> playerColor = new HashMap<>();

    private Map<String, Integer> playerLane = new HashMap<>();

    private static <T> Set<T> findDuplicates(List<T> list) {
        return list.stream().distinct()
                .filter(i -> Collections.frequency(list, i) > 1)
                .collect(Collectors.toSet());
    }

    public void receiveData(int gridSize, int noOfPlayers) {

        this.gridSize = gridSize;

        for (int i = 1; i <= gridSize; i++) {
            playerLocation.add(i);
        }

        this.noOfPlayers = noOfPlayers;

        TextField[] textFields = new TextField[noOfPlayers];

        for (int i = 1; i <= noOfPlayers; i++) {
            Text label = new Text("Player " + i + ":");
            TextField tf = new TextField();


            vBoxLabel.getChildren().add(label);
            vBoxText.getChildren().add(tf);

            ComboBox color = new ComboBox(colors);
            vBoxColor.getChildren().add(color);

            ComboBox location = new ComboBox(playerLocation);
            vBoxLocation.getChildren().add(location);
        }

        vBoxLabel.setLayoutX(122);
        vBoxLabel.setLayoutY(91);

        vBoxText.setLayoutX(197);
        vBoxText.setLayoutY(91);

        vBoxColor.setLayoutX(368);
        vBoxColor.setLayoutY(91);

        vBoxLocation.setLayoutX(480);
        vBoxLocation.setLayoutY(91);

        anchorPane.getChildren().addAll(vBoxLabel, vBoxText, vBoxColor, vBoxLocation);

    }

    public void displayError(String error) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.show();

    }

    @FXML
    protected void onClickStartGame() throws IOException, NoSuchMethodException, URISyntaxException {
        boolean fieldEmptyError = false;
        boolean duplicateError = false;

        pNames.clear();
        pColors.clear();
        pLanes.clear();

        Set<String> duplicates = null;
        ObservableList<Node> textChildren = vBoxText.getChildren();
        for (Node node : textChildren) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;

                if (textField.getText().isEmpty()) {
                    fieldEmptyError = true;
                } else {
                    pNames.add(textField.getText());
                }
            }
        }

        ObservableList<Node> colorChildren = vBoxColor.getChildren();

        for (Node node : colorChildren) {
            if (node instanceof ComboBox<?>) {
                ComboBox colorField = (ComboBox) node;
                pColors.add((String) colorField.getValue());
            }
        }

        ObservableList<Node> locChildren = vBoxLocation.getChildren();

        for (Node node : locChildren) {
            if (node instanceof ComboBox<?>) {
                ComboBox locField = (ComboBox) node;
                pLanes.add((Integer) locField.getValue());
            }
        }

        if (findDuplicates(pNames).size() > 0 || findDuplicates(pColors).size() > 0 || findDuplicates(pLanes).size() > 0)
            duplicateError = true;

        if (fieldEmptyError) {
            displayError("Name field cannot be empty.");
        } else if (duplicateError) {
            displayError("Two Players cannot have same name, color or lane.");
        } else {
            getData(pNames,pColors,pLanes);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("boardScreen.fxml"));
            Parent boardScreen = fxmlLoader.load();

            BoardController boardController = fxmlLoader.getController();
            boardController.initializeBoard(gridSize, noOfPlayers, pNames, playerColor, playerLane);

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

    private void getData(List<String> pNames, List<String> pColors, List<Integer> pLanes) {
        for(int i=0;i<pNames.size();i++){
            playerColor.put(pNames.get(i),pColors.get(i));
            playerLane.put(pNames.get(i),pLanes.get(i));
        }
    }


}
