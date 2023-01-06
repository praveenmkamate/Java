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

/**
 * This class is used to take in details regarding the players.
 */
public class PlayerController {

    /**
     * The anchor pane on which the elements are placed.
     */
    @FXML
    AnchorPane anchorPane;

    /**
     * The button to start the game.
     */
    @FXML
    Button btnStartGame;
    /**
     * VBOX of the player labels.
     */
    @FXML
    VBox vBoxLabel = new VBox(15);

    /**
     * VBOX of the text boxes.
     */
    @FXML
    VBox vBoxText = new VBox(5);

    /**
     * VBOX of the color options to players.
     */
    @FXML
    VBox vBoxColor = new VBox(5);

    /**
     * VBOX of the lane options to the players.
     */
    @FXML
    VBox vBoxLocation = new VBox(5);

    /**
     * This represents the size of the board.
     */
    private int gridSize;
    /**
     * This represents the number of players in current game.
     */
    private int noOfPlayers;

    /**
     * This stores the list of available colors to the players.
     */
    ObservableList<String> colors =
            FXCollections.observableArrayList(
                    "Black", "Blue", "Green", "Neon", "Orange", "Pink", "Purple", "Red", "White", "Yellow"
            );

    /**
     * This stores the list of lanes of the players.
     */
    ObservableList<Integer> playerLocation = FXCollections.observableArrayList();

    /**
     * This list is used store the player names.
     */
    private List<String> pNames = new ArrayList<>();
    /**
     * This list is used store the player colors.
     */
    private List<String> pColors = new ArrayList<>();
    /**
     * This list is used store the player lanes.
     */
    private List<Integer> pLanes = new ArrayList<>();

    /**
     * This map is used store the player color to corresponding player.
     */
    private Map<String, String> playerColor = new HashMap<>();

    /**
     * This map is used store the player lane to corresponding player.
     */
    private Map<String, Integer> playerLane = new HashMap<>();

    /**
     * @param list The list in which duplicates have to be checked
     * @return the number of duplicates in the list
     * Reference: Stack Overflow <https://stackoverflow.com/questions/27677256/java-8-streams-to-find-the-duplicate-elements>
     */
    private static <T> Set<T> findDuplicates(List<T> list) {
        return list.stream().distinct()
                .filter(i -> Collections.frequency(list, i) > 1)
                .collect(Collectors.toSet());
    }

    /**
     * @param gridSize The size of the board.
     * @param noOfPlayers The number of players in the current game.
     */
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

    /**
     * This method is used to display an error on the page.
     * @param error The error message to be displayed on the front end.
     */
    public void displayError(String error) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.show();

    }

    /**
     * This method is called when start game button is clicked.
     */
    @FXML
    protected void onClickStartGame() {
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
            pNames.clear();
            pColors.clear();
            pLanes.clear();
            displayError("Name field cannot be empty.");
        } else if (duplicateError) {
            pNames.clear();
            pColors.clear();
            pLanes.clear();
            displayError("Two Players cannot have same name, color or lane.");
        } else {
            getData(pNames,pColors,pLanes);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("boardScreen.fxml"));
            Parent boardScreen = null;
            try {
                boardScreen = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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

    /**
     * @param pNames The names of the players.
     * @param pColors The colors of the players.
     * @param pLanes The lanes of the players.
     */
    private void getData(List<String> pNames, List<String> pColors, List<Integer> pLanes) {
        for(int i=0;i<pNames.size();i++){
            playerColor.put(pNames.get(i),pColors.get(i));
            playerLane.put(pNames.get(i),pLanes.get(i));
        }
    }


}
