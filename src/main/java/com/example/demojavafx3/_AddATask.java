package com.example.demojavafx3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.example.demojavafx3._ListOfTasks.updateTaskList;

public class _AddATask {

    private MainApplication mainApp;
    private BorderPane view;

    public _AddATask(MainApplication mainApp) {
        this.mainApp = mainApp;
        createView();
    }

    private void createView() {
        view = new BorderPane();

        // Header
        Label title = new Label("Add a Task");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-background-color: #d3d3d3; -fx-text-fill: black; -fx-padding: 5; -fx-background-radius: 15;");
        HBox titleBox = new HBox(10, title);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Button calendarViewBtn = new Button("📅 Calendar View");
        calendarViewBtn.setOnAction(e -> mainApp.showCalendarView());

        Button viewTasksBtn = new Button("🔍 View All Tasks");
        viewTasksBtn.setOnAction(e -> mainApp.showTaskListView());

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

        Button submitButton = new Button("Submit Task");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        submitButton.setPrefWidth(120);

        // Create a container for the submit button
        HBox submitContainer = new HBox(submitButton);
        submitContainer.setAlignment(Pos.CENTER);

        form.getChildren().addAll(
                nameLabel, nameField,
                dateLabel, datePicker,
                descLabel, descArea,
                locLabel, locField,
                submitContainer
        );

        // Add button click handler
        submitButton.setOnAction(event -> {
            if (nameField.getText().isEmpty() || datePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Missing Required Fields");
                alert.setContentText("Please fill in all required fields (Task Name and Date)");
                alert.showAndWait();
                return;
            }

            // Save to shared task storage
            String name = nameField.getText();
            String date = datePicker.getValue().toString();
            String desc = descArea.getText();
            String loc = locField.getText();

            _TaskStorage.addTask(name, date, desc, loc);
            updateTaskList(); // Update the task list in the main view

            // Optionally show a confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task Saved");
            alert.setHeaderText(null);
            alert.setContentText("Task saved successfully!");
            alert.showAndWait();

            // Clear the form fields
            nameField.clear();
            datePicker.setValue(null);
            descArea.clear();
            locField.clear();

            // Switch back to the task list view
            mainApp.showTaskListView();
        });

        view.setTop(header);
        view.setCenter(form);
    }

    public BorderPane getView() {
        return view;
    }
}