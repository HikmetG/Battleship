package com.example.media1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    /** Wird verwendet um das Video abzuspielen */
    @FXML
    private MediaView mediaView;
    /** Wird verwendet um die Datei des Videos zugreifen zu können */
    private File file;
    /** Wird verwendet um das Video abzuspielen */
    private Media media;
    /** Wird verwendet um das Video abzuspielen */
    private MediaPlayer mediaPlayer;
    /** Wird verwendet um die Fxml Datei zu laden */
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
    @FXML
    void handleButtonAction(MouseEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("stage2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Laesst das Video im Hintergrund automatisch abspielen
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        file = new File("Media1/videos/ezgif.com-gif-maker.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

    }

    /**
     * Funktion des Buttons um das Video zu starten
     */
    public void playMedia(){
        mediaPlayer.play();
        if(mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            mediaPlayer.seek(Duration.seconds(0.0));
        }

    }
    /**
     * Funktion des Buttons um das Video zu pausieren
     */
    public void pauseMedia(){
        mediaPlayer.pause();
    }

    /**
     * Funktion um das Programm zu beenden
     * @param event
     */
    public void closeApp(MouseEvent event){
        System.exit(0);
    }
}