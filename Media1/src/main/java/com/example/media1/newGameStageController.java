package com.example.media1;

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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;


public class newGameStageController {
    /** Layout für die Buttons */
    @FXML
    private GridPane gridPaneButtons;
    /** Array für die platzierten Schiffe */
    private static int[][] placeOfShips = new int[10][10];
    /** Array für die platzierten Schiffe für den Bot */
    private static int[][] placeOfBotShips = new int[10][10];

    /**
     * Verfaerbt die Buttons in den Zellen des Gridpanes in der Szene des Waehlbereiches der Schiffe
     * Setzt die Jeweilige Array Position auf 1 sofern darauf geklickt wurde.
     * @param event
     */
    public void getIdOfButton(ActionEvent event){
        Button btn = (Button) event.getSource();
        String[] part = btn.getId().split("n");
        int k = Integer.parseInt(part[1]);
        int a = k / 10;
        int b = k % 10;
        if(placeOfShips[a][b]==0) {
            placeOfShips[a][b] = 1;
            btn.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        } else {
            placeOfShips[a][b] = 0;
            btn.setBackground(new Background(new BackgroundFill(null, null, null)));

        }
    }

    /**
     * Generiert die Buttons in den Zellen des Gridpanes
     */
    public void placeGridPane(){
        for (int i=0; i<10;i++){
            for(int q= 0; q<10;q++){
                Button temp = new Button(i +";"+ q);
                temp.setMinHeight(25);
                temp.setMinWidth(39);
                temp.setPrefHeight(25);
                temp.setPrefWidth(39);
                temp.setId("button"+((q*10)+i));

                temp.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        getIdOfButton(event);
                    }
                });
                gridPaneButtons.add(temp,i,q);
            }
        }
    }

    /**
     * Funktion um die Platzierung der Schiffe für den Bot zu generieren.
     */
    public void generateBotArray(){

        int n,z; // n ist der Array Index von der Bot Schiffe. z bestimmt ob es vertical oder horizontal ist.
        boolean con = true; // Bleibt True während der Schiffe Platzierung.
        Random rand = new Random();
        while(con) {
            // Bot Array = 0
            for(int i=0; i<100; i++){
                int a = i / 10;
                int b = i % 10;
                placeOfBotShips[a][b] = 0;
            }


            try {
                // 2 und <6 sind die Schiffsgroessen
                for (int q = 2; q < 6; q++) {
                    n = rand.nextInt(100); // waehlt ein Feld für die Schiffe aus
                    z = rand.nextInt(2); //z=0 vertical z=1 horizontal
                    // zB n = 97 -> Array[a][b] = Array[9][7]
                    int a = n / 10;
                    int b = n % 10;

                    // abfrage ob in der Zelle etwas platziert ist.
                    if (placeOfBotShips[a][b] != 1) {
                        if (z == 1) {
                            // for schleife laeuft solange wie grösse des Schiffes.
                            for (int pl = 0; pl < q; pl++) {
                                if(placeOfBotShips[a][b + pl] != 1){
                                    placeOfBotShips[a][b + pl] = 1;
                                }else{
                                    throw new ArrayIndexOutOfBoundsException();
                                };
                            }
                        } else {
                            for (int pl = 0; pl < q; pl++) {
                                if(placeOfBotShips[a + pl][b] != 1){
                                    placeOfBotShips[a + pl][b] = 1;
                                }else{
                                    throw new ArrayIndexOutOfBoundsException();
                                };
                            }
                        }
                    } else { //falls kein Schiff platziert wurden ist muss q = q -1 berechnet werden, da die jeweilige Schiffsgrösse nicht platziert worden ist.
                             //Deswegen da q nicht eins erhöht werden
                        q = q - 1;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {

                continue;
            }
            break;
        }
    }
    /**
     * Getter von placeOfShips
     */
    public static int[][] getPlaceOfShips(){
        return placeOfShips;
    }
    /**
     * Getter von PlaceofBotShips
     */
    public static int[][] getPlaceOfBotShips(){
        return placeOfBotShips;
    }
    @FXML
    private AnchorPane InnerAnchorPane;
    @FXML
    private Button PlaceShipsButton;

    /**
     * Damit beginnt die Platzierung der Schiffe
     * @param event
     * @throws InterruptedException
     */
    public void createArrayOfShips(ActionEvent event) throws InterruptedException {
        placeGridPane();

        InnerAnchorPane.setVisible(true);
        PlaceShipsButton.setVisible(true);
        for(int i=0; i<100; i++){
            int a = i / 10;
            int b = i % 10;
            placeOfShips[a][b] = 0;
            placeOfBotShips[a][b] = 0;
        }
        generateBotArray();
    }

    /**
     * Kontrolliert ob die Schiffe richtig platziert worden sind.
     * @param placeOfShips
     * @return
     */
    public boolean shipsPlaced(int[][] placeOfShips){
        int h=0;
        int[] ships = {0,0,0,0};
        boolean ship1 = false;
        boolean ship2 = false;
        boolean ship3 = false;
        boolean ship4 = false;



        for (int i=0; i<10;i++){
            for (int q=0;q<10;q++){
                if(placeOfShips[i][q]==1 && q !=9){
                    h++;
                }else {
                    if(placeOfShips[i][q]==1 && q ==9){
                        h++;
                    }
                    if(h<6){
                        if(h==2){
                            if(ship1==false){
                                ship1 = true;
                            }else{
                                ship1 = false;
                            }
                        }

                        if(h==3){
                            if(ship2==false){
                                ship2 = true;
                            }else{
                                ship2 = false;
                            }
                        }
                        if(h==4){
                            if(ship3==false){
                                ship3 = true;
                            }else{
                                ship3 = false;
                            }
                        }if(h==5){
                            if(ship4==false){
                                ship4 = true;
                            }else{
                                ship4 = false;
                            }
                        }

                    }else{
                        h =0;
                        return false;
                    }
                    h =0;
                }
            }
            h=0;
        }

        for (int q=0; q<10;q++){
            for (int i=0;i<10;i++){
                if(placeOfShips[i][q]==1 && i !=9){
                    h++;
                }else {
                    if(placeOfShips[i][q]==1 && i ==9){
                        h++;
                    }
                    if(h<6){
                        if(h==2){
                            if(ship1==false){
                                ship1 = true;
                            }else{
                                ship1 = false;
                            }
                        }

                        if(h==3){
                            if(ship2==false){
                                ship2 = true;
                            }else{
                                ship2 = false;
                            }
                        }
                        if(h==4){
                            if(ship3==false){
                                ship3 = true;
                            }else{
                                ship3 = false;
                            }
                        }if(h==5){
                            if(ship4==false){
                                ship4 = true;
                            }else{
                                ship4 = false;
                            }
                        }

                    }else{
                        h=0;
                        return false;
                    }
                    h =0;
                }
            }
            h=0;
        }
        if (ship1 && ship2 && ship3 && ship4){

            return true;
    }else{
            return false;
        }

    }


    /**
     * Falls falsch platziert ist -> POPUP ausgeben
     */
    public void displayPopUp(){
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Wrong");
        Label label1= new Label("Ships placed wrong");
        Button button1= new Button("Close this pop up window");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Wird verwendet um die Szene zu wechseln
     * @param event
     * @throws IOException
     */
    public void toGameScene(ActionEvent event) throws IOException {
        if (shipsPlaced(placeOfShips)) {
            root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else {
            displayPopUp();
        }
    }

    /**
     * Wird verwendet um die Szene zu wechseln
     * @param event
     * @throws IOException
     */
    public void toStage2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("stage2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
