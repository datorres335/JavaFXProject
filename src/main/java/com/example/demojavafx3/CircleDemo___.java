package com.example.demojavafx3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleDemo___ extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        // Red filled circle
        Circle redCircle = new Circle();
        redCircle.setCenterX(75);
        redCircle.setCenterY(100);
        redCircle.setRadius(50);
        redCircle.setFill(Color.RED);

        // Black outlined circle (no fill)
        Circle outlineCircle = new Circle(200, 100, 50);
        outlineCircle.setFill(null);
        outlineCircle.setStroke(Color.BLACK);

        // Add circles to the pane
        pane.getChildren().addAll(redCircle, outlineCircle);

        Scene scene = new Scene(pane, 300, 200);
        primaryStage.setTitle("Circle Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
