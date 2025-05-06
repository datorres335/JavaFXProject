package com.example.demojavafx3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class _ListOfTasks {

    private MainApplication mainApp;
    private BorderPane view;
    private static VBox taskList;
    private static _ListOfTasks instance; // Add a static reference to the current instance

    public _ListOfTasks(MainApplication mainApp) {
        this.mainApp = mainApp;
        instance = this; // Store the instance
        createView();
    }

    private void createView() {
        view = new BorderPane();

        // Title
        Label title = new Label("List of Tasks");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-background-color: #d3d3d3; -fx-text-fill: black; -fx-padding: 5; -fx-background-radius: 15;");

        HBox titleBox = new HBox(10, title);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Top right buttons
        Button calendarViewBtn = new Button("📅 Calendar View");
        calendarViewBtn.setOnAction(e -> mainApp.showCalendarView());

        Button addTaskBtn = new Button("➕   Add a Task   ");
        addTaskBtn.setOnAction(e -> mainApp.showAddTaskView(null)); // Pass null for a new task

        VBox rightButtons = new VBox(10, calendarViewBtn, addTaskBtn);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        // Header layout
        BorderPane header = new BorderPane();
        header.setLeft(titleBox);
        header.setRight(rightButtons);
        header.setPadding(new Insets(10, 20, 10, 20));

        // List of tasks
        taskList = new VBox(5);
        taskList.setPadding(new Insets(10));
        refreshTaskList();

        ScrollPane scrollPane = new ScrollPane(taskList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f9f9f9;");

        view.setTop(header);
        view.setCenter(scrollPane);
    }

    public BorderPane getView() {
        return view;
    }

    public void refreshTaskList() {
        taskList.getChildren().clear();
        List<String[]> tasks = _TaskStorage.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            String[] task = tasks.get(i);
            final int taskIndex = i; // Need final variable for lambda

            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(5));
            row.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 4;");

            // date, name, desc, loc
            Label date = new Label(task[0]);
            Label name = new Label(task[1]);
            Label desc = new Label(task[2]);
            Label loc = new Label(task[3]);

            row.getChildren().addAll(date, name, desc, loc);

            // Make the row clickable
            row.setOnMouseEntered(e -> {
                row.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 4; -fx-cursor: hand;");
            });

            row.setOnMouseExited(e -> {
                row.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 4;");
            });

            row.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                // Navigate to edit task view with the selected task
                mainApp.showAddTaskView(task);
            });

            taskList.getChildren().add(row);
        }
    }

    // Static method for updating from other classes
    public static void updateTaskList() {
        // This is a static method that will be called from other classes
        // We need to make sure the taskList is initialized before updating
        if (taskList != null) {
            // If we have an instance, use its refreshTaskList method to get clickable rows
            if (instance != null) {
                instance.refreshTaskList();
            } else {
                // Fallback if no instance is available
                taskList.getChildren().clear();
                List<String[]> tasks = _TaskStorage.getTasks();

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
        }
    }
}