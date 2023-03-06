package com.example.media1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.example.media1.Stage2Controller.getcopyBotArray;
import static com.example.media1.Stage2Controller.getcopyArray;
import static com.example.media1.newGameStageController.getPlaceOfShips;
import static com.example.media1.newGameStageController.getPlaceOfBotShips;
import static com.example.media1.Stage2Controller.getCopyClickedArray;
import static com.example.media1.Stage2Controller.getCopyClickedBotArray;
public class GameSceneController {

    @FXML
    private GridPane gridPane1;
    @FXML
    private GridPane gridPane2;
    @FXML
    Button saveButton;
    private static int[][] emptyArray = new int[10][10];
    int[][] placesOfClicks = new int[10][10]; //Ausgewählte Bereiche von den Schiffen
    int[][] placesOfBotClicks = new int[10][10]; //Ausgewählte Bereiche von den Schiffen von Bot
    /** Array des Spielers waehrend des Spiels */
    int[][] importedArray;
    /** Array des Bots waehrend des Spiels  */
    int[][] importedBotArray;
    /** Wenn ein Spielstand geladen worden ist -> ausgewaehlten Feldern speichern zu können*/
    int[][] mustClickedArrayAfterLoad = new int[10][10];
    /** Wenn ein Spielstand geladen worden ist -> ausgewaehlten Feldern speichern zu können für Bot*/
    int[][] mustClickedBotArrayAfterLoad = new int[10][10];

