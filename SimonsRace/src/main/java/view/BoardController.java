package view;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;

import java.util.List;

public class BoardController {


    @FXML
    SplitPane splitPane;
    @FXML
    AnchorPane anchorPane;

    public void initializeBoard(int gridSize, int noOfPlayers, List<String> playerNames) {

        GridPane root = new GridPane();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col ++) {
                StackPane square = new StackPane();
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                square.setStyle("-fx-background-color: "+color+";");
                root.add(square, col, row);
            }
        }
        for (int i = 0; i < gridSize; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(150, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(150, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        anchorPane.getChildren().add(root);

    }
}
