package com.example.demojavafx3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LabelCssDemo___ extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a label with an error message
        Label outputLabel = new Label("File not found!");
        outputLabel.setId("label-error");  // Set the CSS ID

        // Add the label to a layout
        StackPane root = new StackPane(outputLabel);
        Scene scene = new Scene(root, 300, 100);

        // Load the CSS file
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Set up the stage
        primaryStage.setTitle("Label CSS Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
