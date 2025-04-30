package com.example.demojavafx3;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;

public class FileChooserDemo___ extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label fileLabel = new Label("No file selected");

        // Open File button
        Button openButton = new Button("Open File");
        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                fileLabel.setText("Opened: " + selectedFile.getAbsolutePath());
            } else {
                fileLabel.setText("Open operation was cancelled.");
            }
        });

        // Save File button
        Button saveButton = new Button("Save File");
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");

            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                fileLabel.setText("Saving to: " + selectedFile.getAbsolutePath());
            } else {
                fileLabel.setText("Save operation was cancelled.");
            }
        });

        // Layout
        VBox root = new VBox(10, openButton, saveButton, fileLabel);
        Scene scene = new Scene(root, 400, 200);

        // Stage setup
        primaryStage.setTitle("FileChooser Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
