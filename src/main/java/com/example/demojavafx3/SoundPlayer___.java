package com.example.demojavafx3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SoundPlayer___ extends Application
{
   public static void main(String[] args)
   {
      // Launch the application.
      launch(args);
   }
   
   @Override
   public void start(Stage primaryStage)
   {
      // Load the sound file and create the MediaPlayer.
//      File soundFile = new File(getClass().getResource("guitar.wav").toExternalForm());//File("guitar.wav");
//      Media media = new Media(soundFile.toURI().toString());
      Media media = new Media(getClass().getResource("guitar.wav").toExternalForm());
      MediaPlayer player = new MediaPlayer(media);
      
      // Event handler for the OnEndOfMedia event
      player.setOnEndOfMedia(() ->
      {
         player.stop();
      });
      
      // Create the buttons.
      Button playButton = new Button("Play");
      Button pauseButton = new Button("Pause");
      Button stopButton = new Button("Stop");
      
      // Event handler for the play Button
      playButton.setOnAction(event ->
      {
         player.play();
      });

      // Event handler for the pause Button
      pauseButton.setOnAction(event ->
      {
         player.pause();
      });

      // Event handler for the stop Button
      stopButton.setOnAction(event ->
      {
         player.stop();
      });
      
      // Put the Buttons in an HBox.
      HBox hbox = new HBox(10, playButton, pauseButton, stopButton);
      hbox.setAlignment(Pos.CENTER);
      hbox.setPadding(new Insets(10));
      
      // Create a Scene with the HBox as its root node.
      Scene scene = new Scene(hbox);
      
      // Add the Scene to the Stage and display the window.
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}