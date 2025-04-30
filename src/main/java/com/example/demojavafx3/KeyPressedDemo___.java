package com.example.demojavafx3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class KeyPressedDemo___ extends Application {
    private int count = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("0");

        HBox hbox = new HBox(10, label);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        hbox.setFocusTraversable(true); // Important!

        // Key press handler on HBox
        hbox.setOnKeyPressed(event -> {
            count++;
            label.setText(String.valueOf(count));
        });

        Scene scene = new Scene(hbox, 250, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Key Press Demo");
        primaryStage.show();

        // Request focus *after* stage is shown
        hbox.requestFocus();
    }
}
