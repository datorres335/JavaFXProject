package com.example.demojavafx3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demojavafx3._ListOfTasks.updateTaskList;

public class _AddATask {

    private MainApplication mainApp;
    private BorderPane view;

    // Form fields as class members so they can be accessed from different methods
    private TextField nameField;
    private DatePicker datePicker;
    private TextArea descArea;
    private TextField locField;
    private Button submitButton;
    private CheckBox completeCheck;

    // Track if we're editing an existing task and which one
    private boolean isEditing = false;
    private int editingTaskIndex = -1;

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

        completeCheck = new CheckBox("Complete");
        Button deleteBtn = new Button("🗑");
        Tooltip deleteTooltip = new Tooltip("Delete task");
        Tooltip.install(deleteBtn, deleteTooltip);

        // Set up delete button action
        deleteBtn.setOnAction(e -> {
            if (isEditing && editingTaskIndex >= 0) {
                // Confirm deletion
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Delete");
                confirmAlert.setHeaderText("Delete Task");
                confirmAlert.setContentText("Are you sure you want to delete this task?");

                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Delete the task
                        _TaskStorage.removeTask(editingTaskIndex);
                        updateTaskList();
                        mainApp.showTaskListView();
                    }
                });
            }
        });

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
        nameField = new TextField();
        nameField.setPromptText("Give the task a name");

        Label dateLabel = new Label("Date");
        datePicker = new DatePicker();

        Label descLabel = new Label("Description");
        descArea = new TextArea();
        descArea.setPromptText("Write a description for the task");
        descArea.setPrefRowCount(3);

        Label locLabel = new Label("Location");
        locField = new TextField();
        locField.setPromptText("Enter the location of task");

        submitButton = new Button("Submit Task");
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

            // Get form data
            String name = nameField.getText();
            String date = datePicker.getValue().toString();
            String desc = descArea.getText();
            String loc = locField.getText();
            boolean isComplete = completeCheck.isSelected();

            if (isEditing && editingTaskIndex >= 0) {
                // Update existing task
                _TaskStorage.updateTask(editingTaskIndex, name, date, desc, loc, isComplete);
            } else {
                // Save new task
                _TaskStorage.addTask(name, date, desc, loc);
            }

            updateTaskList(); // Update the task list in the main view

            // Show confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task Saved");
            alert.setHeaderText(null);
            alert.setContentText(isEditing ? "Task updated successfully!" : "Task saved successfully!");
            alert.showAndWait();

            // Clear form and reset state
            clearForm();

            // Switch back to the task list view
            mainApp.showTaskListView();
        });

        view.setTop(header);
        view.setCenter(form);
    }

    // Method to set up the form for editing an existing task
    public void setupForEditing(String[] taskToEdit) {
        clearForm(); // Clear any previous data

        if (taskToEdit == null) {
            // New task
            isEditing = false;
            editingTaskIndex = -1;
            submitButton.setText("Submit Task");
            completeCheck.setSelected(false);
            return;
        }

        // We're editing an existing task
        isEditing = true;

        // Find the task index in the storage
        List<String[]> tasks = _TaskStorage.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            String[] task = tasks.get(i);
            // Compare task data to find the matching task
            if (task[0].equals(taskToEdit[0]) && task[1].equals(taskToEdit[1])) {
                editingTaskIndex = i;
                break;
            }
        }

        if (editingTaskIndex >= 0) {
            // Fill the form with task data
            String[] task = _TaskStorage.getTask(editingTaskIndex);

            // Assuming task format: [date, name, description, location, isComplete]
            nameField.setText(task[1]); // Name

            // Parse the date string to LocalDate
            try {
                LocalDate date = LocalDate.parse(task[0], DateTimeFormatter.ISO_DATE);
                datePicker.setValue(date);
            } catch (Exception e) {
                // Handle date parsing error
                datePicker.setValue(LocalDate.now());
            }

            descArea.setText(task[2]); // Description
            locField.setText(task[3]); // Location

            // Set completion status if available
            if (task.length > 4) {
                completeCheck.setSelected(Boolean.parseBoolean(task[4]));
            } else {
                completeCheck.setSelected(false);
            }

            submitButton.setText("Update Task");
        }
    }

    private void clearForm() {
        nameField.clear();
        datePicker.setValue(null);
        descArea.clear();
        locField.clear();
        completeCheck.setSelected(false);
    }

    public BorderPane getView() {
        return view;
    }
}