package com.example.demojavafx3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RectangleDemo___ extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        // Rectangle using constructor
        Rectangle rect1 = new Rectangle(200, 100, 75, 150);
        rect1.setFill(Color.LIGHTGRAY); // optional: make it visible

        // Rectangle using setters
        Rectangle rect2 = new Rectangle();
        rect2.setX(10);
        rect2.setY(20);
        rect2.setWidth(50);
        rect2.setHeight(100);
        rect2.setFill(Color.DARKGREEN);

        pane.getChildren().addAll(rect1, rect2);

        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("Rectangle Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
