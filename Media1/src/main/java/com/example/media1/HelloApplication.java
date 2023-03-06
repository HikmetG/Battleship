package com.example.media1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * @author Hikmet 85987
 * @author Ensar 86044
 * @version 1.0
 */
public class HelloApplication extends Application {
    /**
     * Lädt die hello-view.fxml Datei. Startet das Programm in Main Menu.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("SCHIFFE VERSENKEN");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Main Klasse, welches das Programm startet
     */
    public static void main(String[] args) {
        launch();
    }
}