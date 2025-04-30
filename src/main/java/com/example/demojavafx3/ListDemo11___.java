package com.example.demojavafx3;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ListDemo11___ extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a String array.
        String[] strArray = {"Monday", "Tuesday", "Wednesday"};

        // Convert the String array to an ObservableList.
        ObservableList<String> strList = FXCollections.observableArrayList(strArray);

        // Create a ListView control.
        ListView<String> listView = new ListView<>(strList);

        // Optional: Set preferred size
        listView.setPrefSize(200, 120);

        // Add the ListView to the layout
        StackPane root = new StackPane(listView);
        Scene scene = new Scene(root, 300, 200);

        // Set up and show the stage
        primaryStage.setTitle("ListView Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

