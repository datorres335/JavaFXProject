package com.example.demojavafx3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *  HBox Demo
 */

public class HBoxDemo___ extends Application
{
   public static void main(String[] args)
   {
      // Launch the application.
      launch(args);
   }
   
   @Override
   public void start(Stage primaryStage)
   {
      // Create three Button components.
      Button button1 = new Button("Button 1");
      Button button2 = new Button("Button 2");
      Button button3 = new Button("Button 3");
      
      //Apply CSS classes
      button1.getStyleClass().add("button-black");
      button2.getStyleClass().add("button-white");
      button3.getStyleClass().add("button-black");
      
      // Create an HBox.
      VBox hbox = new VBox(10, button1, button2, button3);
      hbox.setPadding(new Insets(10));
      
      // Create a Scene with the HBox as its root node.
      // The Scene is 300 pixels wide by 100 pixels high.
      Scene scene = new Scene(hbox, 300, 100);
      
      // Load the CSS file
      scene.getStylesheets().add(getClass().getResource("demo9.css").toExternalForm());
      
      // Add the Scene to the Stage.
      primaryStage.setScene(scene);
      
      // Set the stage title.
      primaryStage.setTitle("HBox Demo");
      
      // Show the window.
      primaryStage.show();
   }
}