    /**
     * Bestimmt ob Load Game oder New Game
     * Und platziert die Button des ganzen Spielfeldes.
     */
    public void initialize(){

        boolean includes1 = false;
        boolean includes2 = false;

        for (int i=0;i<10;i++){
            for(int q=0;q<10;q++){
                if (getPlaceOfShips()[i][q] == 1){
                    includes1 = true;
                }
            }
        }
        for (int i=0;i<10;i++){
            for(int q=0;q<10;q++){
                if (getPlaceOfBotShips()[i][q] == 1){
                    includes2 = true;
                }
            }
        }

        if (!includes1){
            importedArray = getcopyArray();
            mustClickedArrayAfterLoad = getCopyClickedArray();
        }else {
            importedArray = getPlaceOfShips();
            mustClickedArrayAfterLoad = emptyArray;
        }
        if (!includes2){
            importedBotArray = getcopyBotArray();
            mustClickedBotArrayAfterLoad = getCopyClickedBotArray();
        }else {
            importedBotArray = getPlaceOfBotShips();
            mustClickedBotArrayAfterLoad = emptyArray;
        }
        for (int i=0; i<10;i++){
            for(int q= 0; q<10;q++){
                Button temp = new Button(i +";"+ q);
                temp.setMinHeight(20.5);
                temp.setMinWidth(37);
                temp.setPrefHeight(20.5);
                temp.setPrefWidth(37);
                temp.setId("button"+((q*10)+i));

                temp.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        Button btn = (Button) event.getSource();

                        String[] part = btn.getId().split("n");
                        int k = Integer.parseInt(part[1]);
                        int a = k / 10;
                        int b = k % 10;
                        btn.setBackground(clickedIsOnShip(importedArray,a,b,1,btn));
                        placesOfClicks[a][b]=1;
                    }
                });
                gridPane1.add(temp,i,q);
            }
        }
        for (int p=0; p<10;p++){
            for(int c= 0; c<10;c++){
                Button temp = new Button(p +";"+ c);
                temp.setMinHeight(20.5);
                temp.setMinWidth(37);
                temp.setPrefHeight(20.5);
                temp.setPrefWidth(37);
                temp.setId("button"+((c*10)+p));

                temp.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        Button btn = (Button) event.getSource();

                        String[] part = btn.getId().split("n");
                        int k = Integer.parseInt(part[1]);
                        int a = k / 10;
                        int b = k % 10;
                        btn.setBackground(clickedIsOnShip(importedBotArray,a,b,2,btn));
                        placesOfBotClicks[a][b]=1;
                    }
                });
                gridPane2.add(temp,p,c);
            }
        }
        gridPane1.setVisible(false);
        gridPane2.setVisible(false);
        saveButton.setVisible(false);
    }

    /** Counter, falls gleich 14 dann gewonnen */
    int finish1 = 0;
    /** Counter, falls gleich 14 dann gewonnen */
    int finish2 = 0;

    /**
     * Falls ein getroffen worden ist, wird Hintergrund rot gefaerbt und counter erhöht.
     * @param shipArray
     * @param a
     * @param b
     * @param whichGridPane
     * @param btn
     * @return
     */
    public Background clickedIsOnShip(int[][] shipArray, int a, int b, int whichGridPane, Button btn){

        int i,j;

        if(shipArray[a][b] == 1){
            btn.setDisable(true);
            if(whichGridPane == 1){
                finish1++;
            }else {
                finish2++;
            }
            if (finish1==14){
                displayPopUp("Bot");
            }
            if (finish2 == 14){
                displayPopUp("YOU");
            }
            return (new Background(new BackgroundFill(Color.RED, null, null)));

        }else{
            if(whichGridPane == 1){
                gridPane1.setDisable(true);
                gridPane2.setDisable(false);
            }else {
                gridPane2.setDisable(true);
                gridPane1.setDisable(false);
            }
            btn.setDisable(true);
            botButtonFire();
            return (new Background(new BackgroundFill(Color.BLACK, null, null)));
        }


    }

    @FXML
    Button startGameButton;

    /**
     * start game Button
     */
    public void gamePlay(){
        gridPane1.setVisible(true);
        gridPane1.setDisable(true);
        gridPane2.setVisible(true);
        saveButton.setVisible(true);
        startGameButton.setVisible(false);
        autoFire();
    }

    /**
     * Ki des Bots zum schiessen
     */
    public void botButtonFire(){
        for (int h=1;h<101;h++) {
            Random rand = new Random();
            int n = rand.nextInt(100);
            n++;

            Button btnnnn = (Button) gridPane2.getChildren().get(n);
            Button btnnn = (Button) gridPane1.getChildren().get(n);
            btnnn.fire();
        }
        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(2)));
        pause.play();
    }

    /**
     * Falls Load Game verwendet worden ist, werden die Schiffe automatisch auf den letzten Zustand gesetzt.
     */
    public void autoFire(){

        for (int i=0; i<10;i++){
            for (int q=0;q<10;q++){
                if(mustClickedArrayAfterLoad[i][q]==1){
                    int n = i*10+q+1;
                    Button FireButton = (Button) gridPane2.getChildren().get(n);
                    FireButton.fire();
                }
                if(mustClickedBotArrayAfterLoad[i][q]==1){
                    int n = i*10+q+1;
                    Button FireButton = (Button) gridPane1.getChildren().get(n);
                    FireButton.fire();
                }
            }
        }

    }

    /**
     * Game Over Pop up
     * @param text
     */
    public void displayPopUp(String text){
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("GAME OVER");
        Label label1= new Label(text+" WON");
        Button button1= new Button("Close this pop up window");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                popupwindow.close();
                gridPane1.setDisable(true);
                gridPane2.setDisable(true);
            }
        });
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
    FileChooser fileChooseer = new FileChooser();

    /**
     * Funktionalitaet des Save Game Buttons
     * @throws IOException
     */
    public void saveGame() throws IOException {
        int[][] copied = getPlaceOfShips();

        fileChooseer.setInitialDirectory(new File("Media1/SaveLoadGameFolder"));
        File file = fileChooseer.showSaveDialog(new Stage());
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i=0;i<10;i++){
            for (int q=0;q<10;q++){

                bw.write(Integer.toString(importedArray[i][q]));
                bw.newLine();

            }
        }
        for (int i=0;i<10;i++){
            bw.write("");
            bw.newLine();
        }
        for (int i=0;i<10;i++){
            for (int q=0;q<10;q++){

                bw.write(Integer.toString(importedBotArray[i][q]));
                bw.newLine();

            }
        }
        for (int i=0;i<10;i++){
            bw.write("");
            bw.newLine();
        }
        for (int i=0;i<10;i++){
            for (int q=0;q<10;q++){

                bw.write(Integer.toString(placesOfClicks[i][q]));
                bw.newLine();

            }
        }
        for (int i=0;i<10;i++){
            bw.write("");
            bw.newLine();
        }
        for (int i=0;i<10;i++){
            for (int q=0;q<10;q++){

                bw.write(Integer.toString(placesOfBotClicks[i][q]));
                bw.newLine();

            }
        }
        bw.close();
        }

    private Stage stage;
    private Scene scene;
    private Parent root;

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
}
