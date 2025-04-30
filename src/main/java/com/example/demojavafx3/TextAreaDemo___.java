package com.example.demojavafx3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextAreaDemo___ extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a TextArea with no initial text
        TextArea textArea = new TextArea();

        // Set preferred number of columns and rows
        textArea.setPrefColumnCount(20);
        textArea.setPrefRowCount(10);

        // Enable text wrapping
        textArea.setWrapText(true);

        // Button to display current text content
        Button showTextButton = new Button("Show Text");
        showTextButton.setOnAction(e -> {
            String content = textArea.getText();
            System.out.println("TextArea contains:\n" + content);
        });

        // Button to replace content
        Button setTextButton = new Button("Set Example Text");
        setTextButton.setOnAction(e -> {
            textArea.setText("This is some example text that will wrap across multiple lines.");
        });

        // Layout
        VBox root = new VBox(10, textArea, showTextButton, setTextButton);
        Scene scene = new Scene(root, 400, 300);

        // Stage setup
        primaryStage.setTitle("TextArea Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
