package com.example.media1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Stage2Controller {
    /** Wird verwendet um die Fxml Datei zu laden */
    @FXML
    private Stage stage;
    /** Wird verwendet um die Fxml Datei zu laden */
    private Scene scene;
    /** Wird verwendet um die Fxml Datei zu laden */
    private Parent root;

    /**
     * Wird verwendet um die Szene zu wechseln
     * @param event
     * @throws IOException
     */
    public void toStage1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wird verwendet um die Szene zu wechseln
     * @param event
     * @throws IOException
     */
    public void toNewGameStage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("newGameStage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** Wird verwendet um auf die LoadDatei zugreifen zu können */
    FileChooser fileChooser = new FileChooser();
    /** Kopie des Player Arrays */
    public static int[][] copyArray = new int[10][10];
    /** Kopie des Bot Arrays */
    public static int[][] copyBotArray = new int[10][10];
    /** Kopie der ausgewählten Bereiche während des Spieles. */
    public static int[][] copyClickedArray = new int[10][10];
    /** Kopie der ausgewählten Bereiche des Botes während des Spieles. */
    public static int[][] copyClickedBotArray = new int[10][10];
    /**
     * Getter von copyArray
     */
    public static int[][] getcopyArray(){
        return copyArray;
    }
    /**
     * Getter von copyBotArray
     */
    public static int[][] getcopyBotArray(){
        return copyBotArray;
    }
    /**
     * Getter von copyClickedArray
     */
    public static int[][] getCopyClickedArray(){return copyClickedArray;}
    /**
     * Getter von copyClickedBotArray
     */
    public static int[][] getCopyClickedBotArray(){return copyClickedBotArray;}

    /**
     * Funktion um den Load Button bedienen zu können
     * @param event
     * @throws IOException
     */
    public void loadData(ActionEvent event) throws IOException {
        fileChooser.setInitialDirectory(new File("Media1/SaveLoadGameFolder"));
        File file = fileChooser.showOpenDialog(new Stage());
        Scanner scanner = new Scanner(file);

        /** Entnimmt das Array in der Txt Datei und speichert sie in den jeweiligen Arrays. */
        while (scanner.hasNextLine()){
            for (int i=0;i<10;i++){
                for(int q=0;q<10;q++){
                    copyArray[i][q] = Integer.parseInt(scanner.nextLine());
                }
            }
            for (int i=0;i<10;i++){
                scanner.nextLine();
            }
            for (int i=0;i<10;i++){
                for(int q=0;q<10;q++){
                    copyBotArray[i][q] = Integer.parseInt(scanner.nextLine());
                }
            }
            for (int i=0;i<10;i++){
                scanner.nextLine();
            }
            for (int i=0;i<10;i++){
                for(int q=0;q<10;q++){
                    copyClickedArray[i][q] = Integer.parseInt(scanner.nextLine());
                }
            }for (int i=0;i<10;i++){
                scanner.nextLine();
            }
            for (int i=0;i<10;i++){
                for(int q=0;q<10;q++){
                    copyClickedBotArray[i][q] = Integer.parseInt(scanner.nextLine());
                }
            }
        }
        /** Wechselt die Szene direkt zur Game Szene */
        toGameScene(event);
    }

    /**
     * Wird verwendet um die Szene zu wechseln
     * @param event
     * @throws IOException
     */
    public void toGameScene(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

}
