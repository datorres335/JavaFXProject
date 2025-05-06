package com.example.demojavafx3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class _ListOfTasks {

    private MainApplication mainApp;
    private BorderPane view;
    private static VBox taskList;

    public _ListOfTasks(MainApplication mainApp) {
        this.mainApp = mainApp;
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
        addTaskBtn.setOnAction(e -> mainApp.showAddTaskView());

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
        updateTaskList();

        ScrollPane scrollPane = new ScrollPane(taskList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f9f9f9;");

        view.setTop(header);
        view.setCenter(scrollPane);
    }

    public BorderPane getView() {
        return view;
    }

    public static void updateTaskList() {
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