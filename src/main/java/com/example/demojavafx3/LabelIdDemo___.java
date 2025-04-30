package com.example.demojavafx3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LabelIdDemo___ extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a label and set its ID
        Label outputLabel = new Label("File not found!");
        outputLabel.setId("label-error");

        // Add it to the layout
        StackPane root = new StackPane(outputLabel);
        Scene scene = new Scene(root, 300, 100);

        // Load the CSS file
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Set the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Label ID CSS Demo");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
