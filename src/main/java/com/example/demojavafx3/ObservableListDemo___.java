package com.example.demojavafx3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;

public class ObservableListDemo___ extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create labels and buttons
        Label label1 = new Label("Label 1");
        Label label2 = new Label("Label 2");
        Label label3 = new Label("Label 3");

        Button addButton = new Button("Add All");
        Button removeButton = new Button("Remove Label 1");
        Button clearButton = new Button("Clear");
        Button setAllButton = new Button("Set All");

        // Create an empty HBox
        HBox hbox = new HBox(10); // 10px spacing between children

        // Access its ObservableList of children
        ObservableList list = hbox.getChildren();

        // Button actions using ObservableList methods
        addButton.setOnAction(e -> {
            list.addAll(label1, label2, label3);
        });

        removeButton.setOnAction(e -> {
            list.remove(label1);
        });

        clearButton.setOnAction(e -> {
            list.clear();
        });

        setAllButton.setOnAction(e -> {
            list.setAll(new Label("Reset Label A"), new Label("Reset Label B"));
        });

        // Layout for buttons and HBox
        VBox root = new VBox(10, addButton, removeButton, clearButton, setAllButton, hbox);

        // Set the scene and stage
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ObservableList Demo");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
