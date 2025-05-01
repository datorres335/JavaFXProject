package com.example.demojavafx3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class _ListOfTasks extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("List of Tasks");

        // Title
        Label title = new Label("List of Tasks");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-background-color: #d3d3d3; -fx-text-fill: black; -fx-padding: 5; -fx-background-radius: 15;");

        HBox titleBox = new HBox(10, title);//HBox(10, leftArrow, title, rightArrow);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Top right buttons
        Button calendarViewBtn = new Button("📅 Calendar View");
        Button addTaskBtn = new Button("➕   Add a Task   ");
        VBox rightButtons = new VBox(10, calendarViewBtn, addTaskBtn);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        // Header layout
        BorderPane header = new BorderPane();
        header.setLeft(titleBox);
        header.setRight(rightButtons);
        header.setPadding(new Insets(10, 20, 10, 20));

        // List of tasks
        VBox taskList = new VBox(5);
        taskList.setPadding(new Insets(10));

        // Example tasks
        String[][] tasks = {
                {"4/1/25 7:00am", "< Description of task >"},
                {"4/7/25 10:00am", "< Description of task >"},
                {"4/7/25 11:00am", "< Description of task >"},
                {"4/7/25 1:00pm", "< Description of task >"},
                {"4/17/25 11:00am", "< Description of task >"},
                {"4/29/25 12:00pm", "< Description of task >"},
                {"05/15/25 1:00pm", "< Description of task >"},
                {"05/29/25 4:00pm", "< Description of task >"},
                {"06/07/25 3:00pm", "< Description of task >"},
                {"06/25/25 4:00pm", "< Description of task >"},
                {"07/01/25 7:00am", "< Description of task >"}
        };

        for (String[] task : tasks) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(5));
            row.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 4;");
            Label date = new Label(task[0]);
            Label desc = new Label(task[1]);
            row.getChildren().addAll(date, desc);
            taskList.getChildren().add(row);
        }

        ScrollPane scrollPane = new ScrollPane(taskList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f9f9f9;");

        VBox root = new VBox(header, scrollPane);
        Scene scene = new Scene(root, 700, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
