/**
 * This module is used to make use of Java FX project.
 */
module com.simonsrace.simonsrace {
    requires javafx.controls;
    requires javafx.fxml;

    opens view to javafx.fxml;
    exports view;
    exports obstacle;
    exports controller;
    exports board;
}