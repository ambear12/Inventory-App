package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Name: Amber Lanier
 * Course: C482 Software 1
 * ID:#001313050
 * WGU
 *
 * Main pulls up and starts the program and inventory.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
        primaryStage.setTitle("First Screen");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();

    }

    public static void main(String [] args) {
        launch(args);
    }
}
