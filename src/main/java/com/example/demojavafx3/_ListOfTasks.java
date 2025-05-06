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

import java.util.List;

public class _ListOfTasks extends Application {

    private static VBox taskList; // Keep taskList as a class member

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("List of Tasks");

        // Title
        Label title = new Label("List of Tasks");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-background-color: #d3d3d3; -fx-text-fill: black; -fx-padding: 5; -fx-background-radius: 15;");

        HBox titleBox = new HBox(10, title);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Top right buttons
        Button calendarViewBtn = new Button("📅 Calendar View");
        Button addTaskBtn = new Button("➕   Add a Task   ");
        addTaskBtn.setOnAction(e -> {
            _AddATask.openWindow();
            updateTaskList(); // Call updateTaskList when a new task is added.
        });
        VBox rightButtons = new VBox(10, calendarViewBtn, addTaskBtn);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        // Header layout
        BorderPane header = new BorderPane();
        header.setLeft(titleBox);
        header.setRight(rightButtons);
        header.setPadding(new Insets(10, 20, 10, 20));

        // List of tasks
        taskList = new VBox(5); // Initialize taskList here
        taskList.setPadding(new Insets(10));
        updateTaskList(); // Initial population of task list

        ScrollPane scrollPane = new ScrollPane(taskList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f9f9f9;");

        VBox root = new VBox(header, scrollPane);
        Scene scene = new Scene(root, 700, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void updateTaskList() {
        taskList.getChildren().clear(); // Clear existing tasks
        List<String[]> tasks = _TaskStorage.getTasks(); // Get the updated task list

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}