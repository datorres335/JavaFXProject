package com.example.demojavafx3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class _AddATask extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add a Task");

        // Header
        Label title = new Label("Add a Task");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-background-color: #d3d3d3; -fx-text-fill: black; -fx-padding: 5; -fx-background-radius: 15;");
        HBox titleBox = new HBox(10, title);//HBox(10, leftArrow, title, rightArrow);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Button calendarViewBtn = new Button("📅 Calendar View");
        Button viewTasksBtn = new Button("🔍 View All Tasks");
        CheckBox completeCheck = new CheckBox("Complete");
        Button deleteBtn = new Button("🗑");
        Tooltip deleteTooltip = new Tooltip("Delete task");
        Tooltip.install(deleteBtn, deleteTooltip);

        HBox topRightBtnsContainer = new HBox(10, completeCheck, deleteBtn);
        VBox topRightBtns = new VBox(10, calendarViewBtn, viewTasksBtn, topRightBtnsContainer);
        topRightBtns.setAlignment(Pos.TOP_RIGHT);

        BorderPane header = new BorderPane();
        header.setLeft(title);
        header.setRight(topRightBtns);
        BorderPane.setMargin(title, new Insets(10));
        BorderPane.setMargin(topRightBtns, new Insets(10));

        // Form Elements
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.TOP_LEFT);

        Label nameLabel = new Label("Task Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Give the task a name");

        Label dateLabel = new Label("Date");
        DatePicker datePicker = new DatePicker();

        Label descLabel = new Label("Description");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Write a description for the task");
        descArea.setPrefRowCount(3);

        Label locLabel = new Label("Location");
        TextField locField = new TextField();
        locField.setPromptText("Enter the location of task");

        form.getChildren().addAll(
                nameLabel, nameField,
                dateLabel, datePicker,
                descLabel, descArea,
                locLabel, locField
        );

        VBox root = new VBox(0, header, form);
        Scene scene = new Scene(root, 600, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
